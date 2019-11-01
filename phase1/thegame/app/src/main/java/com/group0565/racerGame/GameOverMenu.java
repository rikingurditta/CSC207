package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.math.Vector;

public class GameOverMenu extends Menu {

    /**
     * A Button that restarts the current RacerGame
     */
    private Button restartButton;

    GameOverMenu(double z){
        super(z);
        restartButton = new Button(new Vector(600, 900), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
    }


    @Override
    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        canvas.drawRect(getAbsolutePosition().getX() - 75,
                getAbsolutePosition().getY() - 75, getAbsolutePosition().getX() + 75,
                getAbsolutePosition().getY() + 75, colour);
    }
}
