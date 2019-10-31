package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;

public class ObstacleManager extends GameObject {


    private long time = 0;
    RacerGame parent;

    ObstacleManager(RacerGame parent) {
        this.parent = parent;
    }

     private void spawnObstacle() {
        double d = Math.random();
        int randomLane = 1 + (int)(Math.random() * ((3 - 1) + 1));
        if (d < 0.5) {
            this.adopt(new SquareObstacle(randomLane, 0, this));
        }
        else {
            this.adopt(new CircleObstacle(randomLane, 0, this));
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
