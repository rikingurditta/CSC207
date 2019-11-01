package com.group0565.racerGame;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.enums.Orientation;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.math.Vector;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.theme.Themes;

public class RacerMainActivity extends GameActivity {

    public RacerMainActivity() {
        super(new RacerGame(new Vector()), 60, Orientation.Portrait);
        IPreferenceInteractor prefInter = PreferencesInjector.inject();
        this.getGame()
                .setGlobalPreferences(
                        new GlobalPreferences(
                                Themes.valueOf(prefInter.getTheme()),
                                prefInter.getLanguage(),
                prefInter.getVolume()));
    }
}
