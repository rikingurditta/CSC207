package com.group0565.racer.objects;

import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;

/** A Square-shaped Obstacle */
public class SquareObstacle extends Obstacle {

  /** ThemedPaintCan for the colours of the circle */
  private static final ThemedPaintCan SQUARE_PAINT_CAN =
      new ThemedPaintCan("Racer", "Square.Square");

  /** A vector representing the length of each Square */
  private static final Vector SQUARE_SIDE = new Vector(150);

  /** Constructor for a SquareObstacle Object */
  SquareObstacle(ObstacleManager obstacleManager) {
    super(obstacleManager);
  }

  /** Initializes the ThemedPaintCan for CIRCLE_PAINT_CAN */
  @Override
  public void init() {
    super.init();
    SQUARE_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }
  /**
   * Renders this object on the screen
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    canvas.drawRect(getAbsolutePosition(), SQUARE_SIDE, SQUARE_PAINT_CAN);
  }
}
