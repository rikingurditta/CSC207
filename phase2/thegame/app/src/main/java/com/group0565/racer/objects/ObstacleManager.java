package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.math.Vector;

public class ObstacleManager extends GameMenu {

    public ObstacleManager(Vector size) {
        super(size);
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

    }

    public void spawnSquareObstacle() {

    }
}
