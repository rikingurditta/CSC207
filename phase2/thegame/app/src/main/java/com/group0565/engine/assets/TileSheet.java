package com.group0565.engine.assets;

import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.LifecycleListener;

/** An abstract representation of a TileSheet asset */
public abstract class TileSheet extends Asset implements LifecycleListener {
  /** The width of a single tile */
  private int tileWidth;
  /** The length of a single tile */
  private int tileHeight;

  /**
   * Create a new tileSheet
   *
   * @param name The tileSheet name
   * @param path The tileSheet location
   * @param tileWidth A single tile width
   * @param tileHeight A single tile height
   */
  public TileSheet(String name, String path, int tileWidth, int tileHeight) {
    super(name, path);
    this.tileWidth = tileWidth;
    this.tileHeight = tileHeight;
  }

  /**
   * Get tile at given position
   *
   * @param x The x component of position
   * @param y The y component of position
   * @return The tile at that position
   */
  public abstract Bitmap getTile(int x, int y);

  /**
   * Return the width of a single tile
   *
   * @return Tile width
   */
  public int getTileWidth() {
    return tileWidth;
  }

  /**
   * Return the height of a single tile
   *
   * @return Tile height
   */
  public int getTileHeight() {
    return tileHeight;
  }
  /**
   * Setter for tileWidth
   *
   * @param tileWidth The new value for tileWidth
   */
  protected void setTileWidth(int tileWidth) {
    this.tileWidth = tileWidth;
  }

  /**
   * Setter for tileHeight
   *
   * @param tileHeight The new value for tileHeight
   */
  protected void setTileHeight(int tileHeight) {
    this.tileHeight = tileHeight;
  }

  /** An exception for tile sheet errors */
  protected class IllegalTileSetException extends IllegalAssetException {
    /**
     * Create a new exception
     *
     * @param message The error message
     */
    public IllegalTileSetException(String message) {
      super(message);
    }
  }
}
