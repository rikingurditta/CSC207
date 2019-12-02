package com.group0565.racer.objects;

import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;

/** A Circle-shaped Obstacle */
public class CircleObstacle extends Obstacle {

  /** ThemedPaintCan for the colours of the circle */
  private static final ThemedPaintCan CIRCLE_PAINT_CAN =
      new ThemedPaintCan("Racer", "Circle.Circle");

  /** Constructor for a CircleObstacle object */
  CircleObstacle(ObstacleManager obstacleManager) {
    super(obstacleManager);
  }

  /** Initializes the ThemedPaintCan for CIRCLE_PAINT_CAN */
  @Override
  public void init() {
    super.init();
    CIRCLE_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }

  /**
   * Renders this object on the screen
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    canvas.drawCircle(getAbsolutePosition().add(new Vector(75, 0)), 75, CIRCLE_PAINT_CAN);
  }
}
