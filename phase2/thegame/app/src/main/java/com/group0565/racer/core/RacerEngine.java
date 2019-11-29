package com.group0565.racer.core;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;
import com.group0565.racer.menus.RacerGameMenu;
import com.group0565.racer.menus.RacerGameOverMenu;
import com.group0565.racer.menus.RacerPauseMenu;
import com.group0565.racer.obstacles.ObstacleManager;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.statistics.enums.StatisticKey;

public class RacerEngine extends GameObject implements EventObserver, Observable {

    /** Game tag for purposes of database
     */
    private static final String TAG = "RacerGame";

    /** Listener that updates database accordingly */
    StatisticRepositoryInjector.RepositoryInjectionListener listener;

    /** Date that this RacerGame was created (up to millisecond, used as ID for database purposes) */
    private long startTime;

    /** Time in milliseconds since game started */
    private long totalTime = 0;

    /** Time in milliseconds since last object spawn */
    private long spawnTime = 0;

    private long spawnDelay = 750;

    private boolean ended = false;

    private boolean paused = false;

    /**
     * Boolean representing whether or not the player is still alive (The player is considered alive
     * until they collide with an object)
     */
    private boolean live = true;

    private Lane leftLane;

    private Lane middleLane;

    private Lane rightLane;

    /** The left button (moves racer object to left most lane) */
    private Button leftButton;

    /** The middle button (moves racer object to middle lane) */
    private Button middleButton;

    /** The right button(moves racer object to right most lane) */
    private Button rightButton;

    /** Pauses game when pressed, brings up a paused menu */
    private Button pauseButton;

    /** ObstacleManager that spawns and manages all of this game's obstacles */
    private ObstacleManager obsManager;

    /** The racer object that the player controls with */
    private Racer racer;

    /** Database object for game statistics */
    private IAsyncStatisticsRepository myStatRepo;

    private RacerGameMenu gameMenu;

    private RacerGameOverMenu gameOverMenu;

    private RacerPauseMenu pauseMenu;

    public RacerEngine() {
        super(new Vector());
    }

    public void init() {
        leftLane = new Lane(new Vector(0, 0), 0);
        middleLane = new Lane(new Vector(0, 0), 0);
        rightLane = new Lane(new Vector(0, 0), 0);

        this.adopt(leftLane);
        this.adopt(middleLane);
        this.adopt(rightLane);

        leftButton =
                new Button(
                        new Vector(100, 1750),
                        new Vector(150, 150),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0));
        middleButton =
                new Button(
                        new Vector(475, 1750),
                        new Vector(150, 150),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0));
        rightButton =
                new Button(
                        new Vector(850, 1750),
                        new Vector(150, 150),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0));

        this.adopt(leftButton);
        this.adopt(middleButton);
        this.adopt(rightButton);
        leftButton.registerObserver(this);
        middleButton.registerObserver(this);
        rightButton.registerObserver(this);

        racer = new Racer(new Vector(475, 1600), 2);
        this.adopt(racer);

        obsManager = new ObstacleManager(this);
        this.adopt(obsManager);

        gameMenu = new RacerGameMenu(this);
        this.adopt(gameMenu);

        gameOverMenu = new RacerGameOverMenu(null, this);
        this.adopt(gameOverMenu);
        gameOverMenu.setEnable(false);

        pauseButton = new Button(new Vector(900, 150),
                new Vector(100, 100),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0));
        this.adopt(pauseButton);
        pauseButton.registerObserver(this);

        pauseMenu = new RacerPauseMenu(null, this);
        this.adopt(pauseMenu);
        pauseMenu.setEnable(false);

    }

    /** @param observable Button objects */
    public void observe(Observable observable, ObservationEvent observationEvent) {
        if (observable == leftButton && observationEvent.getMsg().equals(Button.EVENT_DOWN)) {
            racer.setAbsolutePosition(new Vector(100, 1600));
            racer.setLane(1);
        } else if (observable == middleButton && observationEvent.getMsg().equals(Button.EVENT_DOWN)) {
            racer.setAbsolutePosition(new Vector(475, 1600));
            racer.setLane(2);
        } else if (observable == rightButton && observationEvent.getMsg().equals(Button.EVENT_DOWN)) {
            racer.setAbsolutePosition(new Vector(850, 1600));
            racer.setLane(3);
        } else if (observable == pauseButton && observationEvent.getMsg().equals(Button.EVENT_DOWN)) {
            pauseMenu.setEnable(true);
            setEnable(false);
        }
    }

    /**
     * Updates the database with user's time survived
     *
     * @param totalTime the player's time survived during this game
     */
    public void updateDB(long totalTime) {
        if (myStatRepo != null) {
            // You can always use put (also for new objects) because of the way that Firebase DB works
            myStatRepo.put(
                    IStatisticFactory.createGameStatistic(
                            StatisticKey.RACER_TIME_SURVIVED.getValue() + startTime, totalTime));
        }
    }

    /**
     * Updates the game
     *
     * @param ms Milliseconds Since Last Update
     */
    @Override
    public void update(long ms) {
        if (live) {
            spawnTime += ms;
            totalTime += ms;
            if (spawnTime >= spawnDelay) {
                obsManager.spawnObstacle();
                spawnTime = 0;
                spawnDelay -= 1;
            }
        }
    }

    /**
     * Renders this object on the screen
     *
     * @param canvas The Canvas on which to draw
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void endGame() {
        racer.setEnable(false);
        obsManager.setEnable(false);
        gameMenu.setEnable(false);
        gameOverMenu.setEnable(true);
        setLive();
    }

    /**
     * Getter method that returns this game's Racer object
     *
     * @return Racer object
     */
    public Racer getRacer() {
        return racer;
    }

    public boolean hasEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Getter method that returns totalTime attribute
     *
     * @return totalTime
     */
    public long getTotalTime() {
        return totalTime;
    }

    /**
     * Getter method that returns spawnTime attribute
     *
     * @return spawnTime
     */
    public long getSpawnTime() {
        return spawnTime;
    }

    /** Sets the live attribute of this variable to the opposite value */
    public void setLive() {
        live = !live;
    }


}
