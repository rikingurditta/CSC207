package com.group0565.racerGame;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/** A Racer object (Player-controlled) */
public class Racer extends GameObject {

  /** The lane that this object occupies */
  private int lane;

  /**
   * A constructor for a Racer object
   *
   * @param position a Vector representing the position of this object on the screen
   * @param z the rendering level of this object
   */
  Racer(Vector position, double z) {
    super(position, z);
    this.lane = 2;
  }

  /**
   * Getter method for the lane attribute of this Racer Object
   *
   * @return the integer value that this lane is assigned
   */
  public int getLane() {
    return lane;
  }

  /**
   * Setter method for the lane attribute of this Racer Object
   *
   * @param lane Set the lane attribute to parameter lane.
   */
  void setLane(int lane) {
    this.lane = lane;
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
