package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class RacerGame extends GameObject implements Observer {

    private Button leftButton;
    private Button middleButton;
    private Button rightButton;
    private ObstacleManager obsManager;
    private Racer racer;

    RacerGame(Vector position) {
        super(position);
    }

    public void init(){
        leftButton = new Button(new Vector(100, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        middleButton = new Button(new Vector(475, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        rightButton = new Button(new Vector(850, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        this.adopt(leftButton);
        this.adopt(middleButton);
        this.adopt(rightButton);
        leftButton.registerObserver(this);
        middleButton.registerObserver(this);
        rightButton.registerObserver(this);
        racer = new Racer(new Vector(500, 1000), 0);
        this.adopt(racer);
        obsManager = new ObstacleManager(this);
        this.adopt(obsManager);
        super.init();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().theme == GlobalPreferences.Theme.LIGHT) {
            // Set background to white
            canvas.drawRGB(255, 255, 255);
        } else {
            // Set background to black
            canvas.drawRGB(0, 0, 0);
        }
        // Set the colour of the lines
        Paint colour = new Paint();
        colour.setARGB(255, 255, 0, 0);
        // Draw the red lines that separate the lanes
        canvas.drawRect(canvas.getWidth() / 3 - 15, 0, canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawRect(2 * canvas.getWidth() / 3 - 15, 0, 2 * canvas.getWidth() / 3 + 15, 2500, colour);
    }

    public void observe(Observable observable) {
        if (observable == leftButton) {
            racer.setAbsolutePosition(new Vector(100, 1750));
            racer.setLane(0);
        } else if (observable == middleButton) {
            racer.setAbsolutePosition(new Vector(475, 1750));
            racer.setLane(1);
        } else if (observable == rightButton) {
            racer.setAbsolutePosition(new Vector(850, 1750));
            racer.setLane(2);
        }
    }

    /**
     * Getter method that returns this game's Racer object
     * @return Racer object
     */

    Racer getRacer() {
        return racer;
    }
}
