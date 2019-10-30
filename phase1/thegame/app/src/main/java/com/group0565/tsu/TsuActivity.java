package com.group0565.tsu;

import com.group0565.engine.android.GameActivity;
import com.group0565.tsu.gameObjects.TsuGame;

public class TsuActivity extends GameActivity {
    public TsuActivity() {
        super(new TsuGame());
    }
}
