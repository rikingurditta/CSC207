package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;

import java.util.ArrayList;

public class ObstacleManager extends GameObject {


    long time;
    RacerGame parent;
    ArrayList obstacleList;

    ObstacleManager(RacerGame parent) {
        this.parent = parent;
        this.obstacleList = new ArrayList();
    }

    private void spawnObstacle() {
        double d = Math.random();
        int lane = getRNG().nextInt(3) + 1;
        if (d < 0.5) {
            SquareObstacle obs = new SquareObstacle(lane, 0);
            parent.adopt(new SquareObstacle(lane, 0));
        }
        else {
            parent.adopt(new CircleObstacle(lane, 0));
        }
    }

    @Override
    public void update(long ms) {
        this.time += ms;
        if (this.time >= 3000) {
            spawnObstacle();
            this.time = 0;
        }
    }

}
