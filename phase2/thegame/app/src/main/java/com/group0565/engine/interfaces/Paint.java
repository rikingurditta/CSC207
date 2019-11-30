package com.group0565.engine.interfaces;

import android.graphics.Rect;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.math.Vector;

/** An interface for Paints */
public interface Paint extends Cloneable {
  /**
   * Set ARGB of the paint
   *
   * @param a Alpha
   * @param r Red
   * @param g Green
   * @param b Blue
   */
  void setARGB(int a, int r, int g, int b);

  /**
   * Sets the Paint's textSize
   *
   * @param textSize The target text size
   */
  void setTextSize(float textSize);

  /**
   * Sets the Paint's color
   *
   * @param color Target color
   */
  void setColor(int color);

  /**
   * Sets the Paint's stroke width
   *
   * @param width Target stroke width
   */
  void setStrokeWidth(float width);

  /**
   * Sets the Paint's type face
   *
   * @param typeface Target typeface
   */
  void setTypeface(Typeface typeface);

  /**
   * Create a clone of this Paint
   *
   * @return A new Paint object with the same values
   */
  Paint clone();

  /**
   * Create a new concrete Paint
   *
   * @return An instance of type Paint
   */
  static Paint createInstance() {
    return new AndroidPaint();
  }

  @Deprecated
  void getTextBounds(String text, int i, int length, Rect output);

  /**
   * Gets the vector that fits the text
   *
   * @param text Target text
   * @return A vector of length that wraps text
   */
  Vector getTextBounds(String text);

  int getColor();
}
