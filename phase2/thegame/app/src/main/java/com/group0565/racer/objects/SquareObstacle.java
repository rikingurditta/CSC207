package com.group0565.racer.objects;

import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/** A Square-shaped Obstacle */
public class SquareObstacle extends Obstacle {

  /**
   * Constructor for a SquareObstacle Object

   */
  SquareObstacle(Vector position, ObstacleManager obstacleManager) {
    super(position, obstacleManager);
  }

  /**
   * Renders this object on the screen
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    Paint colour = Paint.createInstance();

    // if the object has been hit, change its colour to green
    if (isCollided()) {
      colour.setARGB(255, 0, 255, 0);
    }

    // Otherwise it remains red
    else {
      colour.setARGB(255, 255, 0, 0);
    }

    canvas.drawRect(
        getAbsolutePosition().getX() - 75,
        getAbsolutePosition().getY() - 75,
        getAbsolutePosition().getX() + 75,
        getAbsolutePosition().getY() + 75,
        colour);
  }
}
