package com.group0565.tsu.gameObjects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.preferences.PreferencesInjector;

public class TsuGame extends GameObject implements Observer {
    private TsuMenu menu;
    private StatsMenu stats;
    private TsuEngine engine;

    @Override
    public void init() {
        this.menu = new TsuMenu();
        this.engine = new TsuEngine();
        this.engine.setEnable(false);
        this.stats = new StatsMenu();
        this.stats.setEnable(false);
        this.adopt(engine);
        this.adopt(stats);
        this.adopt(menu);
        menu.registerObserver(this);
        stats.registerObserver(this);
        engine.registerObserver(this);
        super.init();
    }

    @Override
    public void observe(Observable observable) {
        if (observable == menu) {
            if (menu.hasStarted()) {
                menu.setStarted(false);
                menu.setEnable(false);
                engine.setDifficulty(menu.getDifficulty());
                engine.setEnable(true);
                if (engine.isPaused())
                    engine.restartEngine();
                else
                    engine.startEngine();
            } else if (menu.isStats()) {
                menu.setStats(false);
                menu.setEnable(false);
                stats.setEnable(true);
            }
        } else if (observable == stats) {
            if (stats.isExit()) {
                stats.setExit(false);
                stats.setEnable(false);
                menu.setEnable(true);
            }
        } else if (observable == engine) {
            if (engine.hasEnded()) {
                SessionHitObjects objects = engine.getSessionHitObjects();
                engine.setEnable(false);
                engine.setEnded(false);
                if (objects != null){
                    stats.setActive(objects);
                    stats.setEnable(true);
                }else
                    menu.setEnable(true);
            }
        }
    }

    public void setPreferences(String s) {
//        PreferencesInjector.inject().updatePreference();
    }
}
