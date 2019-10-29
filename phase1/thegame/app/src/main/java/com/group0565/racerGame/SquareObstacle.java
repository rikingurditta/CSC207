package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.math.Vector;

public class SquareObstacle extends Obstacle {

    public SquareObstacle(Vector position, double z) {
        super(position, z);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        colour.setARGB(255,0,0,0);

        canvas.drawRect(getAbsolutePosition().getX() - 100,
                getAbsolutePosition().getY() - 100, getAbsolutePosition().getX() + 100,
                getAbsolutePosition().getY() + 100, colour);
    }
}
