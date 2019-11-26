package com.group0565.engine.interfaces;

import android.graphics.Rect;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.math.Vector;

public interface Paint extends Cloneable{
    void setARGB(int a, int r, int g, int b);
    void setTextSize(float textSize);
    void setColor(int color);
    void setStrokeWidth(float width);
    Typeface setTypeface(Typeface typeface);
    Paint clone();

    static Paint createInstance(){
        return new AndroidPaint();
    }

    void getTextBounds(String text, int i, int length, Rect output);
    Vector getTextBounds(String text);

    int getColor();
}
