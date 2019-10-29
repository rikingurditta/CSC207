package com.group0565.bomberGame.bombs;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.math.Vector;

public class NormalBomb extends Bomb{

    public NormalBomb(Vector position, int z){
        super(position, z);
    }

    @Override
    public void draw(Canvas canvas) {
        // Set our color to Red
        Paint p = new Paint();
        p.setARGB(123, 255, 0, 255);
        // Draw an rectangle at our touch position
        canvas.drawRect(
                getAbsolutePosition().getX(),
                getAbsolutePosition().getY(),
                getAbsolutePosition().getX() + 100,
                getAbsolutePosition().getY() + 100,
                p);
    }
}
