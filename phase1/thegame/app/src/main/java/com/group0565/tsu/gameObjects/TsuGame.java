package com.group0565.tsu.gameObjects;

import android.content.res.Resources;

import com.example.thegame.R;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.hitObjectsRepository.HitObjectsRepositoryInjector;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.preferences.UserPreferenceFactory;
import com.thegame.TheGameApplication;

import java.util.ArrayList;
import java.util.List;

public class TsuGame extends GameObject implements Observer {
    private String ThemePrefName = "";
    private String VolumePrefName = "";
    private String LanguagePrefName = "";
    private String DifficultyPrefName = "";
    private String AutoPrefName = "";
    private TsuMenu menu;
    private StatsMenu stats;
    private TsuEngine engine;

    public TsuGame(){
        this.stats = new StatsMenu(this);
        HitObjectsRepositoryInjector.inject(
                repository -> repository.getAll(stats::setHistory)
        );
    }

    @Override
    public void init() {
        Resources resources = TheGameApplication.getInstance().getResources();
        ThemePrefName = resources.getString(R.string.theme_pref_id);
        LanguagePrefName = resources.getString(R.string.lan_pref_id);
        VolumePrefName = resources.getString(R.string.vol_pref_id);
        DifficultyPrefName = "tsu-difficulty";
        AutoPrefName = "tsu-auto";
        IPreferenceInteractor prefInter = PreferencesInjector.inject();
        this.menu = new TsuMenu(this);
        Object difficulty;
        if (!((difficulty = prefInter.getPreference(DifficultyPrefName, 5)) instanceof Double) && !(difficulty instanceof Float))
            difficulty = 5D;
        this.menu.setDifficulty((float) (double) difficulty);
        Object auto;
        if (!((auto = prefInter.getPreference(AutoPrefName, false)) instanceof Boolean))
            auto = false;
        this.menu.setAuto((boolean) auto);

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
        super.init();
    }

    @Override
    public void observe(Observable observable) {
        if (observable == menu) {
            if (menu.hasStarted()) {
                menu.setStarted(false);
                menu.setEnable(false);
                engine.setDifficulty(menu.getDifficulty());
                engine.setAuto(menu.getAuto());
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
                if (stats.getHistory() == null)
                    stats.setHistory(new ArrayList<>());
                stats.getHistory().add(objects);
                updateStats(stats.getHistory());
                if (objects != null){
                    stats.setSelectedObject(objects);
                    stats.setEnable(true);
                }else
                    menu.setEnable(true);
            }
        }
    }

    public void updateStats(List<SessionHitObjects> newList) {
        HitObjectsRepositoryInjector.inject(
                repository -> repository.pushList(newList));
    }

    public void setPreferences(String name, Object value) {
        IPreferenceInteractor interactor = PreferencesInjector.inject();
        interactor.updatePreference(UserPreferenceFactory.getUserPreference(name, value));
    }

    public void setPreferences(GlobalPreferences preferences) {
        setPreferences(ThemePrefName, preferences.theme.name());
        setPreferences(VolumePrefName, (int)(preferences.volume * 100));
        setPreferences(LanguagePrefName, preferences.language);
    }

    public String getThemePrefName() {
        return ThemePrefName;
    }

    public String getVolumePrefName() {
        return VolumePrefName;
    }

    public String getLanguagePrefName() {
        return LanguagePrefName;
    }

    public String getDifficultyPrefName() {
        return DifficultyPrefName;
    }

    public String getAutoPrefName() {
        return AutoPrefName;
    }
}
