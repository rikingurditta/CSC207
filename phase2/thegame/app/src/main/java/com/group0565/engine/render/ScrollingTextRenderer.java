package com.group0565.engine.render;

import com.group0565.engine.android.AndroidBitmap;
import com.group0565.engine.gameobjects.MenuObject;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.LinearTemporalInterpolator;
import com.group0565.math.Vector;

public class ScrollingTextRenderer extends MenuObject {
    private long scrollTime;
    private long holdTime;
    private long timer;
    private PaintCan paintCan;
    private PaintCan bgPaintCan;
    private Source<String> text;
    private String lastText = "";
    private LinearTemporalInterpolator interpolator = null;
    private Bitmap drawableSurface;
    private Canvas drawableCanvas;

    public ScrollingTextRenderer(Source<String> text, long scrollTime, long holdTime, PaintCan paintCan, PaintCan bgPaintCan, Vector size) {
        super(size);
        this.paintCan = paintCan;
        this.text = text;
        this.timer = -holdTime;
        this.scrollTime = scrollTime;
        this.holdTime = holdTime;
        this.drawableSurface = Bitmap.createBitmap(size);
        this.drawableCanvas = drawableSurface.getCanvas();
        this.bgPaintCan = bgPaintCan;
        setSize(getSize());
    }

    @Override
    public void update(long ms) {
        super.update(ms);
        if (!lastText.equals(text.getValue()))
            recalculateInterpolation();
        lastText = text.getValue();
        if (interpolator == null)
            timer = -holdTime;
        else {
            timer += ms;
            if (timer > scrollTime + holdTime)
                timer = -holdTime;
        }
    }

    private void recalculateInterpolation(){
        if (paintCan == null)
            return;
        Vector bounds = paintCan.getPaint().getTextBounds(text.getValue());
        if (bounds.getX() < getSize().getX())
            interpolator = new LinearTemporalInterpolator(new Vector(), new Vector(), 1, true);
        else
            interpolator = new LinearTemporalInterpolator(new Vector(), new Vector(getSize().getX() - bounds.getX() - 30, 0), scrollTime, true);
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        if (interpolator != null) {
            drawableCanvas.drawRGB(bgPaintCan);
            drawableCanvas.drawText(text.getValue(), interpolator.interpolate(timer), paintCan);
            canvas.drawBitmap(drawableSurface, pos, size);
        }
    }

    @Override
    protected void initialSizeUpdate(Vector size) {
        super.initialSizeUpdate(size);
        if (paintCan != null) {
            Vector bounds = paintCan.getPaint().getTextBounds(text.getValue());
            size = size.newSetY(bounds.getY() + 30);
        }
        if (this.drawableSurface != null)
            this.drawableSurface.recycle();
        this.drawableSurface = Bitmap.createBitmap(size);
        this.drawableCanvas = drawableSurface.getCanvas();
        recalculateInterpolation();
    }

    @Override
    public void setSize(Vector size) {
        if (paintCan != null) {
            Vector bounds = paintCan.getPaint().getTextBounds(text.getValue());
            size = size.newSetY(bounds.getY() + 30);
        }
        super.setSize(size);
        if (this.drawableSurface != null)
            this.drawableSurface.recycle();
        this.drawableSurface = Bitmap.createBitmap(size);
        this.drawableCanvas = drawableSurface.getCanvas();
        recalculateInterpolation();
    }
}
