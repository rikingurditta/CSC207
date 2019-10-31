package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.math.Vector;

public class CircleObstacle extends Obstacle {

    CircleObstacle(int lane, double z, ObstacleManager parent) {
        super(z, lane, parent);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        if (isCollided()) {
            colour.setARGB(255, 0, 255, 0);
        }
        else {
            colour.setARGB(255, 255,0,0);
        }

        canvas.drawCircle(getAbsolutePosition().getX(),
                getAbsolutePosition().getY(),
                75, colour);
    }
}
