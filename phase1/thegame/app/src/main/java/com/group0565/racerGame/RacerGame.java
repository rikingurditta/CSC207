package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class RacerGame extends GameObject implements Observer {

    private Button leftButton;
    private Button middleButton;
    private Button rightButton;
    private ObstacleManager obsManager;

    RacerGame(Vector position) {
        super(position);
    }

    public void init(){
        leftButton = new Button(new Vector(100, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        middleButton = new Button(new Vector(475, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        rightButton = new Button(new Vector(850, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("RacerButton", "RacerButton").getTile(0, 0));
        obsManager = new ObstacleManager(this);
        this.adopt(leftButton);
        this.adopt(middleButton);
        this.adopt(rightButton);
        leftButton.registerObserver(this);
        middleButton.registerObserver(this);
        rightButton.registerObserver(this);
        this.adopt(new Racer(new Vector(500, 1000), 0));
        this.adopt(obsManager);
    }

    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        colour.setARGB(255, 255, 0, 0);
        super.draw(canvas);
        canvas.drawRGB(255, 255, 255);
        canvas.drawRect(canvas.getWidth() / 3 - 15, 0, canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawRect(2 * canvas.getWidth() / 3 - 15, 0, 2 * canvas.getWidth() / 3 + 15, 2500, colour);
    }

    public void observe(Observable observable) {

    }
}
