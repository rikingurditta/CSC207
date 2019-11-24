package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Vector;

/** An Obstacle in the game */
public abstract class Obstacle extends GameObject {

  /** The lane in the game that this Obstacle occupies */
  private int lane;

  /** An ObstacleManager that this object is adopted by */
  private ObstacleManager obsManager;

  // Need to add falling faster over time functionality
  /** The current speed of the object, used for calculations in update method */
  private float speed = 0.5f;

  /** Boolean representing whether or not the Racer has hit this Obstacle */
  private boolean collided = false;

  /**
   * Constructs a new Obstacle Object
   *
   * @param lane the lane that this object occupies
   * @param z the rendering level of this object
   * @param parent the ObstacleManager that this object is adopted by
   */
  Obstacle(int lane, double z, ObstacleManager parent) {
    super(new Vector(lane * 275, 0), z);
    this.lane = lane;
    this.obsManager = parent;
  }

  /**
   * checks if this Obstacle object has collided with a Racer. Returns True if the object has
   * collided.
   */
  private void checkCollision() {
    float racerY = obsManager.parent.getRacer().getAbsolutePosition().getY();
    float thisY = this.getAbsolutePosition().getY();
    if (Math.abs(thisY - racerY) <= 50 && lane == obsManager.parent.getRacer().getLane()) {
      collided = true;
      obsManager.parent.updateDB(
          obsManager.parent.getTotalTime() + obsManager.parent.getSpawnTime());
      obsManager.parent.setLive();
      obsManager.parent.disableAll();
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
