package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

import java.util.HashSet;

public class Racer extends GameObject {

    private int lane;

    public Racer(Vector position, double z) {
        super(position, z);
        this.lane = 0;
    }


    /**
     * Setter method for the lane attribute of this Racer Object
     * @param lane Set the lane attribute to parameter lane.
     */
    public void setLane(int lane) {
        this.lane = lane;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint colour = new Paint();
        colour.setARGB(255,77,166,255);

        canvas.drawCircle(getAbsolutePosition().getX(),
                getAbsolutePosition().getY(),
                50, colour);
    }

    @Override
    public void update(long ms) {
//        Vector position = this.getAbsolutePosition();
//        Vector delta = new Vector();
//        float speed = 0.1f;
//        delta = delta.add(new Vector(-speed, 0));
//        delta = delta.multiply(ms);
//        this.setAbsolutePosition(position.add(delta));
    }
}
