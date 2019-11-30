package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.math.Vector;

public class ObstacleManager extends GameMenu {

    private Lane lane;

    public ObstacleManager(Vector size, Lane lane) {
        super(size);
        this.lane = lane;
    }

    public void spawnObstacle() {
        double d = Math.random();

        if (d > 0.5) {
            spawnCircleObstacle();
        } else {
            spawnSquareObstacle();
        }

    }

    public void spawnCircleObstacle() {
        this.adopt(new CircleObstacle(this));


    }

    public void spawnSquareObstacle() {
        this.adopt(new SquareObstacle(this));
    }

    public Lane getLane() {
        return lane;
    }
}
