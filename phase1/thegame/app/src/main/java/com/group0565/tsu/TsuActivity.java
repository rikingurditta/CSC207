package com.group0565.tsu;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.tsu.gameObjects.TsuGame;

public class TsuActivity extends GameActivity {
    public TsuActivity() {
        super(new TsuGame().setGlobalPreferences(
                new GlobalPreferences(GlobalPreferences.Theme.DARK,
                        "en",
                        1.0)));
    }
}
