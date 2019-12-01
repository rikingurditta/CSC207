package com.group0565.engine.interfaces;

import com.group0565.engine.android.AndroidBitmap;
import com.group0565.math.Vector;

public interface Bitmap{

    int getWidth();
    int getHeight();

    int getPixel(int x, int y);

    static Bitmap createBitmap(int width, int height){
        return new AndroidBitmap(width, height);
    }

    static Bitmap createBitmap(Vector size){
        return createBitmap((int) size.getX(), (int) size.getY());
    }

    Canvas getCanvas();

    void recycle();
}
