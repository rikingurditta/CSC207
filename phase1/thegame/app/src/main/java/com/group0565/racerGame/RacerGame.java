package com.group0565.racerGame;

import android.graphics.Canvas;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public class RacerGame extends GameObject {

    RacerGame(Vector position) {
        super(position);
        this.adopt(new Racer(new Vector(), 0).setAbsolutePosition(new Vector(500, 100)));
        this.adopt(new SquareObstacle(new Vector(), 0).setAbsolutePosition(new Vector(250, 100)));
        this.adopt(new CircleObstacle(new Vector(), 0).setAbsolutePosition(new Vector(750, 100)));

    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(255, 255, 255);
    }

}
