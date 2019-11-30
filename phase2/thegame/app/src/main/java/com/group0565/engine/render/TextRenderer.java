package com.group0565.engine.render;

import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;

/** Draws Text */
public class TextRenderer extends MenuObject {
  /** The Text's paint */
  private Paint paint;
  /** The text's PaintCan */
  private PaintCan paintCan;
  /** The text to draw */
  private Source<String> textSource;

  /**
   * Create a new TextRenderer
   *
   * @param position The target position to draw
   * @param string The target text to draw
   * @param paintCan The target paintCan to draw
   */
  public TextRenderer(Vector position, String string, PaintCan paintCan) {
    super(paintCan.getPaint().getTextBounds(string));
    this.setRelativePosition(position);
    this.setPaintCan(paintCan);
    this.textSource = new LanguageText(string);
  }

  /**
   * Create a new TextRenderer
   *
   * @param position The target position to draw
   * @param string The target text to draw
   * @param paint The target paintCan to draw
   */
  public TextRenderer(Vector position, String string, Paint paint) {
    super(paint.getTextBounds(string));
    this.setRelativePosition(position);
    this.setPaint(paint);
    this.textSource = new LanguageText(string);
  }

  /**
   * Create a new TextRenderer
   *
   * @param position The target position to draw
   * @param text The target text to draw
   * @param paintCan The target paintCan to draw
   */
  public TextRenderer(Vector position, Source<String> text, PaintCan paintCan) {
    super(paintCan.getPaint().getTextBounds(text.getValue()));
    this.setRelativePosition(position);
    this.setPaintCan(paintCan);
    this.textSource = text;
  }

  /**
   * Create a new TextRenderer
   *
   * @param position The target position to draw
   * @param text The target text to draw
   * @param paint The target paintCan to draw
   */
  public TextRenderer(Vector position, Source<String> text, Paint paint) {
    super(paint.getTextBounds(text.getValue()));
    this.setRelativePosition(position);
    this.setPaint(paint);
    this.textSource = text;
  }

  /**
   * Create a new TextRenderer
   *
   * @param string The target text to draw
   * @param paintCan The target paintCan to draw
   */
  public TextRenderer(String string, PaintCan paintCan) {
    super(paintCan.getPaint().getTextBounds(string));
    this.setPaintCan(paintCan);
    this.textSource = new LanguageText(string);
  }

  /**
   * Create a new TextRenderer
   *
   * @param string The target text to draw
   * @param paint The target paintCan to draw
   */
  public TextRenderer(String string, Paint paint) {
    super(paint.getTextBounds(string));
    this.setPaint(paint);
    this.textSource = new LanguageText(string);
  }

  /**
   * Create a new TextRenderer
   *
   * @param text The target text to draw
   * @param paintCan The target paintCan to draw
   */
  public TextRenderer(Source<String> text, PaintCan paintCan) {
    super(paintCan.getPaint().getTextBounds(text.getValue()));
    this.setPaintCan(paintCan);
    this.textSource = text;
  }

  /**
   * Create a new TextRenderer
   *
   * @param text The target text to draw
   * @param paint The target paintCan to draw
   */
  public TextRenderer(Source<String> text, Paint paint) {
    super(paint.getTextBounds(text.getValue()));
    this.setPaint(paint);
    this.textSource = text;
  }

  @Override
  public void draw(Canvas canvas, Vector pos, Vector size) {
    super.draw(canvas, pos, size);
    if (paint != null) canvas.drawText(getString(), pos, paint);
    else if (paintCan != null) canvas.drawText(getString(), pos, paintCan.getPaint());
  }

  /**
   * Sets the paint according to the given paint
   *
   * @param paint The target paint to use
   */
  public void setPaint(Paint paint) {
    this.paint = paint;
    this.paintCan = null;
  }

  /**
   * Sets the paint can
   *
   * @param paintCan The target paintCan
   */
  public void setPaintCan(PaintCan paintCan) {
    this.paintCan = paintCan;
    this.paint = null;
  }

  /**
   * Get the text
   *
   * @return The text of the TextRenderer
   */
  public String getString() {
    return this.textSource.getValue();
  }

  /**
   * Get the current paint
   *
   * @return The paint of TextRenderer
   */
  protected Paint getPaint() {
    return paintCan != null ? paintCan.getPaint() : paint;
  }
}
