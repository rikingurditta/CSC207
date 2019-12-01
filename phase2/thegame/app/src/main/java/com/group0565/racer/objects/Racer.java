package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/** A Racer object (Player-controlled) */
public class Racer extends GameObject {

  /*
   * The radius of the Racer.
   */
  public static final int RACER_RADIUS = 50;

  /*
   * The blue value of the Racer's colour.
   */
  public static final int RACER_COLOUR_B = 255;

  /*
   * The alpha value of the Racer's colour.
   */
  public static final int RACER_COLOUR_A = 255;

  /*
   * The red value of the Racer's colour.
   */
  public static final int RACER_COLOUR_R = 77;

  /*
   * The green value of the Racer's colour.
   */
  public static final int RACER_COLOUR_G = 166;

  /**
   * A constructor for a Racer object
   *
   * @param position a Vector representing the position of this object on the screen
   * @param z the rendering level of this object
   */
  public Racer(Vector position, double z) {
    super(position, z);
  }

  /**
   * Renders this object on the screen
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    Paint colour = Paint.createInstance();
    colour.setARGB(RACER_COLOUR_A, RACER_COLOUR_R, RACER_COLOUR_G, RACER_COLOUR_B);

    canvas.drawCircle(getAbsolutePosition().getX(), getAbsolutePosition().getY(), RACER_RADIUS, colour);
  }
}
