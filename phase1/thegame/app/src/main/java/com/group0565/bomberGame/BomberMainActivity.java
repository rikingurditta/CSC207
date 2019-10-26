package com.group0565.bomberGame;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public class BomberMainActivity extends GameActivity {


    public BomberMainActivity() {
        super(new BomberGame(null, new Vector(), false));

    }
}

