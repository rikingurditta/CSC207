package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;

import java.util.ArrayList;

public class ObstacleManager extends GameObject {

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
            this.adopt(new SquareObstacle(lane, 0));
        }
        else {
            this.adopt(new CircleObstacle(lane, 0));
        }
    }

}
