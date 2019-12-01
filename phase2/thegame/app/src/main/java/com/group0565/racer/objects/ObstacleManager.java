package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;

import java.util.ArrayList;

public class ObstacleManager extends GameMenu implements Observable {

    public static final String DEAD_MESSAGE = "Dead";
    public static final String COLLISION_MESSAGE = "Collision";
    public static final Vector STARTING_RELATIVE_POSITION = new Vector(0, 0);
    private Lane lane;

    public static int runCounter = 0;

    private ArrayList<Obstacle> deadObstacles = new ArrayList<Obstacle>();

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
        Obstacle circle = new CircleObstacle(this);
        circle.registerObserver(this::observeObstacle);
        this.adopt(circle);
        circle.init();
        circle.setRelativePosition(STARTING_RELATIVE_POSITION);
    }

    public void spawnSquareObstacle() {
        Obstacle square = new SquareObstacle(this);
        square.registerObserver(this::observeObstacle);
        this.adopt(square);
        square.init();
        square.setRelativePosition(STARTING_RELATIVE_POSITION);
    }

    public Lane getLane() {
        return lane;
    }

    public void update(long ms) {
//        for (Obstacle obstacle: deadObstacles) {
//            getChildren().remove(obstacle.getUUID());
//            System.out.println(runCounter++);
//        }
//        deadObstacles.clear();
    }

    public void observeObstacle(Observable observable, ObservationEvent observationEvent) {
        if (observationEvent.getMsg().equals(DEAD_MESSAGE)) {
            Obstacle obstacle = (Obstacle) observable;
            deadObstacles.add(obstacle);
        } else if (observationEvent.getMsg().equals(COLLISION_MESSAGE)) {
            notifyObservers(observationEvent);
        }
    }
}
