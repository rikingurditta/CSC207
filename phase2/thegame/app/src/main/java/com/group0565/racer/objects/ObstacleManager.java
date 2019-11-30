package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.math.Vector;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

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
        CircleObstacle circle = new CircleObstacle(this);

        this.adopt(circle);
        circle.setRelativePosition(new Vector(0, 0));
    }

    public void spawnSquareObstacle() {
        SquareObstacle square = new SquareObstacle(this);

        this.adopt(square);
        square.setRelativePosition(new Vector(0, 0));
    }

    public Lane getLane() {
        return lane;
    }
}
