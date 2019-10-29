package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

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
        Paint colour = new Paint();
        colour.setARGB(255, 255, 0, 0);
        super.draw(canvas);
        canvas.drawRGB(255, 255, 255);
        canvas.drawRect(canvas.getWidth() / 3 - 15, 0, canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawRect(2 * canvas.getWidth() / 3 - 15, 0, 2 * canvas.getWidth() / 3 + 15, 2500, colour);
    }

}
