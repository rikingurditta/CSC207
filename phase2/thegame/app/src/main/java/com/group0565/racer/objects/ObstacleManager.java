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
//        this.build()
//                .add("Circle", (new CircleObstacle(new Vector(175, 0), this)).build().close())
//                .addAlignment(HCenter, THIS, HCenter)
//                .addAlignment(Top, THIS, Top)
//                .close();

        this.adopt(new CircleObstacle(new Vector(175, 0), this));


    }

    public void spawnSquareObstacle() {
//        this.build()
//                .add("Square", (new SquareObstacle(new Vector(175, 0), this)).build().close())
//                .addAlignment(HCenter, THIS, HCenter)
//                .addAlignment(Top, THIS, Top)
//                .close();

        this.adopt(new SquareObstacle(new Vector(175, 0), this));
    }

    public Lane getLane() {
        return lane;
    }
}
