package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public abstract class Obstacle extends GameObject {

    public Obstacle(Vector position, double z) {
        super(position, z);
    }

    @Override
    public abstract void draw(Canvas canvas);

    @Override
    public void update(long ms) {
        Vector position = this.getAbsolutePosition();
        Vector delta = new Vector();
        float speed = 0.1f;
        delta = delta.add(new Vector(0, speed));
        delta = delta.multiply(ms);
        this.setAbsolutePosition(position.add(delta));
    }
}
