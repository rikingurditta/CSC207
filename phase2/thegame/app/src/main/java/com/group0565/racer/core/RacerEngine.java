package com.group0565.racer.core;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.racer.menus.RacerGameOverMenu;
import com.group0565.racer.menus.RacerPauseMenu;
import com.group0565.racer.obstacles.ObstacleManager;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.statistics.enums.StatisticKey;
import com.group0565.theme.Themes;

public class RacerEngine extends GameObject implements Observer, Observable {

    private boolean ended = false;
    private boolean paused = false;

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

    /**
     * Boolean representing whether or not the player is still alive (The player is considered alive
     * until they collide with an object)
     */
    private boolean live = true;

    /** The left button (moves racer object to left most lane) */
    private Button leftButton;

    /** The middle button (moves racer object to middle lane) */
    private Button middleButton;

    /** The right button(moves racer object to right most lane) */
    private Button rightButton;

    /** ObstacleManager that spawns and manages all of this game's obstacles */
    private ObstacleManager obsManager;

    /** The racer object that the player controls with */
    private Racer racer;

    /** Database object for game statistics */
    private IAsyncStatisticsRepository myStatRepo;

    private RacerGameOverMenu gameOverMenu;

    private RacerPauseMenu pauseMenu;

    private RacerRenderer renderer;

    public RacerEngine() {
        super(new Vector());

    }

    public void init() {
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

////        gameOverMenu = new RacerGameOverMenu(size?);
//        gameOverMenu.setEnable(false);
//
////        pauseMenu = new RacerPauseMenu();
//        pauseMenu.setEnable(false);
//
////        renderer = new RacerRenderer();
//        renderer.setEnable(true);
    }

    /** @param observable Button objects */
    public void observe(Observable observable) {
        if (observable == leftButton) {
            racer.setAbsolutePosition(new Vector(100, 1600));
            racer.setLane(1);
        } else if (observable == middleButton) {
            racer.setAbsolutePosition(new Vector(475, 1600));
            racer.setLane(2);
        } else if (observable == rightButton) {
            racer.setAbsolutePosition(new Vector(850, 1600));
            racer.setLane(3);
        }
    }

    /**
     * Getter method that returns this game's Racer object
     *
     * @return Racer object
     */
    public Racer getRacer() {
        return racer;
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

    /** Disables the racer and obstacles from rendering on the screen when the user loses the game */
    public void disableAll() {
        racer.setEnable(false);
        obsManager.setEnable(false);
        gameOverMenu.setEnable(true);
    }

    /**
     * Updates the game
     *
     * @param ms Milliseconds Since Last Update
     */
    @Override
    public void update(long ms) {
        if (live) {
            this.spawnTime += ms;
            this.totalTime += ms;
            if (this.spawnTime >= 750) {
                obsManager.spawnObstacle();
                this.spawnTime = 0;
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
        renderer.draw(canvas);
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
}
