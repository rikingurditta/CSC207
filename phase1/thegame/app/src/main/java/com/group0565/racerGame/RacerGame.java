package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.statistics.enums.StatisticKey;
import com.group0565.theme.Themes;

import java.util.Date;

public class RacerGame extends GameObject implements Observer {

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
  /** The right buttom (moves racer object to right most lane) */
  private Button rightButton;
  /** ObstacleManager that spawns and manages all of this game's obstacles */
  private ObstacleManager obsManager;
  /** The racer object that the player controls with */
  private Racer racer;
  /** Database object for game statistics */
  private IAsyncStatisticsRepository myStatRepo;

  /**
   * A constructor for a RacerGame object
   *
   * @param position A Vector representing the position of this object
   */
  RacerGame(Vector position) {
    super(position);
    listener =
        repository -> {
          myStatRepo = repository;
        };
    StatisticRepositoryInjector.inject(TAG, listener);
  }

  /** Initializer method that initializes the buttons and objects of the game */
  public void init() {
    startTime = System.currentTimeMillis();
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
    super.init();
  }

  /**
   * Renders this object on the screen
   *
   * @param canvas The Canvas on which to draw
   */
  public void draw(Canvas canvas) {
    super.draw(canvas);
    Paint time = new Paint();
    if (getGlobalPreferences().getTheme() == Themes.LIGHT) {
      // Set background to white
      canvas.drawRGB(255, 255, 255);
      // Set text colour to black
      time.setARGB(255, 0, 0, 0);
    } else {
      // Set background to black
      canvas.drawRGB(0, 0, 0);
      // Set text colour to white
      time.setARGB(255, 255, 255, 255);
    }
    time.setTextSize(128);
    // Set the colour of the lines
    Paint colour = new Paint();
    colour.setARGB(255, 255, 0, 0);
    // Draw the red lines that separate the lanes
    canvas.drawRect(canvas.getWidth() / 3 - 15, 0, canvas.getWidth() / 3 + 15, 2500, colour);
    canvas.drawRect(
        2 * canvas.getWidth() / 3 - 15, 0, 2 * canvas.getWidth() / 3 + 15, 2500, colour);
    canvas.drawText(Long.toString(totalTime), 600, 200, time);
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
  Racer getRacer() {
    return racer;
  }

  /**
   * Updates the database with user's time survived
   *
   * @param totalTime the player's time survived during this game
   */
  void updateDB(long totalTime) {
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
  long getTotalTime() {
    return totalTime;
  }

  /**
   * Getter method that returns spawnTime attribute
   *
   * @return spawnTime
   */
  long getSpawnTime() {
    return spawnTime;
  }

  /** Sets the live attribute of this variable to the opposite value */
  void setLive() {
    live = !live;
  }

  /** Disables the racer and obstacles from rendering on the screen when the user loses the game */
  void disableAll() {
    racer.setEnable(false);
    obsManager.setEnable(false);
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
}
