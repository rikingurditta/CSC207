package com.group0565.racerGame;

import android.graphics.Canvas;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

/** An abstract Menu class */
public abstract class Menu extends GameObject {

  /**
   * A constructor for an abstract Menu object
   *
   * @param z The rendering level of this Menu object
   */
  Menu(double z) {
    super(z);
  }

  /** Visually renders the object onto the screen */
  public abstract void draw(Canvas canvas);
}
