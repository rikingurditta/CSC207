package com.group0565.engine.interfaces;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.group0565.math.Vector;

public interface Canvas {
    /**
     * @see android.graphics.Canvas#drawColor(int)
     */
    void drawColor(int color);

    /**
     * Wrapper method for {@link #drawText(String, float, float, Paint)}
     */
    default void drawText(String text, Vector position, Paint paint) {
        this.drawText(text, position.getX(), position.getY(), paint);
    }

    /**
     * @see android.graphics.Canvas#drawText(String, float, float, Paint)
     */
    void drawText(String text, float x, float y, Paint paint);

    /**
     * Wrapper method for {@link #drawRect(float, float, float, float, Paint)}
     */
    default void drawRect(Vector pos, Vector size, Paint paint) {
        Vector sum = pos.add(size);
        this.drawRect(pos.getX(), pos.getY(), sum.getX(), sum.getY(), paint);
    }

    /**
     * @see android.graphics.Canvas#drawRect(float, float, float, float, Paint)
     */
    void drawRect(float left, float top, float right, float bottom, Paint paint);

    /**
     * @see android.graphics.Canvas#drawBitmap(Bitmap, Rect, Rect, Paint)
     */
    void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint);


    /**
     * Wrapper method for {@link #drawCircle(float, float, float, Paint)}
     */
    default void drawCircle(Vector pos, float radius, Paint paint) {
        this.drawCircle(pos.getX(), pos.getY(), radius, paint);
    }

    /**
     * @see android.graphics.Canvas#drawCircle(float, float, float, Paint)
     */
    void drawCircle(float cx, float cy, float radius, Paint paint);

    /**
     * Wrapper method for {@link #drawText(String, float, float, Paint)}
     */
    default void drawRoundRect(Vector pos, Vector size, Vector radius, Paint paint) {
        Vector sum = pos.add(size);
        this.drawRoundRect(pos.getX(), pos.getY(), sum.getX(), sum.getY(), radius.getX(), radius.getY(), paint);
    }

    /**
     * @see android.graphics.Canvas#drawRoundRect(float, float, float, float, float, float, Paint)
     */
    void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint);

    /**
     * Wrapper method for {@link #drawLine(float, float, float, float, Paint)}
     */
    default void drawLine(Vector start, Vector end, Paint paint) {
        this.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
    }

    /**
     * @see android.graphics.Canvas#drawLine(float, float, float, float, Paint)
     */
    void drawLine(float startX, float startY, float stopX, float stopY, Paint paint);

    /**
     * @see android.graphics.Canvas#drawRGB(int, int, int)
     */
    void drawRGB(int r, int g, int b);

    /**
     * Draw a drawable
     * @param drawable The drawable to draw.
     */
    default void drawDrawable(Drawable drawable){
        drawable.draw(this);
    }

    /**
     * @see android.graphics.Canvas#getWidth()
     */
    int getWidth();

    /**
     * @see android.graphics.Canvas#getHeight()
     */
    int getHeight();
}
