package com.group0565.bomberGame.core;

import com.group0565.bomberGame.BomberEngine;
import com.group0565.bomberGame.menus.BomberMenu;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;

public class BomberGame extends GameObject {

  private BomberMenu menu;

  private BomberEngine engine;

  public BomberGame() {}

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

  public void observeMenu(Observable observable, ObservationEvent event) {
    if (event.getMsg().equals("To game")) {
      engine.restartEngine();
      engine.setEnable(true);
      menu.setEnable(false);
    }
  }

  public void observeEngine(Observable observable, ObservationEvent event) {
    if (event.getMsg().equals("To menu")) {
      engine.setEnable(false);
      menu.setEnable(true);
    }
  }
}
