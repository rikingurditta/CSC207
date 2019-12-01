package com.group0565.racer.core;

import com.group0565.engine.gameobjects.GameObject;
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

  /** Game tag for purposes of database */
  private static final String TAG = "RacerGame";

  /**
   * The starting position of the Racer.
   */
  private static final Vector RACER_STARTING_POSITION = new Vector(540, 1550);

  /**
   * The position of the Racer when it is in the left Lane.
   */
  private static final Vector RACER_LEFT_POSITION = new Vector(180, 1550);

  /**
   * The position of the Racer when it is in the middle Lane.
   */
  private static final Vector RACER_MIDDLE_POSITION = new Vector(540, 1550);

  /**
   * The position of the Racer when it is in the right Lane.
   */
  private static final Vector RACER_RIGHT_POSITION = new Vector(900, 1550);

  /**
   * The score required to earn the 15 second achievement.
   */
  private static final int ACHIVEMENT_15_SEC_VALUE = 15000;

  /**
   * The score required to earn the 60 second achievement.
   */
  private static final int ACHIEVEMENT_60_SEC_VALUE = 60000;

  /**
   * Listener that updates database accordingly
   */
  private StatisticRepositoryInjector.RepositoryInjectionListener listener;

  /**
   *  Date that this RacerGame was created (up to millisecond, used as ID for database purposes)
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

  /**
   *  The time between each spawn
   */
  private long spawnDelay = 600;

  /**
   * Whether or not this racer is still alive
   */
  private boolean alive = true;

  /**
   * Whether or not the game is paused
   */
  private boolean paused = false;

  /**
   *  Whether or not the survived for 15 seconds achievement has been achieved
   */
  private boolean achieved15Sec = false;

  /**
   *  Whether or not the survived for 60 seconds achievement has been achieved
   */
  private boolean achieved60Sec = false;

  /** Database object for game statistics */
  private IAsyncStatisticsRepository myStatRepo;

  /**
   * The RacerGameMenu
   */
  private RacerGameMenu gameMenu;

  /**
   * The GameOverMenu
   */
  private RacerGameOverMenu gameOverMenu;

  /**
   * The pause menu
   */
  private RacerPauseMenu pauseMenu;

  /**
   * Constructs a RacerEngine
   */
  RacerEngine() {
    super(new Vector());

    listener = repository -> myStatRepo = repository;
  }

  /**
   * Initializes menus and attributes
   */
  public void init() {
    super.init();

    startTime = System.currentTimeMillis();

    gameMenu = new RacerGameMenu(this);
    this.adopt(gameMenu);

    racer = new Racer(RACER_STARTING_POSITION, 2);
    this.adopt(racer);

    gameOverMenu = new RacerGameOverMenu(null, this);
    this.adopt(gameOverMenu);
    gameOverMenu.setEnable(false);

    pauseMenu = new RacerPauseMenu(null, this);
    this.adopt(pauseMenu);
    pauseMenu.setEnable(false);

    StatisticRepositoryInjector.inject(TAG, listener);
  }

  /**
   * Spawns an obstacle in the game
   */
  private void spawnObstacle() {
    gameMenu.spawnObstacle();
  }

  /**
   * Pauses the game
   */
  public void pauseGame() {
    paused = true;
    gameMenu.setEnable(false);
    pauseMenu.setEnable(true);
  }

  /**
   * Unpauses the game
   */
  public void unpauseGame() {
    paused = false;
    gameMenu.setEnable(true);
    pauseMenu.setEnable(false);
  }

  /**
   * Ends the game
   */
  public void endGame() {
    alive = false;
    racer.setEnable(false);
    gameMenu.setEnable(false);
    gameOverMenu.setEnable(true);
    updateDB(totalTime);
  }

  /**
   * Moves the racer to a given lane
   * @param lane the lane
   */
  public void moveRacer(int lane) {
    if (lane == 1) {
      racer.setAbsolutePosition(RACER_LEFT_POSITION);
    } else if (lane == 2) {
      racer.setAbsolutePosition(RACER_MIDDLE_POSITION);
    } else {
      racer.setAbsolutePosition(RACER_RIGHT_POSITION);
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

      if (!achieved15Sec && totalTime > ACHIVEMENT_15_SEC_VALUE) {
        this.getEngine().getAchievementManager().unlockAchievement("Racer", "racer_15_seconds");
        achieved15Sec = true;
      }
      if (!achieved60Sec && totalTime > ACHIEVEMENT_60_SEC_VALUE) {
        this.getEngine().getAchievementManager().unlockAchievement("Racer", "racer_60_seconds");
        achieved60Sec = true;
      }
    }
  }

  /**
   * Updates the database with user's time survived
   *
   * @param totalTime the player's time survived during this game
   */
  private void updateDB(long totalTime) {
    if (myStatRepo != null) {
      // You can always use put (also for new objects) because of the way that Firebase DB works
      myStatRepo.put(
          IStatisticFactory.createGameStatistic(
              StatisticKey.RACER_TIME_SURVIVED.getValue() + startTime, totalTime));
    }
  }

  /** @param observable Button objects */
  public void observe(Observable observable, ObservationEvent observationEvent) {}

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
