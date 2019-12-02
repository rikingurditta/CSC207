package com.group0565.bombergame.core;

import android.util.Log;

import com.group0565.bombergame.grid.SquareGrid;
import com.group0565.bombergame.gridobjects.BomberMan;
import com.group0565.bombergame.input.InputSystem;
import com.group0565.bombergame.input.JoystickInput;
import com.group0565.bombergame.input.RandomInput;
import com.group0565.bombergame.menus.GameOverMenu;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.statistics.enums.StatisticKey;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

/** The class with the main running logic of the game. */
public class BomberEngine extends GameObject implements Observable {
  private static final long GAME_DURATION = 120000;
  private static final String GAME_NAME = "BomberGame";

  /**
   * Create a STRONG reference to the listener so it won't get garbage collected. Keeping it
   * non-private for that reason as well.
   */
  StatisticRepositoryInjector.RepositoryInjectionListener listener;

  /** Lists to help manage this Engine's children. */
  private ArrayList<GameObject> itemsToBeAdopted = new ArrayList<>();

  private ArrayList<GameObject> itemsToBeRemoved = new ArrayList<>();

  /** The user's BomberMan. */
  private BomberMan meBomberMan;

  /** The timer counting how many ms are left in the game. */
  private long gameTimer;

  /** Whether or not this game has ended. */
  private boolean gameEnded = false;

  /** What time (real world) this game started. */
  private long startTime;

  /** The repository to interact with the stats DB */
  private IAsyncStatisticsRepository myStatRepo;

  /** LanguageTexts for in-game time/stats displays. */
  private LanguageText bombsPlacedLT;

  private LanguageText damageDealtLT;
  private LanguageText hpRemainingLT;
  private LanguageText timeLeftLT;

  /** PaintCans for background and text so that theme switching works. */
  private ThemedPaintCan bgPaintCan;

  private ThemedPaintCan textPaintCan;

  /** Menu with options for what to do when game is over. */
  private GameMenu gameOverMenu;

  /** Create a new game. Construct game grid and objects within it. */
  public BomberEngine() {
    super(new Vector());
  }

  @Override
  public void init() {
    listener =
        repository -> {
          myStatRepo = repository;
        };
    StatisticRepositoryInjector.inject(GAME_NAME, listener);

    gameEnded = false;

    // set time stuff
    gameTimer = GAME_DURATION;
    startTime = System.currentTimeMillis();

    // create grid
    SquareGrid grid = new SquareGrid(new Vector(100, 100), 0, 16, 8, 100, this);
    adoptLater(grid);

    // create player
    InputSystem joystickInput =
        new JoystickInput(new Vector(150, 750), 100, new Vector(0), new Vector(1700, 100), 100);
    adoptLater(joystickInput);
    meBomberMan = new BomberMan(new Coords(0, 0), 20, joystickInput, this, grid, 10);
    adoptLater(meBomberMan);

    // create NPC player using RandomInput

    InputSystem randomInput = new RandomInput(1000);
    adoptLater(randomInput);
    BomberMan bm2 = new BomberMan(new Coords(10, 6), 20, randomInput, this, grid, 10);
    adoptLater(bm2);

    // make 25 crates
    for (int i = 0; i < 25; i++) grid.makeRandomCrate();

    // create game over menu
    gameOverMenu = new GameOverMenu(new Vector(625, 625));
    gameOverMenu.updateAllPosition();
    gameOverMenu.setOffset(new Vector(500, 250));
    gameOverMenu.setZ(1000);
    gameOverMenu.setEnable(false);
    gameOverMenu.registerObserver(this::observeGameOverMenu);
    adoptLater(gameOverMenu);

    GlobalPreferences gp = getGlobalPreferences();
    GameAssetManager am = getEngine().getGameAssetManager();
    bgPaintCan = new ThemedPaintCan("Bomber", "Background.Background");
    textPaintCan = new ThemedPaintCan("Bomber", "Text.Text");
    bgPaintCan.init(gp, am);
    textPaintCan.init(gp, am);
    bombsPlacedLT = new LanguageText(gp, am, "Bomber", "Bombs_Placed");
    damageDealtLT = new LanguageText(gp, am, "Bomber", "Damage_Dealt");
    hpRemainingLT = new LanguageText(gp, am, "Bomber", "HP_Remaining");
    timeLeftLT = new LanguageText(gp, am, "Bomber", "Time_Left");

    updateChildren();
    super.init();
  }

