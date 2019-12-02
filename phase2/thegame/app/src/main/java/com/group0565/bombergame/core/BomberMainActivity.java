package com.group0565.bombergame.core;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.theme.Themes;

/** The main activity of the game. */
public class BomberMainActivity extends GameActivity {

  public BomberMainActivity() {
    // create a new game and inject global preferences into it
    super(new BomberGame());
    IPreferenceInteractor prefInter = PreferencesInjector.inject();
    this.getGame()
        .setGlobalPreferences(
            new GlobalPreferences(
                Themes.valueOf(prefInter.getTheme()),
                prefInter.getLanguage(),
                prefInter.getVolume()));
  }
}
