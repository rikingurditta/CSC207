package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class GameOverMenu extends Menu implements Observer {

    /**
     * A Button that restarts the current RacerGame
     */
    private Button restartButton;

    /**
     * A constructor for a GameOverMenu object
     * @param z the rendering level of this object
     */
    GameOverMenu(double z){
        super(z);
        restartButton = new Button(new Vector(600, 900), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
    }

    /**
     * Visually renders the object onto the screen
     */
    @Override
    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        canvas.drawRect(getAbsolutePosition().getX() - 75,
                getAbsolutePosition().getY() - 75, getAbsolutePosition().getX() + 75,
                getAbsolutePosition().getY() + 75, colour);
    }


    public void observe(Observable observable) {
        if (observable == restartButton) {
        }
    }
}
