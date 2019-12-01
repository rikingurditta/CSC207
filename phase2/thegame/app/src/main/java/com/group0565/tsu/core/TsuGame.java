package com.group0565.tsu.core;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.hitObjectsRepository.HitObjectsRepositoryInjector;
import com.group0565.hitObjectsRepository.ISessionHitObjectsRepository;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.tsu.game.Beatmap;
import com.group0565.tsu.game.TsuEngine;
import com.group0565.tsu.menus.BeatmapMenu;
import com.group0565.tsu.menus.TsuMenu;
import com.group0565.tsu.menus.StatsMenu;

import java.util.ArrayList;
import java.util.List;

public class TsuGame extends GameObject implements EventObserver {
    private TsuMenu menu;
    private StatsMenu stats;
    private TsuEngine engine;
    private BeatmapMenu beatmapMenu;
    private ISessionHitObjectsRepository repository;
    private TsuPreferences preferences;

    public TsuGame(){
        this.stats = new StatsMenu();
        preferences = new TsuPreferences();
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
        this.menu.setEnable(true);
        this.adopt(menu);

        this.engine = new TsuEngine();
        this.adopt(engine);
        this.engine.setEnable(false);

        this.stats.setZ(1);
        this.stats.setEnable(false);
        this.adopt(stats);

        this.beatmapMenu = new BeatmapMenu();
        this.beatmapMenu.setEnable(false);
        this.adopt(beatmapMenu);

        menu.registerObserver(this);
        stats.registerObserver(this);
        beatmapMenu.registerObserver(this);
        engine.registerObserver(this);
    }

    @Override
    public void postInit() {
        super.postInit();
        this.engine.resetGame();
        this.engine.startGame();
    }

    @Override
    public void observe(Observable observable, ObservationEvent event) {
        if (observable == menu) {
            if (event.getMsg().equals(TsuMenu.TO_GAME)) {
                menu.setEnable(false);
                beatmapMenu.setEnable(true);
            }
        }
        else if (observable == stats) {
            if (event.isEvent(StatsMenu.EXIT_EVENT)){
                beatmapMenu.setEnable(true);
                stats.setEnable(false);
            }else if(event.isEvent(StatsMenu.TO_REPLAY)){
                if (event.getPayload() instanceof SessionHitObjects) {
                    SessionHitObjects sessionHitObjects = (SessionHitObjects) event.getPayload();
                    stats.setEnable(false);
                    engine.setBeatmap(beatmapMenu.getSelectedBeatmap());
                    engine.setSource(StatsMenu.TO_REPLAY);
                    engine.setReplayGenerator(sessionHitObjects.getArchive());
                    engine.setEnable(true);
                    engine.startGame();
                }
            }
        }else if (observable == beatmapMenu){
            if (event.isEvent(BeatmapMenu.STATS_EVENT)){
                Object payload = event.getPayload();
                if (payload instanceof Beatmap){
                    stats.setSelectedBeatmap((Beatmap) payload);
                    beatmapMenu.setEnable(false);
                    stats.setEnable(true);
                }
            }else if (event.isEvent(BeatmapMenu.PLAY_EVENT)){
                Object payload = event.getPayload();
                if (payload instanceof Beatmap){
                    engine.setBeatmap((Beatmap) payload);
                    beatmapMenu.setEnable(false);
                    engine.setEnable(true);
                    engine.startGame();
                }
            }else if (event.isEvent(BeatmapMenu.BACK_EVENT)){
                beatmapMenu.setEnable(false);
                menu.setEnable(true);
            }
        }
        else if (observable == engine){
            if (event.isEvent(TsuEngine.GAME_END)){
                Object payload = event.getPayload();
                if (payload instanceof SessionHitObjects){
                    List<SessionHitObjects> history = stats.getHistory();
                    history = history == null ? new ArrayList<>() : history;
                    history.add((SessionHitObjects) payload);
                    updateStats(history);
                    stats.setHistory(history);
                    stats.setSelectedObject((SessionHitObjects) payload);
                    stats.setEnable(true);
                } else
                    menu.setEnable(true);
                engine.resetGame();
                engine.setEnable(false);
            }
            else if (event.isEvent(TsuEngine.TO_STATS)) {
                stats.setEnable(true);
                engine.resetGame();
                engine.setEnable(false);
            }
        }
    }

    public void updateStats(List<SessionHitObjects> newList) {
        if (repository != null)
            repository.pushList(newList);
    }
}
