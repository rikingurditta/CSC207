package com.group0565.racer.objects;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/** A Racer object (Player-controlled) */
public class Racer extends GameObject {

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
    colour.setARGB(255, 77, 166, 255);

    canvas.drawCircle(getAbsolutePosition().getX(), getAbsolutePosition().getY(), 50, colour);
  }
}
