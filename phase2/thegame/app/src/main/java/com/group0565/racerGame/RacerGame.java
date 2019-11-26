package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.racerGame.Menus.RacerMenu;


public class RacerGame extends GameObject implements Observer {

  private RacerMenu menu;

  private RacerEngine engine;

  public RacerGame() {

  }

  public void init() {
    this.menu = new RacerMenu();
    this.engine =  new RacerEngine();

    this.engine.setEnable(true);

    this.adopt(menu);
    this.adopt(engine);

    menu.registerObserver(this);
    engine.registerObserver(this);

    super.init();
  }

    @Override
    public void observe(Observable observable) {
        if (observable == menu) {

        } else if (observable == engine) {
            if (engine.hasEnded()) {
                engine.setEnable(false);
                engine.setEnded(false);
            }
        }
    }
}