  /** Reset the game so that it may be played again. */
  void restartEngine() {
    getChildren().clear();
    init();
  }

  /**
   * Draw the objects in this game as well as the in-game stats.
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    // Fill background with White
    canvas.drawColor(bgPaintCan.getPaint().getColor());

    canvas.drawText(
        timeLeftLT.getValue() + ": " + Math.floor(gameTimer / 1000) + "s",
        new Vector(100, 10),
        textPaintCan);
    canvas.drawText(
        bombsPlacedLT.getValue() + ": " + meBomberMan.getNumBombsPlaced(),
        new Vector(550, 0),
        textPaintCan);
    canvas.drawText(
        damageDealtLT.getValue() + ": " + meBomberMan.getDamageDealt(),
        new Vector(1000, 0),
        textPaintCan);
    canvas.drawText(
        hpRemainingLT.getValue() + ": " + meBomberMan.getHp(), new Vector(1450, 0), textPaintCan);

    canvas.drawText(
        "Max Bombs: " + meBomberMan.getNumSimultaneousBombs(), new Vector(450, 920), textPaintCan);
    canvas.drawText(
        "Bomb Strength: " + meBomberMan.getBombStrength(), new Vector(850, 910), textPaintCan);
  }

  /**
   * Update the state of the game, including timer and children to manage.
   *
   * @param ms Milliseconds Since Last Update
   */
  @Override
  public void update(long ms) {
    if (!gameEnded) {
      if (gameTimer <= 0) {
        endGame(true);
      } else {
        // gameTimer > 0
        gameTimer -= ms;
        updateChildren();
      }
    }
  }

  /**
   * Update this object's children. Add and remove children to be added/removed. Can cause
   * ConcurrentModification errors if not called within this game's update function.
   */
  private void updateChildren() {
    for (GameObject item : itemsToBeAdopted) {
      adopt(item);
      item.init();
    }
    itemsToBeAdopted.clear();

    for (GameObject item : itemsToBeRemoved) {
      UUID objID = item.getUUID();
      Map<UUID, GameObject> gameChildren = getChildren();
      gameChildren.remove(objID);
    }
    itemsToBeRemoved.clear();
  }

  /** Send game statistics. */
  private void sendStats() {
    if (myStatRepo != null) {

      myStatRepo.put(
          IStatisticFactory.createGameStatistic(
              StatisticKey.BOMBER_BOMBS_PLACED.getValue() + startTime,
              meBomberMan.getNumBombsPlaced()));
      myStatRepo.put(
          IStatisticFactory.createGameStatistic(
              StatisticKey.BOMBER_DAMAGE_DEALT.getValue() + startTime,
              meBomberMan.getDamageDealt()));
      myStatRepo.put(
          IStatisticFactory.createGameStatistic(
              StatisticKey.BOMBER_HP_REMAINING.getValue() + startTime, meBomberMan.getHp()));
    }
  }

  /** Add obj to list of items to be adopted next time it is safe to do so. */
  public void adoptLater(GameObject obj) {
    itemsToBeAdopted.add(obj);
  }
  /** Add obj to list of items to be removed next time it is safe to do so. */
  public void removeLater(GameObject obj) {
    itemsToBeRemoved.add(obj);
  }

  /** Method that is called when gameOverMenu needs to notify this object. */
  private void observeGameOverMenu(Observable observable, ObservationEvent event) {
    if (event.getMsg().equals("To menu")) {
      Log.i("BomberEngine", "To menu");
      notifyObservers(new ObservationEvent("To menu"));
    }
  }

  /** End the game and open gameOverMenu. */
  private void endGame(boolean finished) {
    if (finished) {
      sendStats();
    }
    gameEnded = true;
    gameOverMenu.setEnable(true);
  }
}
