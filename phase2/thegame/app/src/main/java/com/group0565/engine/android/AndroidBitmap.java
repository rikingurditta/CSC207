package com.group0565.engine.android;

import android.graphics.Bitmap;

/**
 * An implementation of the Bitmap interface that is just a wrapper for an android.graphics.Bitmap
 */
public class AndroidBitmap implements com.group0565.engine.interfaces.Bitmap {
  /** The Wrapped Bitmap* */
  private Bitmap bitmap;

  /**
   * Create a new AndroidBitmap
   *
   * @param bitmap The android.graphics.Bitmap to wrap
   */
  public AndroidBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  /**
   * Getter for the wrapped bitmap.
   *
   * @return bitmap
   */
  public Bitmap getBitmap() {
    return bitmap;
  }

  /** @see Bitmap#getWidth() */
  @Override
  public int getWidth() {
    return bitmap.getWidth();
  }

  /** @see Bitmap#getHeight() */
  @Override
  public int getHeight() {
    return bitmap.getHeight();
  }

  @Override
  public int getPixel(int x, int y) {
    return bitmap.getPixel(x, y);
  }
}
