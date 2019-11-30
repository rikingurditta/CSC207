package com.group0565.engine.interfaces;

/** An interface for a Bitmap */
public interface Bitmap {
  /**
   * Get the width of the bitmap
   *
   * @return The object width
   */
  int getWidth();

  /**
   * Get the height of the bitmap
   *
   * @return The object height
   */
  int getHeight();

  /**
   * Get the pixel at given coordinates
   *
   * @param x The x coordinate
   * @param y The y coordinate
   * @return The pixel at the x and y coordinates
   */
  int getPixel(int x, int y);
}
