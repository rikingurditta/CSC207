package com.group0565.racer.objects;

import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/** A Circle-shaped Obstacle */
public class CircleObstacle extends Obstacle {

  /**
   * Constructor for a CircleObstacle object
   *
   */
  CircleObstacle(ObstacleManager obstacleManager) {
    super(obstacleManager);
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

    canvas.drawCircle(getAbsolutePosition().getX() + 75, getAbsolutePosition().getY(), 75, colour);
  }
}
