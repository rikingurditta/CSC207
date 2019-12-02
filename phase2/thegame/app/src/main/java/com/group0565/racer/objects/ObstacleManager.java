package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;

class ObstacleManager extends GameMenu implements Observable {

  /** The observation message passed when a collision occurs. */
  private static final String COLLISION_MESSAGE = "Collision";

  /** The starting relative position of the obstacle manager. */
  private static final Vector STARTING_RELATIVE_POSITION = new Vector(0, 0);

  /** The lane that this ObstacleManager is in. */
  private Lane lane;

  ObstacleManager(Vector size, Lane lane) {
    super(size);
    this.lane = lane;
  }

  /**
   * Spawn an Obstacle from this ObstacleManager. Randomly choose between SquareObstacle
   * CircleObstacle.
   */
  void spawnObstacle() {
    double d = Math.random();

    if (d > 0.5) {
      spawnCircleObstacle();
    } else {
      spawnSquareObstacle();
    }
  }

  /** Spawn a CircleObstacle from this ObstacleManager. */
  private void spawnCircleObstacle() {
    Obstacle circle = new CircleObstacle(this);
    circle.registerObserver(this::observeObstacle);
    this.adopt(circle);
    circle.init();
    circle.setRelativePosition(STARTING_RELATIVE_POSITION);
  }

  /** Spawn a SquareObstacle from this ObstacleManager. */
  private void spawnSquareObstacle() {
    Obstacle square = new SquareObstacle(this);
    square.registerObserver(this::observeObstacle);
    this.adopt(square);
    square.init();
    square.setRelativePosition(STARTING_RELATIVE_POSITION);
  }

  /** Return the Lane that this ObstacleManager is in. */
  Lane getLane() {
    return lane;
  }

  /** Observe when a collision with one of the obstacles in this lane. */
  private void observeObstacle(Observable observable, ObservationEvent observationEvent) {
    if (observationEvent.getMsg().equals(COLLISION_MESSAGE)) {
      notifyObservers(observationEvent);
    }
  }
}
