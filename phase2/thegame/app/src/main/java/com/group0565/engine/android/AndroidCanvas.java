package com.group0565.engine.android;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Vector;

public class AndroidCanvas implements Canvas {

    private android.graphics.Canvas aCanvas;

    public AndroidCanvas(android.graphics.Canvas aCanvas) {
        this.aCanvas = aCanvas;
    }

    @Override
    public void drawColor(int color) {
        aCanvas.drawColor(color);
    }

    @Override
    public void drawText(String text, float x, float y, Paint paint) {
        aCanvas.drawText(text, x, y, paint);
    }

    @Override
    public void drawRect(float left, float top, float right, float bottom, Paint paint) {
        aCanvas.drawRect(left, top, right, bottom, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) {
        aCanvas.drawBitmap(bitmap, src, dst, paint);
    }

    @Override
    public void drawCircle(float cx, float cy, float radius, Paint paint) {
        aCanvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    public void drawRoundRect(Vector pos, Vector size, Vector radius, Paint paint){
        Vector sum = pos.add(size);
        this.drawRoundRect(pos.getX(), pos.getY(), sum.getX(), sum.getY(), radius.getX(), radius.getY(), paint);
    }

    @Override
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint) {
        aCanvas.drawRoundRect(left, top, right, bottom, rx, ry, paint);
    }

    @Override
    public void drawLine(Vector start, Vector end, Paint paint){
        this.drawLine(start.getX(), start.getY(), end.getX(), end.getY(), paint);
    }

    @Override
    public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
        aCanvas.drawLine(startX, startY, stopX, stopY, paint);
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
