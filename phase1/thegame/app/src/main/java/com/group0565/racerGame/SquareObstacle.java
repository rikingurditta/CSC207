package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.math.Vector;

public class SquareObstacle extends Obstacle {

    SquareObstacle(int lane, double z) {
        super(z, lane);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        colour.setARGB(255,0,0,0);

        canvas.drawRect(getAbsolutePosition().getX() - 75,
                getAbsolutePosition().getY() - 75, getAbsolutePosition().getX() + 75,
                getAbsolutePosition().getY() + 75, colour);
    }
}
