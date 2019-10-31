package com.group0565.racerGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

public abstract class Obstacle extends GameObject {

    private int lane;
    private ObstacleManager obsManager;
    // Need to add falling faster over time functionality
    private float speed = 0.1f;
    private boolean collided = false;

    Obstacle(int lane, double z, ObstacleManager parent) {
        super(new Vector(lane * 275, 0), z);
        this.lane = lane;
        this.obsManager = parent;
    }

    /**
     * checks if this Obstacle object has collided with a Racer. Returns True if the object has
     * collided.
     * @return Returns True or False depending on whether or not the Obstacle has hit the Racer.
     * */
    private void checkCollision() {
        float racerY = obsManager.parent.getRacer().getAbsolutePosition().getY();
        float thisY = this.getAbsolutePosition().getY();
        if (Math.abs(thisY - racerY) <= 50 && lane == obsManager.parent.getRacer().getLane()){
            collided = true;
            obsManager.update(obsManager.getTotalTime() + obsManager.getSpawnTime());
        }
    }

    /**
     * getter method that returns whether or not this object has been collided with
     * @return
     */
    public boolean isCollided() {
        return collided;
    }

    @Override
    public abstract void draw(Canvas canvas);

    @Override
    public void update(long ms) {
        Vector position = this.getAbsolutePosition();
        Vector delta = new Vector();
        delta = delta.add(new Vector(0, speed));
        delta = delta.multiply(ms);
        this.setAbsolutePosition(position.add(delta));
        checkCollision();
    }
}
