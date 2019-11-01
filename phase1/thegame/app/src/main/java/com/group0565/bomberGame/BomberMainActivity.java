package com.group0565.bomberGame;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.math.Vector;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;


public class BomberMainActivity extends GameActivity {

  public BomberMainActivity() {

    super(new BomberGame(new Vector()));
    IPreferenceInteractor prefInter = PreferencesInjector.inject();
    this.getGame();
    //this.getGame().setGlobalPreferences(new GlobalPreferences(Themes.valueOf(prefInter.getTheme()), prefInter.getLanguage(), prefInter.getVolume()))

  }
}
