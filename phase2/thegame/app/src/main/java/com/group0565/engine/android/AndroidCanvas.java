package com.group0565.engine.android;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/**
 * Android Implementation of the Canvas Interface. Wraps an android.graphics.Canvas
 */
public class AndroidCanvas implements com.group0565.engine.interfaces.Canvas {
    /**The wrapped Canvas**/
    private Canvas aCanvas;

    /**
     * Creates a new Android Canvas
     * @param aCanvas The canvas to wrap
     */
    public AndroidCanvas(Canvas aCanvas) {
        this.aCanvas = aCanvas;
    }

    @Override
    public void drawColor(int color) {
        aCanvas.drawColor(color);
    }

    @Override
    public void drawText(String text, float x, float y, Paint paint) {
        if (paint instanceof AndroidPaint) {
            AndroidPaint aPaint = (AndroidPaint) paint;
            Vector bounds = aPaint.getTextBounds(text);
            aCanvas.drawText(text, x, y + bounds.getY(), aPaint);
        }else
            throw new IllegalArgumentException("Only AndroidPaint can be drawn with.");
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom, Paint paint) {
        if (paint instanceof AndroidPaint) {
            AndroidPaint aPaint = (AndroidPaint) paint;
            aCanvas.drawRect(left, top, right, bottom, aPaint);
        }else
            throw new IllegalArgumentException("Only AndroidPaint can be drawn with.");    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) {
        if (paint instanceof AndroidPaint || paint == null) {
            AndroidPaint aPaint = (AndroidPaint) paint;
            if (bitmap instanceof AndroidBitmap) {
                AndroidBitmap androidBitmap = (AndroidBitmap) bitmap;
                aCanvas.drawBitmap(androidBitmap.getBitmap(), src, dst, aPaint);
            }else
                throw new IllegalArgumentException("Only AndroidBitmap can be drawn.");
        }else
            throw new IllegalArgumentException("Only AndroidPaint can be drawn with.");
    }

    @Override
    public void drawCircle(float cx, float cy, float radius, Paint paint) {
        if (paint instanceof AndroidPaint) {
            AndroidPaint aPaint = (AndroidPaint) paint;
            aCanvas.drawCircle(cx, cy, radius, aPaint);
        }else
            throw new IllegalArgumentException("Only AndroidPaint can be drawn with.");
    }

    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint) {
        if (paint instanceof AndroidPaint) {
            AndroidPaint aPaint = (AndroidPaint) paint;
            aCanvas.drawRoundRect(left, top, right, bottom, rx, ry, aPaint);
        }else
            throw new IllegalArgumentException("Only AndroidPaint can be drawn with.");
    }

    @Override
    public void drawLine(Vector start, Vector end, Paint paint){
        if (paint instanceof AndroidPaint) {
            AndroidPaint aPaint = (AndroidPaint) paint;
            this.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), aPaint);
        }else
            throw new IllegalArgumentException("Only AndroidPaint can be drawn with.");
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        if (paint instanceof AndroidPaint) {
            AndroidPaint aPaint = (AndroidPaint) paint;
            aCanvas.drawLine(startX, startY, stopX, stopY, aPaint);
        }else
            throw new IllegalArgumentException("Only AndroidPaint can be drawn with.");
    }

    @Override
    public void drawRGB(int r, int g, int b) {
        aCanvas.drawRGB(r, g, b);
    }

    @Override
    public int getWidth() {
        return aCanvas.getWidth();
    }

    @Override
    public int getHeight() {
        return aCanvas.getHeight();
    }
}
