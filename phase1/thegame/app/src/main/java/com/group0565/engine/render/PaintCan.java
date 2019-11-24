package com.group0565.engine.render;

import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Typeface;

public class PaintCan implements Observable {
    private Paint paint;
    private Paint sourcePaint;
    private boolean setARGB = false;
    private boolean setColor = false;
    private boolean setTextSize = false;
    private boolean setStrokeWidth = false;
    private boolean setTypeface = false;
    private int a = 0, r = 0, g = 0, b = 0;
    private int color = 0;
    private float textSize = 0;
    private float width = 0;
    private Typeface typeface = null;

    public PaintCan(Paint paint) {
        this.sourcePaint = paint;
        this.paint = (paint == null) ? null : sourcePaint.clone();
    }

    public void setARGB(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
        setARGB = true;
        paint.setARGB(a, r, g, b);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        setTextSize = true;
        paint.setTextSize(textSize);
    }

    public void setColor(int color) {
        this.color = color;
        setColor = true;
        paint.setColor(color);
    }

    public void setStrokeWidth(float width) {
        this.width = width;
        setStrokeWidth = true;
        paint.setStrokeWidth(width);
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
        setTypeface = true;
        paint.setTypeface(typeface);
    }

    public boolean hasSetARGB() {
        return setARGB;
    }

    public void unsetARGB() {
        this.setARGB = false;
        this.paint = this.sourcePaint.clone();
    }

    public boolean hasSetColor() {
        return setColor;
    }

    public void unsetColor() {
        this.setColor = false;
        this.paint = this.sourcePaint.clone();
    }

    public boolean hasSetTextSize() {
        return setTextSize;
    }

    public void unsetTextSize() {
        this.setTextSize = false;
        this.paint = this.sourcePaint.clone();
    }

    public boolean hasSetStrokeWidth() {
        return setStrokeWidth;
    }

    public void unsetStrokeWidth() {
        this.setStrokeWidth = false;
        this.paint = this.sourcePaint.clone();
    }

    public boolean hasSetTypeface() {
        return setTypeface;
    }

    public void unsetTypeface(boolean setTypeface) {
        this.setTypeface = setTypeface;
        this.paint = this.sourcePaint.clone();
    }

    public void setPaint(Paint sourcePaint) {
        this.sourcePaint = sourcePaint;
        this.paint = sourcePaint.clone();
        if (setTypeface)    this.paint.setTypeface(typeface);
        if (setARGB)        this.paint.setARGB(a, r, g, b);
        if (setStrokeWidth) this.paint.setStrokeWidth(width);
        if (setTextSize)    this.paint.setTextSize(textSize);
        if (setColor)       this.paint.setColor(color);
        this.notifyObservers();
    }

    public Paint getPaint() {
        return this.paint;
    }
}
