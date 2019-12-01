package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;

/** An Obstacle in Racer */
public abstract class Obstacle extends GameObject implements Observable {

  /**
   * The starting relative position of the Obstacle.
   */
  private static final Vector STARTING_RELATIVE_POSITION = new Vector(0, 0);

  /**
   * The top y-coordinate of the collision hitbox.
   */
  private static final int COLLISION_HIGHER_BOUND = 1450;

  /**
   * The bottom y-coordinate of the collision hitbox.
   */
  private static final int COLLISION_LOWER_BOUND = 1550;

  /**
   * The observation message passed when a collision occurs.
   */
  private static final String COLLISION_MSG = "Collision";

  /**
   * The ObstacleManager for this Obstacle.
   */
  private ObstacleManager obstacleManager;

  /**
   * The current speed of the Obstacle, used for calculations in update method.
   */
  private static final Vector SPEED = new Vector(0, 0.8f);


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
  private void checkCollision() {
    if (obstacleManager.getLane().getIsOccupied() && COLLISION_HIGHER_BOUND <= getRelativePosition().getY() && getRelativePosition().getY() <= COLLISION_LOWER_BOUND) {
      ObservationEvent event = new ObservationEvent(COLLISION_MSG);
      notifyObservers(event);
    }
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
    delta = delta.add(SPEED);
    delta = delta.multiply(ms);
    this.setRelativePosition(position.add(delta));

    checkCollision();
  }
}
