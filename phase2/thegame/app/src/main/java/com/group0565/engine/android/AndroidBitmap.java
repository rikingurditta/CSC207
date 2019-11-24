package com.group0565.engine.android;

import android.graphics.Bitmap;

public class AndroidBitmap implements com.group0565.engine.interfaces.Bitmap {
    private Bitmap bitmap;

    public AndroidBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public int getPixel(int x, int y) {
        return bitmap.getPixel(x, y);
    }
}
