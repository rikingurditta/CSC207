package com.group0565.engine.assets;

import com.group0565.engine.interfaces.Paint;

/** An abstract implementation of a Theme asset */
public abstract class ThemeAsset extends Asset {
  /**
   * Create a new ThemeAsset
   *
   * @param name The asset name
   * @param path The asset location
   */
  public ThemeAsset(String name, String path) {
    super(name, path);
  }

  /**
   * Get the paint for the Theme
   *
   * @param name The theme name
   * @return The paint for the theme
   */
  public abstract Paint getPaint(String name);

  /** An exception for illegal theme lookup */
  protected class IllegalThemeAssetException extends IllegalAssetException {
    /**
     * Create a new exception
     *
     * @param message The error message
     */
    public IllegalThemeAssetException(String message) {
      super(message);
    }
  }
}
