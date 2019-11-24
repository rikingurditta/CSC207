package com.group0565.engine.android;

import com.group0565.engine.interfaces.Typeface;

import java.lang.reflect.Type;

public class AndroidTypeface extends Typeface {

    public AndroidTypeface() {
        super();
    }

    public AndroidTypeface(String family, Typeface.Style style) {
       super(family, style);
    }

    public android.graphics.Typeface asNativeTypeface(){
        return android.graphics.Typeface.create(getFamily(), getStyle().getValue());
    }
}
