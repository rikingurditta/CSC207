package com.group0565.bomberGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.JoystickInput;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.statistics.enums.StatisticKey;
import com.group0565.theme.Themes;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.lang.Math;

public class BomberGame extends GameObject {

  private ArrayList<GameObject> itemsToBeAdopted = new ArrayList<GameObject>();
  private ArrayList<GameObject> itemsToBeRemoved = new ArrayList<GameObject>();
  private BomberMan meBomberMan;
  private String numBombStats;
  private String damageDealtStats;
  private String currentHealth;
  private int gameTimer = 60000;
  private int bgColor = Color.DKGRAY;
  private boolean gameEnded = false;

  private long startTime;
  private String statisticName1 = "Bombs placed";
  private String statisticName2 = "Damage dealt";
  private String statisticName3 = "HP remaining";
  private String timeLeftinLang = "Time Left";

  /** The repository to interact with the stats DB */
  private IAsyncStatisticsRepository myStatRepo;

  /** Create a STRONG reference to the listener so it won't get garbage collected */
  StatisticRepositoryInjector.RepositoryInjectionListener listener;

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

    if (getGlobalPreferences().theme == Themes.DARK) {
      bgColor = Color.DKGRAY;
    } else if (getGlobalPreferences().theme == Themes.LIGHT) {
      bgColor = Color.WHITE;
    }

    if (getGlobalPreferences().language.equals("en")) {
      statisticName1 = "Bombs placed";
      statisticName2 = "Damage dealt";
      statisticName3 = "HP remaining";
    } else if (getGlobalPreferences().language.equals("fr")) {
      statisticName1 = "bombes placées";
      statisticName2 = "dégâts infligés";
      statisticName3 = "santé restante";
      timeLeftinLang = "temps restant";
    }
  }

  public void draw(Canvas canvas) {
    super.draw(canvas);
    // Fill background with White
    canvas.drawColor(bgColor);

    Paint textPaint = new Paint();
    textPaint.setTextSize(50);
    textPaint.setColor(Color.BLACK);

    canvas.drawText(timeLeftinLang + Math.floor(gameTimer / 1000) + "s", 1600, 200, textPaint);
    canvas.drawText(numBombStats, 1600, 250, textPaint);
    canvas.drawText(damageDealtStats, 1600, 300, textPaint);
    canvas.drawText(currentHealth, 1600, 350, textPaint);
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
        updateStats();
      }
    }
  }

  private void updateChildren() {
    for (GameObject item : itemsToBeAdopted) {
      this.adopt(item);
    }
    itemsToBeAdopted.clear();

    for (GameObject item : itemsToBeRemoved) {
      UUID objID = item.getUUID();
      Map<UUID, GameObject> gameChildren = this.getChildren();
      gameChildren.remove(objID);
    }
    itemsToBeRemoved.clear();
  }

  public void updateStats() {
    numBombStats = statisticName1 + ": " + meBomberMan.getNumBombsPlaced();
    damageDealtStats = statisticName2 + ": " + meBomberMan.getDamageDealt();
    currentHealth = statisticName3 + ": " + meBomberMan.getHp();
  }

  public void sendStats() {
    timeLeftinLang = "GAME OVER ";
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
