package com.group0565.tsu.core;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.hitObjectsRepository.HitObjectsRepositoryInjector;
import com.group0565.hitObjectsRepository.ISessionHitObjectsRepository;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.tsu.game.Beatmap;
import com.group0565.tsu.game.TsuEngine;
import com.group0565.tsu.menus.BeatmapMenu;
import com.group0565.tsu.menus.StatsMenu;
import com.group0565.tsu.menus.TsuMenu;

import java.util.ArrayList;
import java.util.List;

public class TsuGame extends GameObject {
    private TsuMenu menu;
    private StatsMenu stats;
    private TsuEngine engine;
    private BeatmapMenu beatmapMenu;
    private ISessionHitObjectsRepository repository;

    public TsuGame() {
        this.stats = new StatsMenu();
        TsuPreferences preferences = new TsuPreferences();
        preferences.reload();
        this.setGlobalPreferences(preferences);

        //Hook up repository listener to stats menu's history
        HitObjectsRepositoryInjector.inject(
                repository -> {
                    this.repository = repository;
                    repository.getAll(stats::setHistory);
                }
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

        menu.registerObserver(this::observeMenu);
        stats.registerObserver(this::observeStats);
        beatmapMenu.registerObserver(this::observeBeatmapMenu);
        engine.registerObserver(this::observeEngine);
    }

    @Override
    public void postInit() {
        super.postInit();
        this.engine.resetGame();
        this.engine.startGame();
    }

    /**
     * Observer method for TsuMenu
     */
    private void observeMenu(Observable observable, ObservationEvent event) {
        if (event.getMsg().equals(TsuMenu.TO_GAME)) {
            menu.setEnable(false);
            beatmapMenu.setEnable(true);
        }
    }

    /**
     * Observer method for StatsMenu
     */
    private void observeStats(Observable observable, ObservationEvent event) {
        if (event.isEvent(StatsMenu.EXIT_EVENT)) {
            beatmapMenu.setEnable(true);
            stats.setEnable(false);
        } else if (event.isEvent(StatsMenu.TO_REPLAY)) {
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
    }

    /**
     * Observer method for Beatmap Menu
     */
    private void observeBeatmapMenu(Observable observable, ObservationEvent event) {

        if (event.isEvent(BeatmapMenu.STATS_EVENT)) {
            Object payload = event.getPayload();
            if (payload instanceof Beatmap) {
                stats.setSelectedBeatmap((Beatmap) payload);
                beatmapMenu.setEnable(false);
                stats.setEnable(true);
            }
        } else if (event.isEvent(BeatmapMenu.PLAY_EVENT)) {
            Object payload = event.getPayload();
            if (payload instanceof Beatmap) {
                engine.setBeatmap((Beatmap) payload);
                beatmapMenu.setEnable(false);
                engine.setEnable(true);
                engine.startGame();
            }
        } else if (event.isEvent(BeatmapMenu.BACK_EVENT)) {
            beatmapMenu.setEnable(false);
            menu.setEnable(true);
        }
    }

    /**
     * Observer method for Tsu Engine
     */
    private void observeEngine(Observable observable, ObservationEvent event) {
        if (event.isEvent(TsuEngine.GAME_END)) {
            Object payload = event.getPayload();
            if (payload instanceof SessionHitObjects) {
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
        } else if (event.isEvent(TsuEngine.TO_STATS)) {
            stats.setEnable(true);
            engine.resetGame();
            engine.setEnable(false);
        }
    }

    /**
     * Updates the stats in the FireBase Repository
     * @param newList The new list to save in the Repository
     */
    private void updateStats(List<SessionHitObjects> newList) {
        if (repository != null)
            repository.pushList(newList);
    }
}
