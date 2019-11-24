package com.group0565.engine.android;

import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Typeface;
import com.group0565.math.Vector;

public class AndroidPaint extends android.graphics.Paint implements Paint {
    public AndroidPaint() {
    }

    public AndroidPaint(android.graphics.Paint paint) {
        super(paint);
    }

    @Override
    public Typeface setTypeface(Typeface typeface) {
        return null;
    }

    @Override
    public Vector getTextBounds(String text) {
        Rect size = new Rect();
        this.getTextBounds(text, 0, text.length(), size);
        return new Vector(size.width(), size.height());
    }

    @NonNull
    @Override
    public AndroidPaint clone(){
        return new AndroidPaint(this);
    }
}
