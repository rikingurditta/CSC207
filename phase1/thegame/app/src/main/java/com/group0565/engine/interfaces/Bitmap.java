package com.group0565.engine.interfaces;

public interface Bitmap{

    int getWidth();
    int getHeight();

    int getPixel(int x, int y);

    static Bitmap createBitmap(Bitmap source, int x, int y, int width, int height){
        return null;
    }
}
