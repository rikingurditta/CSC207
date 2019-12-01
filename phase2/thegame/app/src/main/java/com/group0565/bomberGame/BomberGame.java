package com.group0565.bomberGame;

import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.JoystickInput;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Canvas;
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

public class BomberGame extends GameObject {

  /** Create a STRONG reference to the listener so it won't get garbage collected */
  StatisticRepositoryInjector.RepositoryInjectionListener listener;

  private ArrayList<GameObject> itemsToBeAdopted = new ArrayList<>();
  private ArrayList<GameObject> itemsToBeRemoved = new ArrayList<>();
  private BomberMan meBomberMan;
  private int gameTimer = 60000;
  private boolean gameEnded = false;
  private long startTime;
  /** The repository to interact with the stats DB */
  private IAsyncStatisticsRepository myStatRepo;

  private LanguageText bombsPlacedLT;
  private LanguageText damageDealtLT;
  private LanguageText hpRemainingLT;
  private LanguageText timeLeftLT;
  private LanguageText gameOverLT;
  private ThemedPaintCan bgPaintCan = new ThemedPaintCan("Bomber", "Background.Background");
  private ThemedPaintCan textPaintCan = new ThemedPaintCan("Bomber", "Text.Text");

  public BomberGame(Vector position) {
    super(position);
    InputSystem joystickInput =
        new JoystickInput(new Vector(150, 750), 100, new Vector(0, 0), new Vector(1700, 100), 100);
    adopt(joystickInput);
    InputSystem randomInput = new RandomInput(1000);
    adopt(randomInput);
    SquareGrid grid = new SquareGrid(new Vector(100, 100), 0, 15, 8, 100, this);
    adopt(grid);
    BomberMan bm = new BomberMan(new Coords(0, 0), 20, joystickInput, this, grid, 10);
    adopt(bm);
    meBomberMan = bm;
    BomberMan bm2 = new BomberMan(new Coords(10, 6), 20, randomInput, this, grid, 10);
    adopt(bm2);
    // make 25 crates
    for (int i = 0; i < 25; i++) grid.makeRandomCrate();

    listener =
        repository -> {
          myStatRepo = repository;
        };
    StatisticRepositoryInjector.inject("BomberGame", listener);
  }

  @Override
  public void init() {
    updateChildren();
    super.init();
    startTime = System.currentTimeMillis();

    GlobalPreferences gp = getGlobalPreferences();
    GameAssetManager am = getEngine().getGameAssetManager();
    bgPaintCan.init(gp, am);
    textPaintCan.init(gp, am);
    gameOverLT = new LanguageText(gp, am, "Bomber", "Game_Over");
    bombsPlacedLT = new LanguageText(gp, am, "Bomber", "Bombs_Placed");
    damageDealtLT = new LanguageText(gp, am, "Bomber", "Damage_Dealt");
    hpRemainingLT = new LanguageText(gp, am, "Bomber", "HP_Remaining");
    timeLeftLT = new LanguageText(gp, am, "Bomber", "Time_Left");
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    // Fill background with White
    canvas.drawColor(bgPaintCan.getPaint().getColor());

    if (!gameEnded) {
      canvas.drawText(
          timeLeftLT.getValue() + ": " + Math.floor(gameTimer / 1000) + "s",
          new Vector(1600, 200),
          textPaintCan);
    } else {
      canvas.drawText(gameOverLT.getValue(), new Vector(1600, 200), textPaintCan);
    }
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
    updateChildren();

    if (gameTimer <= 0 && !gameEnded) {
      sendStats();
      gameEnded = true;

    } else {
      if (gameEnded) {
        // TODO need to write code from proper game ending procedure
        System.out.println("game ended");
      } else {
        // gameTimer > 0
        gameTimer -= ms;
      }
    }
  }

  private void updateChildren() {
    for (GameObject item : itemsToBeAdopted) {
      this.adopt(item);
      item.init();
    }
    itemsToBeAdopted.clear();

    for (GameObject item : itemsToBeRemoved) {
      UUID objID = item.getUUID();
      Map<UUID, GameObject> gameChildren = this.getChildren();
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
}
