package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;

public class ObstacleManager extends GameObject {

    private long totalTime = 0;
    private long spawnTime = 0;
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
        this.spawnTime += ms;
        this.totalTime += ms;
        if (this.spawnTime >= 2000) {
            spawnObstacle();
            this.spawnTime = 0;
            parent.updateDB(totalTime);
        }
    }

}
