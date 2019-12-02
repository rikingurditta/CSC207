package com.group0565.bombergame.core;

import com.group0565.bombergame.menus.BomberMenu;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;

/** The class setting up the menus and main object(s) of the game. */
public class BomberGame extends GameObject {
  private BomberMenu menu;

  private BomberEngine engine;

  BomberGame() {}

  public void init() {
    menu = new BomberMenu();
    engine = new BomberEngine();

    engine.setEnable(false);
    menu.setEnable(true);

    adopt(menu);
    adopt(engine);

    menu.registerObserver(this::observeMenu);
    engine.registerObserver(this::observeEngine);

    super.init();
  }

  private void observeMenu(Observable observable, ObservationEvent event) {
    if (event.getMsg().equals("To game")) {
      engine.restartEngine();
      engine.setEnable(true);
      menu.setEnable(false);
    }
  }

  private void observeEngine(Observable observable, ObservationEvent event) {
    if (event.getMsg().equals("To menu")) {
      engine.setEnable(false);
      menu.setEnable(true);
    }
  }
}
