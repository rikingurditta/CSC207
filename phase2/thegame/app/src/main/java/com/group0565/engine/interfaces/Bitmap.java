package com.group0565.engine.interfaces;

import com.group0565.engine.android.AndroidBitmap;
import com.group0565.math.Vector;

public interface Bitmap {

  static Bitmap createBitmap(int width, int height) {
    return new AndroidBitmap(width, height);
  }

  static Bitmap createBitmap(Vector size) {
    return createBitmap((int) size.getX(), (int) size.getY());
  }

  int getWidth();

  int getHeight();

  int getPixel(int x, int y);

  Canvas getCanvas();

  void recycle();
}
