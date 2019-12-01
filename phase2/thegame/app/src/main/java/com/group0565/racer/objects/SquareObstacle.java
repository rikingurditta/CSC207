package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/** A Square-shaped Obstacle */
public class SquareObstacle extends Obstacle {

  public static final int SQUARE_BOTTOM = 75;
  public static final int SQUARE_TOP = 75;
  public static final int SQUARE_RIGHT = 150;

  /**
   * Constructor for a SquareObstacle Object

   */
  SquareObstacle(ObstacleManager obstacleManager) {
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

    // if the object has been hit, change its colour to green
    if (isCollided()) {
      colour.setARGB(255, 0, 255, 0);
    }

    // Otherwise it remains red
    else {
      colour.setARGB(255, 255, 0, 0);
    }

    canvas.drawRect(
        getAbsolutePosition().getX(),
        getAbsolutePosition().getY() - SQUARE_TOP,
        getAbsolutePosition().getX() + SQUARE_RIGHT,
        getAbsolutePosition().getY() + SQUARE_BOTTOM,
        colour);
  }
}
