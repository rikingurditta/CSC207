package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;

import java.util.ArrayList;

public class ObstacleManager extends GameObject {


    private long time = 0;
    RacerGame parent;

    ObstacleManager(RacerGame parent) {
        this.parent = parent;
    }

     private void spawnObstacle() {
        double d = Math.random();
        if (d < 0.5) {
            this.adopt(new SquareObstacle(1, 0, this));
        }
        else {
            this.adopt(new CircleObstacle(2, 0, this));
        }
    }

    @Override
    public void update(long ms) {
        this.time += ms;
        if (this.time >= 2000) {
            spawnObstacle();
            this.time = 0;
        }
    }

}
