package com.group0565.engine.render;

import androidx.annotation.NonNull;

import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Typeface;

/** A class representing a base PaintCan */
public class PaintCan implements Observable {
  /** The underlying paint */
  private Paint paint;
  /** The sourcePaint - not to be directly changed */
  private Paint sourcePaint;
  /** A bool to note if ARGB was set */
  private boolean setARGB = false;
  /** A bool to note if Color was set */
  private boolean setColor = false;
  /** A bool to note if TextSize was set */
  private boolean setTextSize = false;
  /** A bool to note if StrokeWidth was set */
  private boolean setStrokeWidth = false;
  /** A bool to note if TypeFace was set */
  private boolean setTypeface = false;
  /** The ARGB values */
  private int a = 0, r = 0, g = 0, b = 0;
  /** The color of the paint */
  private int color = 0;
  /** The text size for the paint */
  private float textSize = 0;
  /** The width of the paint */
  private float width = 0;
  /** The Typeface for the paint */
  private Typeface typeface = null;

  /**
   * Create a new instance of a PaintCan
   *
   * @param paint The underlying Paint
   */
  public PaintCan(Paint paint) {
    this.sourcePaint = paint;
    this.paint = (paint == null) ? null : sourcePaint.clone();
  }

  public PaintCan(PaintCan paintCan) {
    this(paintCan.paint);
    this.a = paintCan.a;
    this.r = paintCan.r;
    this.g = paintCan.g;
    this.b = paintCan.b;
    this.color = paintCan.color;
    this.textSize = paintCan.textSize;
    this.width = paintCan.width;
    this.typeface = paintCan.typeface;
    this.setARGB = paintCan.setARGB;
    this.setColor = paintCan.setColor;
    this.setTextSize = paintCan.setTextSize;
    this.setStrokeWidth = paintCan.setStrokeWidth;
    this.setTextSize = paintCan.setTypeface;
  }
  /**
   * Set the ARGB for the paintCan
   *
   * @param a Alpha
   * @param r Red
   * @param g Green
   * @param b Blue
   */
  public void setARGB(int a, int r, int g, int b) {
    this.a = a;
    this.r = r;
    this.g = g;
    this.b = b;
    setARGB = true;
    paint.setARGB(a, r, g, b);
  }

  /**
   * Sets the text size Change setTextSize to true
   *
   * @param textSize Target text size
   */
  public void setTextSize(float textSize) {
    this.textSize = textSize;
    setTextSize = true;
    paint.setTextSize(textSize);
  }

  /**
   * Sets the color Change setColor to true
   *
   * @param color Target color
   */
  public void setColor(int color) {
    this.color = color;
    setColor = true;
    paint.setColor(color);
  }

  /**
   * Sets the stroke width Change setStrokeWidth to true
   *
   * @param width Target width
   */
  public void setStrokeWidth(float width) {
    this.width = width;
    setStrokeWidth = true;
    paint.setStrokeWidth(width);
  }

  /**
   * Sets the stroke width Change setTypeface to true
   *
   * @param typeface Target width
   */
  public void setTypeface(Typeface typeface) {
    this.typeface = typeface;
    setTypeface = true;
    paint.setTypeface(typeface);
  }

  /**
   * Get whether ARGB have been set
   *
   * @return True if ARGB has been set, false otherwise
   */
  public boolean hasSetARGB() {
    return setARGB;
  }

  /** Change setARGB to false and reset paint to sourcePaint */
  public void unsetARGB() {
    this.setARGB = false;
    this.paint = this.sourcePaint.clone();
  }

  /**
   * Get whether color have been set
   *
   * @return True if color has been set, false otherwise
   */
  public boolean hasSetColor() {
    return setColor;
  }

  /** Change setColor to false and reset paint to sourcePaint */
  public void unsetColor() {
    this.setColor = false;
    this.paint = this.sourcePaint.clone();
  }

  /**
   * Get whether text size have been set
   *
   * @return True if text size has been set, false otherwise
   */
  public boolean hasSetTextSize() {
    return setTextSize;
  }

  /** Change setTextSize to false and reset paint to sourcePaint */
  public void unsetTextSize() {
    this.setTextSize = false;
    this.paint = this.sourcePaint.clone();
  }

  /**
   * Get whether stroke width have been set
   *
   * @return True if stroke width has been set, false otherwise
   */
  public boolean hasSetStrokeWidth() {
    return setStrokeWidth;
  }

  /** Change setStrokeWidth to false and reset paint to sourcePaint */
  public void unsetStrokeWidth() {
    this.setStrokeWidth = false;
    this.paint = this.sourcePaint.clone();
  }

  /**
   * Get whether typeface have been set
   *
   * @return True if typeface has been set, false otherwise
   */
  public boolean hasSetTypeface() {
    return setTypeface;
  }

  /** Change setTypeface to false and reset paint to sourcePaint */
  public void unsetTypeface(boolean setTypeface) {
    this.setTypeface = setTypeface;
    this.paint = this.sourcePaint.clone();
  }

  /**
   * Sets the paint to sourcePaint and then change all the changed values to their new values
   *
   * @param sourcePaint The starting paint
   */
  public void setPaint(Paint sourcePaint) {
    this.sourcePaint = sourcePaint;
    this.paint = sourcePaint.clone();
    if (setTypeface) this.paint.setTypeface(typeface);
    if (setARGB) this.paint.setARGB(a, r, g, b);
    if (setStrokeWidth) this.paint.setStrokeWidth(width);
    if (setTextSize) this.paint.setTextSize(textSize);
    if (setColor) this.paint.setColor(color);
    this.notifyObservers();
  }
  /**
   * Get the paint of this can
   *
   * @return The underlying paint
   */
  public Paint getPaint() {
    return this.paint;
  }

  @NonNull
  @Override
  public PaintCan clone() {
    return new PaintCan(this);
  }
}
