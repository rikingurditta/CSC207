package com.group0565.tsu.gameObjects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;

public class TsuGame extends GameObject implements Observer {
    private TsuMenu menu;
    private TsuEngine engine;

    @Override
    public void init() {
        this.menu = new TsuMenu();
        this.engine = new TsuEngine();
        this.engine.setEnable(false);
        this.adopt(engine);
        this.adopt(menu);
        menu.registerObserver(this);
        super.init();
    }

    @Override
    public void observe(Observable observable) {
        if (observable == menu) {
            if (menu.hasStarted()) {
                menu.setEnable(false);
                engine.setEnable(true);
                engine.startEngine();
            }
        }
    }
}
