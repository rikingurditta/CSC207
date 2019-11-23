package com.group0565.engine.android;

import androidx.annotation.NonNull;

import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Typeface;

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

    @NonNull
    @Override
    public AndroidPaint clone(){
        return new AndroidPaint(this);
    }
}
