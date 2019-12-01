package com.group0565.racer.core;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;

/**
 * The Game class for Racer.
 */
public class RacerGame extends GameObject implements Observer {


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
    this.engine =  new RacerEngine();

    this.engine.setEnable(true);

    this.adopt(engine);

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
