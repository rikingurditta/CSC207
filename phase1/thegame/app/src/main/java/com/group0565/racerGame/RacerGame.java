package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class RacerGame extends GameObject implements Observer {

    Button leftButton;
    Button middleButton;
    Button rightButton;

    RacerGame(Vector position) {
        super(position);
    }

    public void init(){
        leftButton = new Button(new Vector(100, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(0, 1));
        middleButton = new Button(new Vector(500, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(0, 1));
        rightButton = new Button(new Vector(900, 1750), new Vector(150, 150), getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(0, 0), getEngine().getGameAssetManager().getTileSheet("Test", "Test1").getTile(0, 1));
        this.adopt(leftButton);
        this.adopt(middleButton);
        this.adopt(rightButton);
        leftButton.registerObserver(this);
        middleButton.registerObserver(this);
        rightButton.registerObserver(this);
        this.adopt(new Racer(new Vector(500, 100), 0).setAbsolutePosition(new Vector(500, 100)));
        spawnObstacle();
    }

    private void spawnObstacle() {
        double d = Math.random();
        int lane = getRNG().nextInt(3) + 1;
        if (d < 0.5) {
            this.adopt(new SquareObstacle(lane, 0));
        }
        else {
            this.adopt(new CircleObstacle(lane, 0));
        }
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
