package com.group0565.bomberGame;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.math.Vector;

public class BomberMainActivity extends GameActivity {

  public BomberMainActivity() {
    super(
        new BomberGame(new Vector())
            .setGlobalPreferences(new GlobalPreferences(GlobalPreferences.Theme.DARK, "en", 1.0)));
  }
}
