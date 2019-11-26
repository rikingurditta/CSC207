package com.group0565.racerGame.Obstacles;

import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;

/** A Circle-shaped Obstacle */
public class CircleObstacle extends Obstacle {

  /**
   * Constructor for a CircleObstacle object
   *
   * @param lane the lane that this obstacle occupies
   * @param z the rendering level of this object
   * @param parent the ObstacleManager that this Obstacle is adopted by
   */
  CircleObstacle(int lane, double z, ObstacleManager parent) {
    super(lane, z, parent);
  }

  /**
   * Renders this object on the screen
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    Paint colour = Paint.createInstance();

    // If this object has been hit, change its colour to green
    if (isCollided()) {
      colour.setARGB(255, 0, 255, 0);
    }
    // Otherwise it remains red.
    else {
      colour.setARGB(255, 255, 0, 0);
    }

    canvas.drawCircle(getAbsolutePosition().getX(), getAbsolutePosition().getY(), 75, colour);
  }
}
