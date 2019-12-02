package com.group0565.engine.android;

import android.graphics.Bitmap;

import com.group0565.engine.interfaces.Canvas;

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
   * Create a new AndroidBitmap
   *
   * @param width The width of the bitmap
   * @param height The height of the bitmap
   */
  public AndroidBitmap(int width, int height) {
    this.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888, true);
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

  @Override
  public Canvas getCanvas() {
    return new AndroidCanvas(new android.graphics.Canvas(bitmap));
  }

  @Override
  public void recycle() {
    bitmap.recycle();
  }
}
