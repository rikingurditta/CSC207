package com.group0565.racer.core;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.racer.menus.RacerMainMenu;

/**
 * The Game class for Racer.
 */
public class RacerGame extends GameObject implements Observer {

  /**
   * The game screen/menu for Racer.
   */
  private RacerMainMenu menu;

  /**
   * The engine for Racer.
   */
  private RacerEngine engine;

  /**
   * The constructor for RacerGame.
   */
  RacerGame() {
  }

  /**
   * The initialization method for RacerGame.
   */
  public void init() {
    this.menu = new RacerMainMenu();
    this.engine =  new RacerEngine();

    this.engine.setEnable(true);

    this.adopt(menu);
    this.adopt(engine);

    menu.registerObserver(this);
    engine.registerObserver(this);

    super.init();
  }

  /**
   *
   * @param observable
   */
  @Override
  public void observe(Observable observable) {

  }
}
