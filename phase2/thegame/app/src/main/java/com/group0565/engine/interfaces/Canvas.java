package com.group0565.engine.interfaces;

import android.graphics.Rect;
import android.graphics.RectF;

import com.group0565.engine.render.PaintCan;
import com.group0565.math.Vector;

/** An interface for an object that can be drawn on */
public interface Canvas {
  /** @see android.graphics.Canvas#drawColor(int) */
  void drawColor(int color);

  /** Wrapper method for {@link #drawText(String, float, float, Paint)} */
  @Deprecated
  default void drawText(String text, Vector position, Paint paint) {
    this.drawText(text, position.getX(), position.getY(), paint);
  }

  /** @see android.graphics.Canvas#drawText(String, float, float, android.graphics.Paint) */
  @Deprecated
  void drawText(String text, float x, float y, Paint paint);

  /** Wrapper method for {@link #drawText(String, float, float, Paint)} */
  default void drawText(String text, Vector position, PaintCan paintCan) {
    this.drawText(text, position.getX(), position.getY(), paintCan.getPaint());
  }

  /** Wrapper method for {@link #drawRect(float, float, float, float, Paint)} */
  default void drawRect(Vector pos, Vector size, Paint paint) {
    Vector sum = pos.add(size);
    this.drawRect(pos.getX(), pos.getY(), sum.getX(), sum.getY(), paint);
  }

  /** Wrapper method for {@link #drawRect(float, float, float, float, Paint)} */
  default void drawRect(Vector pos, Vector size, PaintCan paintCan) {
    Vector sum = pos.add(size);
    this.drawRect(pos.getX(), pos.getY(), sum.getX(), sum.getY(), paintCan.getPaint());
  }

  /** @see android.graphics.Canvas#drawRect(float, float, float, float, android.graphics.Paint) */
  void drawRect(float left, float top, float right, float bottom, Paint paint);

  /**
   * @see android.graphics.Canvas#drawBitmap(android.graphics.Bitmap, Rect, Rect,
   *     android.graphics.Paint)
   */
  @Deprecated
  void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint);

  /**
   * @see android.graphics.Canvas#drawBitmap(android.graphics.Bitmap, Rect, Rect,
   *     android.graphics.Paint)
   */
  @Deprecated
  default void drawBitmap(Bitmap bitmap, Rect src, RectF dst) {
    this.drawBitmap(bitmap, src, dst, (Paint) null);
  }

  /**
   * @see android.graphics.Canvas#drawBitmap(android.graphics.Bitmap, Rect, Rect,
   *     android.graphics.Paint)
   */
  @Deprecated
  default void drawBitmap(Bitmap bitmap, Rect src, RectF dst, PaintCan paintCan) {
    this.drawBitmap(bitmap, src, dst, paintCan.getPaint());
  }

  /**
   * @see android.graphics.Canvas#drawBitmap(android.graphics.Bitmap, Rect, Rect,
   *     android.graphics.Paint)
   */
  default void drawBitmap(Bitmap bitmap, Vector pos, Vector size) {
    Vector sum = pos.add(size);
    this.drawBitmap(bitmap, null, new RectF(pos.getX(), pos.getY(), sum.getX(), sum.getY()));
  }

  /** Wrapper method for {@link #drawCircle(float, float, float, Paint)} */
  default void drawCircle(Vector pos, float radius, Paint paint) {
    this.drawCircle(pos.getX(), pos.getY(), radius, paint);
  }

  /** Wrapper method for {@link #drawCircle(float, float, float, Paint)} */
  default void drawCircle(Vector pos, float radius, PaintCan paintCan) {
    this.drawCircle(pos.getX(), pos.getY(), radius, paintCan.getPaint());
  }

  /** @see android.graphics.Canvas#drawCircle(float, float, float, android.graphics.Paint) */
  void drawCircle(float cx, float cy, float radius, Paint paint);

  /** Wrapper method for {@link #drawText(String, float, float, Paint)} */
  default void drawRoundRect(Vector pos, Vector size, Vector radius, Paint paint) {
    Vector sum = pos.add(size);
    this.drawRoundRect(
        pos.getX(), pos.getY(), sum.getX(), sum.getY(), radius.getX(), radius.getY(), paint);
  }

  /** Wrapper method for {@link #drawText(String, float, float, Paint)} */
  default void drawRoundRect(Vector pos, Vector size, Vector radius, PaintCan paintCan) {
    Vector sum = pos.add(size);
    this.drawRoundRect(
        pos.getX(),
        pos.getY(),
        sum.getX(),
        sum.getY(),
        radius.getX(),
        radius.getY(),
        paintCan.getPaint());
  }

  /**
   * @see android.graphics.Canvas#drawRoundRect(float, float, float, float, float, float,
   *     android.graphics.Paint)
   */
  void drawRoundRect(
      float left, float top, float right, float bottom, float rx, float ry, Paint paint);

  /** Wrapper method for {@link #drawLine(float, float, float, float, Paint)} */
  default void drawLine(Vector start, Vector end, Paint paint) {
    this.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
  }

  /** Wrapper method for {@link #drawLine(float, float, float, float, Paint)} */
  default void drawLine(Vector start, Vector end, PaintCan paintCan) {
    this.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paintCan.getPaint());
  }

  /** @see android.graphics.Canvas#drawLine(float, float, float, float, android.graphics.Paint) */
  void drawLine(float startX, float startY, float stopX, float stopY, Paint paint);

  /** @see android.graphics.Canvas#drawRGB(int, int, int) */
  void drawRGB(int r, int g, int b);

  /**
   * Draw RGB on screen using the color set in Paint
   *
   * @see this#drawRGB(int, int, int)
   */
  default void drawRGB(Paint paint) {
    this.drawColor(paint.getColor());
  }

  /**
   * Draw RGB on screen using the color set in PaintCan
   *
   * @see this#drawRGB(int, int, int)
   */
  default void drawRGB(PaintCan paintCan) {
    this.drawColor(paintCan.getPaint().getColor());
  }

  /**
   * Draw a drawable
   *
   * @param drawable The drawable to draw.
   */
  default void drawDrawable(Drawable drawable) {
    drawable.draw(this);
  }

  /** @see android.graphics.Canvas#getWidth() */
  int getWidth();

  /** @see android.graphics.Canvas#getHeight() */
  int getHeight();
}
