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
        this.build()
                .add("Circle", (new CircleObstacle(this)).build().close())
                .addAlignment(HCenter, THIS, HCenter)
                .addAlignment(Top, THIS, Top)
                .close();


    }

    public void spawnSquareObstacle() {
        this.build()
                .add("Square", (new SquareObstacle(this)).build().close())
                .addAlignment(HCenter, THIS, HCenter)
                .addAlignment(Top, THIS, Top)
                .close();
    }

    public Lane getLane() {
        return lane;
    }
}
