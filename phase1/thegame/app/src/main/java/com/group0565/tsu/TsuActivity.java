package com.group0565.tsu;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.hitObjectsRepository.HitObjectsRepositoryInjector;
import com.group0565.hitObjectsRepository.SessionHitObjects;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.theme.Themes;
import com.group0565.tsu.gameObjects.StatsMenu;
import com.group0565.tsu.gameObjects.TsuGame;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TsuActivity extends GameActivity {
    public TsuActivity() {
        super(new TsuGame());
        IPreferenceInteractor prefInter = PreferencesInjector.inject();
        this.getGame()
                .setGlobalPreferences(
                        new GlobalPreferences(
                                Themes.valueOf(prefInter.getTheme()),
                                prefInter.getLanguage(),
                                prefInter.getVolume() / 100D));
    }
}
