package com.group0565.bomberGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.JoystickInput;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.math.Vector;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;

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
  private int gameTimer = 10000;
  private int bgColor = Color.WHITE;
  boolean sentStats = false;


  /** The repository to interact with the stats DB */
  IAsyncStatisticsRepository myStatRepo;

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
    StatisticRepositoryInjector.inject("Bomber_Game", listener);
  }

  @Override
  public void init() {
    updateChildren();
    super.init();
    if (getGlobalPreferences().theme == GlobalPreferences.Theme.DARK) bgColor = Color.DKGRAY;
  }

  public void draw(Canvas canvas) {
    super.draw(canvas);
    // Fill background with White
    canvas.drawColor(bgColor);

    Paint textPaint = new Paint();
    textPaint.setTextSize(50);
    textPaint.setColor(Color.BLACK);

    canvas.drawText("Time Left:" + Math.floor(gameTimer / 1000) + "s", 1600, 200, textPaint);

  }

  @Override
  public void update(long ms) {
    updateChildren();

    if (gameTimer <= 0 && !sentStats) {
      updateStats();
      sentStats = true;
    }else{
      gameTimer -= ms;
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
    numBombStats = "Bombs Placed:" + meBomberMan.getNumBombsPlaced();
    damageDealtStats = "Damage Dealt:" + meBomberMan.getDamageDealt();


    if (myStatRepo != null) {
      System.out.println("STATS SENT");
      myStatRepo.put(IStatisticFactory.createGameStatistic("Bombs_Placed", meBomberMan.getNumBombsPlaced()));
      myStatRepo.put(IStatisticFactory.createGameStatistic("Damage_Dealt", meBomberMan.getNumBombsPlaced()));
      myStatRepo.put(IStatisticFactory.createGameStatistic("Health_Left", meBomberMan.getHp()));

    }
  }

  public void adoptLater(GameObject obj) {
    itemsToBeAdopted.add(obj);
  }

  public void removeLater(GameObject obj) {
    itemsToBeRemoved.add(obj);
  }
}
