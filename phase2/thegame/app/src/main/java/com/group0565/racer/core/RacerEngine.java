package com.group0565.racer.core;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;
import com.group0565.racer.menus.RacerGameMenu;
import com.group0565.racer.menus.RacerGameOverMenu;
import com.group0565.racer.menus.RacerPauseMenu;
import com.group0565.racer.objects.Racer;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.statistics.enums.StatisticKey;

public class RacerEngine extends GameObject implements EventObserver, Observable {

    /**
     * Game tag for purposes of database
     */
    private static final String TAG = "RacerGame";

    /**
     * Listener that updates database accordingly
     */
    StatisticRepositoryInjector.RepositoryInjectionListener listener;

    /**
     * Date that this RacerGame was created (up to millisecond, used as ID for database purposes)
     */
    private long startTime;

    /**
     * Time in milliseconds since game started
     */
    private long totalTime = 0;

    /**
     * Time in milliseconds since last object spawn
     */
    private long spawnTime = 0;

    /**
     * The racer object that the player controls with
     */
    private Racer racer;

    private long spawnDelay = 750;

    private boolean alive = true;

    private boolean paused = false;

    /**
     * Database object for game statistics
     */
    private IAsyncStatisticsRepository myStatRepo;

    private RacerGameMenu gameMenu;

    private RacerGameOverMenu gameOverMenu;

    private RacerPauseMenu pauseMenu;

    public RacerEngine() {
        super(new Vector());
    }

    public void init() {
        super.init();

        startTime = 0;

        gameMenu = new RacerGameMenu(this);
        this.adopt(gameMenu);

        racer = new Racer(new Vector(540, 1550), 2);
        this.adopt(racer);

        gameOverMenu = new RacerGameOverMenu(null, this);
        this.adopt(gameOverMenu);
        gameOverMenu.setEnable(false);

        pauseMenu = new RacerPauseMenu(null, this);
        this.adopt(pauseMenu);
        pauseMenu.setEnable(false);

    }

    public void spawnObstacle() {
        gameMenu.spawnObstacle();
    }

    public boolean isPaused() {
        return paused;
    }

    public void pauseGame() {
        paused = true;
        gameMenu.setEnable(false);
        pauseMenu.setEnable(true);
    }

    public void unPauseGame() {
        paused = false;
        gameMenu.setEnable(true);
        pauseMenu.setEnable(false);
    }

    public void endGame() {
        alive = false;
        racer.setEnable(false);
        gameMenu.setEnable(false);
        gameOverMenu.setEnable(true);
    }

    public void moveRacer(int lane) {
        if (lane == 1) {
            racer.setAbsolutePosition(new Vector(180, 1550));
        } else if (lane == 2) {
            racer.setAbsolutePosition(new Vector(540, 1550));
        } else {
            racer.setAbsolutePosition(new Vector(900, 1550));
        }
    }

    /**
     * Updates the game
     *
     * @param ms Milliseconds Since Last Update
     */
    @Override
    public void update(long ms) {
        if (alive && !paused) {
            spawnTime += ms;
            totalTime += ms;
            if (spawnTime >= spawnDelay) {
                spawnObstacle();
                spawnTime = 0;
                spawnDelay -= 1;
            }
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
     * Renders this object on the screen
     *
     * @param canvas The Canvas on which to draw
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /**
     * @param observable Button objects
     */
    public void observe(Observable observable, ObservationEvent observationEvent) {

    }

    public RacerPauseMenu getPauseMenu() {
        return pauseMenu;
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
     * Getter method that returns totalTime attribute
     *
     * @return totalTime
     */
    public long getTotalTime() {
        return totalTime;
    }
}

