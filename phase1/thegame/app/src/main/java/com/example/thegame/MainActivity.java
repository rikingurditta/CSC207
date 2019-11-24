package com.example.thegame;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.math.Vector;


public class MainActivity extends GameActivity {
    private static final String TAG = "MainActivity";


    public MainActivity() {
        super(new TestMenu(null).setGlobalPreferences(new GlobalPreferences()));
    }
}
