package com.group0565.racerGame;

import com.group0565.engine.android.GameActivity;
import com.group0565.engine.enums.Orientation;
import com.group0565.math.Vector;

public class RacerMainActivity extends GameActivity {

    public RacerMainActivity(){
        super(new RacerGame(new Vector()), 60, Orientation.Portrait);
    }
}