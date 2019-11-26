package com.group0565.racerGame;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.racerGame.Menus.RacerMenu;
import com.group0565.racerGame.Obstacles.ObstacleManager;
import com.group0565.statistics.IAsyncStatisticsRepository;
import com.group0565.statistics.IStatisticFactory;
import com.group0565.statistics.StatisticRepositoryInjector;
import com.group0565.statistics.enums.StatisticKey;
import com.group0565.theme.Themes;

public class RacerGame extends GameObject implements Observer {

  private RacerMenu menu;

  private RacerEngine engine;

  public RacerGame() {

  }

  public void init() {
    this.menu = new RacerMenu();
    this.engine =  new RacerEngine();
  }

}
