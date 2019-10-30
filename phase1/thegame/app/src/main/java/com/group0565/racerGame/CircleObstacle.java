package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.math.Vector;

public class CircleObstacle extends Obstacle {

    CircleObstacle(int lane, double z) {
        super(z, lane);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        colour.setARGB(255,0,0,0);

        canvas.drawCircle(getAbsolutePosition().getX(),
                getAbsolutePosition().getY(),
                75, colour);
    }
}
