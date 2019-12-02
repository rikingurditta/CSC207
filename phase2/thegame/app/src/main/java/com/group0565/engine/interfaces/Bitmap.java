package com.group0565.engine.interfaces;

import com.group0565.engine.android.AndroidBitmap;
import com.group0565.math.Vector;

/** An interface representation for a BitMap */
public interface Bitmap {

  /**
   * Create a new bitmap
   *
   * @param width Tile width
   * @param height Tile height
   * @return A new bitmap
   */
  static Bitmap createBitmap(int width, int height) {
    return new AndroidBitmap(width, height);
  }

  /**
   * Create a new bitmap
   *
   * @param size The tile size
   * @return A new bitmap
   */
  static Bitmap createBitmap(Vector size) {
    return createBitmap((int) size.getX(), (int) size.getY());
  }

  /**
   * Get the tile width
   *
   * @return The tile width
   */
  int getWidth();

  /**
   * Get the tile height
   *
   * @return The tile height
   */
  int getHeight();

  /**
   * Get a pixel from bitmap
   *
   * @param x Horizontal coordinate
   * @param y Vertical coordinate
   * @return The pixel
   */
  int getPixel(int x, int y);

  /**
   * Get the bitmap's owning canvas
   *
   * @return The canvas
   */
  Canvas getCanvas();

  /** Recycle the bitmap */
  void recycle();
}
