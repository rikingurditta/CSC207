package com.group0565.engine.assets;

import java.io.Closeable;

/** The base Asset class */
public class Asset implements Closeable {
  /** The asset name */
  private final String name;
  /** The asset location */
  private final String path;

  /**
   * Create a new asset
   *
   * @param name The asset name
   * @param path The asset location
   */
  public Asset(String name, String path) {
    if (name == null) throw new IllegalAssetException("Name of TileSet is missing");
    this.name = name;
    if (path == null) throw new IllegalAssetException("Path of TileSet" + name + " is missing");
    this.path = path;
  }

  /** Initialize the asset */
  public void init() {}

  /**
   * Get the asset's name
   *
   * @return The asset name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the asset's location
   *
   * @return The asset location
   */
  public String getPath() {
    return path;
  }

  @Override
  public void close() {}

  /** An exception in asset interactions */
  protected class IllegalAssetException extends RuntimeException {
    /**
     * Create a new asset exception
     *
     * @param message The error message
     */
    IllegalAssetException(String message) {
      super(message);
    }
  }
}
