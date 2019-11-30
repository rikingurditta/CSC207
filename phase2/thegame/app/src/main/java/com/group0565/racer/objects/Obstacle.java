package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;

/** An Obstacle in the game */
public abstract class Obstacle extends GameObject implements Observable {


  /**
   * An ObstacleManager that this object is adopted by
   */
  private ObstacleManager obstacleManager;

  // Need to add falling faster over time functionality
  /**
   * The current speed of the object, used for calculations in update method
   */
  private float speed = 0.5f;

  /**
   * Boolean representing whether or not the Racer has hit this Obstacle
   */
  private boolean collided = false;

  /**
   * Constructs a new Obstacle Object
   *
   */
  Obstacle(ObstacleManager obstacleManager) {
    super();
    setRelativePosition(new Vector(0, 0));
    this.obstacleManager = obstacleManager;
  }

  @Override
  public GameObject setRelativePosition(Vector relativePosition) {
    return super.setRelativePosition(relativePosition);
  }

  /**
   * checks if this Obstacle object has collided with a Racer. Returns True if the object has
   * collided.
   */
  private boolean checkCollision() {
    if (obstacleManager.getLane().getIsOccupied()) {
      return false;
    }
    return true;
  }

  /**
   * getter method that returns whether or not this object has been collided with
   *
   * @return boolean whether or not this object has been collided with
   */
  boolean isCollided() {
    return collided;
  }

  /**
   * Updates this object in the game
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

    checkDead();
  }

  public void checkDead() {
    if (getAbsolutePosition().getY() > 2500) {
      ObservationEvent event = new ObservationEvent("Dead");
      notifyObservers(event);
    }
  }
}
