package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;

/** A Racer object (Player-controlled) */
public class Racer extends GameObject {

  /** The radius of the Racer. */
  private static final int RACER_RADIUS = 50;

  /** ThemedPaintCan for the colours of the racer user */
  private static final ThemedPaintCan RACER_PAINT_CAN = new ThemedPaintCan("Racer", "Racer.Racer");

  /**
   * A constructor for a Racer object
   *
   * @param position a Vector representing the position of this object on the screen
   * @param z the rendering level of this object
   */
  public Racer(Vector position, double z) {
    super(position, z);
  }

  @Override
  public void init() {
    super.init();
    RACER_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }

  /**
   * Renders this object on the screen
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    canvas.drawCircle(getAbsolutePosition(), RACER_RADIUS, RACER_PAINT_CAN);
  }
}
