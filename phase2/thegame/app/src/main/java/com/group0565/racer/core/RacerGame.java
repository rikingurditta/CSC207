package com.group0565.racer.core;

import com.group0565.engine.gameobjects.GameObject;

/** The Game class for Racer. */
public class RacerGame extends GameObject {

  /** The constructor for RacerGame. */
  RacerGame() {}

  /** The initialization method for RacerGame. */
  public void init() {
    RacerEngine engine = new RacerEngine();

    engine.setEnable(true);

    this.adopt(engine);

    super.init();
  }
}
