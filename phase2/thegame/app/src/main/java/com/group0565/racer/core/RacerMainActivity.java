package com.group0565.racer.core;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.enums.Orientation;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.theme.Themes;

/**
 * RacerMainActivity
 */
public class RacerMainActivity extends GameActivity {

  /**
   * MainActivity of the Game
   */
  public RacerMainActivity() {
    super(new RacerGame(), 60, Orientation.Portrait);
    IPreferenceInteractor prefInter = PreferencesInjector.inject();
    this.getGame()
        .setGlobalPreferences(
            new GlobalPreferences(
                Themes.valueOf(prefInter.getTheme()),
                prefInter.getLanguage(),
                prefInter.getVolume()));
  }
}
