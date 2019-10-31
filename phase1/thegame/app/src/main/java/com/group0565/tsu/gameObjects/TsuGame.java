package com.group0565.tsu.gameObjects;

import android.content.res.Resources;

import com.example.thegame.R;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.preferences.UserPreferenceFactory;
import com.thegame.TheGameApplication;

public class TsuGame extends GameObject implements Observer {
    private String ThemePrefName = "";
    private String VolumePrefName = "";
    private String LanguagePrefName = "";
    private String DifficultyPrefName = "";
    private TsuMenu menu;
    private StatsMenu stats;
    private TsuEngine engine;

    @Override
    public void init() {
        Resources resources = TheGameApplication.getInstance().getResources();
        ThemePrefName = resources.getString(R.string.theme_pref_id);
        LanguagePrefName = resources.getString(R.string.lan_pref_id);
        VolumePrefName = resources.getString(R.string.vol_pref_id);
        DifficultyPrefName = "tsu-difficulty";

        this.menu = new TsuMenu(this);
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
}
