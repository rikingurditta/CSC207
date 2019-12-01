package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;

/** An Obstacle in Racer */
public abstract class Obstacle extends GameObject implements Observable {

  /*
   * The starting relative position of the Obstacle.
   */
  public static final Vector STARTING_RELATIVE_POSITION = new Vector(0, 0);

  /*
   * The top y-coordinate of the collision hitbox.
   */
  public static final int COLLISION_HIGHER_BOUND = 1550;

  /*
   * The bottom y-coordinate of the collision hitbox.
   */
  public static final int COLLISION_LOWER_BOUND = 1700;

  /*
   * The observation message passed when a collision occurs.
   */
  public static final String COLLISION_MSG = "Collision";

  /**
   * The ObstacleManager for this Obstacle.
   */
  private ObstacleManager obstacleManager;

  // Need to add falling faster over time functionality
  /**
   * The current speed of the Obstacle, used for calculations in update method.
   */
  private float speed = 0.5f;

  /**
   * A boolean value representing whether or not the Racer has hit this Obstacle.
   */
  private boolean collided = false;

  /**
   * Constructs a new Obstacle Object.
   *
   * @param obstacleManager The ObstacleManager for this Obstacle.
   */
  Obstacle(ObstacleManager obstacleManager) {
    super();
    setRelativePosition(STARTING_RELATIVE_POSITION);
    this.obstacleManager = obstacleManager;
  }


  @Override
  public GameObject setRelativePosition(Vector relativePosition) {
    return super.setRelativePosition(relativePosition);
  }

  /**
   * Checks if this Obstacle object has collided with a Racer. Returns True if the object has
   * collided.
   */
  private boolean checkCollision() {
    if (obstacleManager.getLane().getIsOccupied() && COLLISION_HIGHER_BOUND <= getRelativePosition().getY() && getRelativePosition().getY() <= COLLISION_LOWER_BOUND) {
      ObservationEvent event = new ObservationEvent(COLLISION_MSG);
      notifyObservers(event);
      return true;
    }
    return false;
  }

  /**
   * Getter method that returns whether or not this object has been collided with.
   *
   * @return boolean whether or not this object has been collided with.
   */
  boolean isCollided() {
    return collided;
  }

  /**
   * Updates this object in the game.
   *
   * @param ms Milliseconds Since Last Update
   */
  @Override
  public void update(long ms) {
    Vector position = this.getRelativePosition();
    Vector delta = new Vector();
    delta = delta.add(new Vector(0, speed));
    delta = delta.multiply(ms);
    this.setRelativePosition(position.add(delta));

    checkCollision();
  }
}
