package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.math.Vector;

/** An Obstacle in the game */
public abstract class Obstacle extends MenuObject {


  /** An ObstacleManager that this object is adopted by */
  private Lane lane;

  // Need to add falling faster over time functionality
  /** The current speed of the object, used for calculations in update method */
  private float speed = 0.5f;

  /** Boolean representing whether or not the Racer has hit this Obstacle */
  private boolean collided = false;

  /**
   * Constructs a new Obstacle Object
   *
   * @param lane the ObstacleManager that this object is adopted by
   */
  Obstacle(Lane lane) {
    super(new Vector(0, 0));
    this.lane = lane;
  }

  /**
   * checks if this Obstacle object has collided with a Racer. Returns True if the object has
   * collided.
   */
  private void checkCollision() {
    if (lane.getRacerLane()) {

    }
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
    Vector position = this.getAbsolutePosition();
    Vector delta = new Vector();
    delta = delta.add(new Vector(0, speed));
    delta = delta.multiply(ms);
    this.setAbsolutePosition(position.add(delta));
    checkCollision();
  }
}
