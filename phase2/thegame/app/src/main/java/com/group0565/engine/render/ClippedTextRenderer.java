package com.group0565.engine.render;

import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;

/** Draws Text and clip text that is too long */
public class ClippedTextRenderer extends TextRenderer {
  /** The maximum width of text* */
  private float width;

  /**
   * Creates a new ClippedTextRenderer
   *
   * @param width The maximum width
   * @param paintCan The paint for the text
   * @param position Target position to draw
   * @param string The text
   */
  public ClippedTextRenderer(Vector position, float width, String string, PaintCan paintCan) {
    super(position, string, paintCan);
    this.width = width;
  }

  /**
   * Creates a new ClippedTextRenderer
   *
   * @param width The maximum width
   * @param paint The paint for the text
   * @param position Target position to draw
   * @param string The text
   */
  public ClippedTextRenderer(Vector position, float width, String string, Paint paint) {
    super(position, string, paint);
    this.width = width;
  }

  /**
   * Creates a new ClippedTextRenderer
   *
   * @param width The maximum width
   * @param paintCan The paint for the text
   * @param position Target position to draw
   * @param text The text
   */
  public ClippedTextRenderer(Vector position, float width, Source<String> text, PaintCan paintCan) {
    super(position, text, paintCan);
    this.width = width;
  }

  /**
   * Creates a new ClippedTextRenderer
   *
   * @param width The maximum width
   * @param paint The paint for the text
   * @param position Target position to draw
   * @param text The text
   */
  public ClippedTextRenderer(Vector position, float width, Source<String> text, Paint paint) {
    super(position, text, paint);
    this.width = width;
  }

  /**
   * Creates a new ClippedTextRenderer
   *
   * @param width The maximum width
   * @param paintCan The paint for the text
   * @param string The text
   */
  public ClippedTextRenderer(String string, float width, PaintCan paintCan) {
    super(string, paintCan);
    this.width = width;
  }

  /**
   * Creates a new ClippedTextRenderer
   *
   * @param width The maximum width
   * @param paint The paint for the text
   * @param string The text
   */
  public ClippedTextRenderer(String string, float width, Paint paint) {
    super(string, paint);
    this.width = width;
  }

  /**
   * Creates a new ClippedTextRenderer
   *
   * @param width The maximum width
   * @param paintCan The paint for the text
   * @param text The text
   */
  public ClippedTextRenderer(Source<String> text, float width, PaintCan paintCan) {
    super(text, paintCan);
    this.width = width;
  }

  /**
   * Creates a new ClippedTextRenderer
   *
   * @param width The maximum width
   * @param paint The paint for the text
   * @param text The text
   */
  public ClippedTextRenderer(Source<String> text, float width, Paint paint) {
    super(text, paint);
    this.width = width;
  }

  @Override
  public String getString() {
    String oString = super.getString();
    Paint p = getPaint();
    int l = oString.length();
    while (p.getTextBounds(oString.substring(0, l)).getX() > width) l--;
    return oString.substring(0, l);
  }
}
