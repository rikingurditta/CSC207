package com.group0565.engine.android;

import android.graphics.Paint;
import android.graphics.Typeface;

public class PaintBuilder {
    private Paint paint;
    private PaintBuilder(){
        paint = new Paint();
    }

    public static PaintBuilder build(){
        return new PaintBuilder();
    }

    public PaintBuilder setARGB(int a, int r, int g, int b){
        paint.setARGB(a, r, g, b);
        return this;
    }

    public PaintBuilder setTypeface(Typeface typeface){
        paint.setTypeface(typeface);
        return this;
    }

    public PaintBuilder setTextSize(float textSize){
        paint.setTextSize(textSize);
        return this;
    }

    public PaintBuilder setColor(int color){
        paint.setColor(color);
        return this;
    }

    public PaintBuilder setStrokeWidth(float width){
        paint.setStrokeWidth(width);
        return this;
    }

    public Paint close(){
        return paint;
    }
}
