package com.group0565.tsu.core;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.hitObjectsRepository.HitObjectsRepositoryInjector;
import com.group0565.hitObjectsRepository.ISessionHitObjectsRepository;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.tsu.menus.TsuMenu;
import com.group0565.tsu.menus.StatsMenu;
import com.group0565.tsu.game.TsuEngine;

import java.util.ArrayList;
import java.util.List;

public class TsuGame extends GameObject implements EventObserver {
    private TsuMenu menu;
    private StatsMenu stats;
    private TsuEngine engine;
    private ISessionHitObjectsRepository repository;
    private Preferences preferences;

    public TsuGame(){
        this.stats = new StatsMenu();
        preferences = new Preferences();
        preferences.reload();
        this.setGlobalPreferences(preferences);
        HitObjectsRepositoryInjector.inject(
                repository -> {this.repository = repository;
                repository.getAll(stats::setHistory);}
        );
    }

    @Override
    public void init() {
        super.init();
        this.menu = new TsuMenu();

        this.engine = new TsuEngine();
        this.engine.setEnable(false);
        this.stats.setZ(1);
        this.stats.setEnable(false);
        this.adopt(engine);
        this.adopt(stats);
        this.adopt(menu);
        menu.registerObserver(this);
        stats.registerObserver(this);
        engine.registerObserver(this);
    }

    @Override
    public void postInit() {
        super.postInit();
        this.engine.start();
    }

    @Override
    public void observe(Observable observable, ObservationEvent event) {
        if (observable == menu) {
            if (event.getMsg().equals(TsuMenu.TO_GAME)) {
                menu.setEnable(false);
                engine.setDifficulty(preferences.getDifficulty());
                engine.setAuto(preferences.getAuto());
                engine.setEnable(true);
                if (engine.isPaused())
                    engine.restartEngine();
                else
                    engine.startEngine();
            } else if (event.getMsg().equals(TsuMenu.TO_STATS)) {
                menu.setEnable(false);
                stats.setEnable(true);
            }
        } else
        if (observable == stats) {
//            if (stats.isExit()) {
//                stats.setExit(false);
//                stats.setEnable(false);
//                menu.setEnable(true);
//            }
        } else if (observable == engine) {
            if (engine.hasEnded()) {
                SessionHitObjects objects = engine.getSessionHitObjects();
                engine.setEnable(false);
                engine.setEnded(false);
                if (objects != null){
                    if (stats.getHistory() == null)
                        stats.setHistory(new ArrayList<>());
                    stats.getHistory().add(objects);
                    updateStats(stats.getHistory());
//                    stats.setSelectedObject(objects);
                    stats.setEnable(true);
                }else
                    menu.setEnable(true);
            }
        }
    }

    public void updateStats(List<SessionHitObjects> newList) {
        if (repository != null)
            repository.pushList(newList);
    }
}
