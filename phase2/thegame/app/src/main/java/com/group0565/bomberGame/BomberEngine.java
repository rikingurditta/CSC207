package com.group0565.bomberGame;

import android.util.Log;

import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.JoystickInput;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.bomberGame.menus.GameOverMenu;
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

public class BomberEngine extends GameObject implements Observable {
  private int GAME_DURATION = 60000;

  /** Create a STRONG reference to the listener so it won't get garbage collected */
  StatisticRepositoryInjector.RepositoryInjectionListener listener;

  private ArrayList<GameObject> itemsToBeAdopted = new ArrayList<>();
  private ArrayList<GameObject> itemsToBeRemoved = new ArrayList<>();

  /** The user's BomberMan. */
  private BomberMan meBomberMan;

  /** The timer counting how many ms are left in the game. */
  private int gameTimer;

  private boolean gameEnded = false;
  private long startTime;
  /** The repository to interact with the stats DB */
  private IAsyncStatisticsRepository myStatRepo;

  /** LanguageTexts for in-game time/stats displays. */
  private LanguageText bombsPlacedLT;

  private LanguageText damageDealtLT;
  private LanguageText hpRemainingLT;
  private LanguageText timeLeftLT;
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
    StatisticRepositoryInjector.inject("BomberEngine", listener);

    gameEnded = false;

    // set time stuff
    gameTimer = GAME_DURATION;
    startTime = System.currentTimeMillis();

    // TODO: reposition, rescale displayed objects based on screen size

    // TODO: figure out how to properly init without using adoptLater

    // create grid
    SquareGrid grid = new SquareGrid(new Vector(100, 100), 0, 15, 8, 100, this);
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
    gameOverMenu.setAbsolutePosition(new Vector(500, 250));
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

  public void restartEngine() {
    getChildren().clear();
    init();
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    // Fill background with White
    canvas.drawColor(bgPaintCan.getPaint().getColor());

    canvas.drawText(
        timeLeftLT.getValue() + ": " + Math.floor(gameTimer / 1000) + "s",
        new Vector(1600, 200),
        textPaintCan);
    canvas.drawText(
        bombsPlacedLT.getValue() + ": " + meBomberMan.getNumBombsPlaced(),
        new Vector(1600, 260),
        textPaintCan);
    canvas.drawText(
        damageDealtLT.getValue() + ": " + meBomberMan.getDamageDealt(),
        new Vector(1600, 320),
        textPaintCan);
    canvas.drawText(
        hpRemainingLT.getValue() + ": " + meBomberMan.getHp(), new Vector(1600, 380), textPaintCan);
  }

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

  public void sendStats() {
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

  public void adoptLater(GameObject obj) {
    itemsToBeAdopted.add(obj);
  }

  public void removeLater(GameObject obj) {
    itemsToBeRemoved.add(obj);
  }

  public void observeGameOverMenu(Observable observable, ObservationEvent event) {
    if (event.getMsg().equals("To menu")) {
      Log.i("BomberEngine", "To menu");
      notifyObservers(new ObservationEvent("To menu"));
    }
  }

  private void endGame(boolean finished) {
    if (finished) {
      sendStats();
    }
    setEnded(true);
    gameOverMenu.setEnable(true);
  }

  public boolean hasEnded() {
    return gameEnded;
  }

  public void setEnded(boolean ended) {
    gameEnded = ended;
  }
}
