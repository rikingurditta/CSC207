package com.group0565.tsu.enums;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.interfaces.Bitmap;

/** Enum storing bitmaps for Scroll Buttons. Makes accessing button bitmaps much simpler */
public enum ScrollBitmap {
  SCROLL_UP(0, 0),
  SCROLL_UP_DSB(1, 0),
  SCROLL_DOWN(0, 1),
  SCROLL_DOWN_DSB(1, 1),
  ;

  // The set and tilesheet of Buttons
  private static final String SET = "Tsu";
  private static final String BUTTON_SHEET = "Scroll";
  /** Whether or not we have already initialized */
  private static boolean initialized = false;
  /** The x, y coordinates of the tile on the tilesheet */
  private int tileX, tileY;
  /** The bitmap for this Button */
  private Bitmap bitmap = null;

  /**
   * Initializes coordinates
   *
   * @param tileX The x coordinate of the tile
   * @param tileY The y coordinate of the tile
   */
  ScrollBitmap(int tileX, int tileY) {
    this.tileX = tileX;
    this.tileY = tileY;
  }

  /**
   * Initialize bitmaps for buttons
   *
   * @param manager The GameAssetManager with which to load bitmaps from.
   */
  public static void init(GameAssetManager manager) {
    if (!initialized) {
      TileSheet sheet = manager.getTileSheet(SET, BUTTON_SHEET);
      for (ScrollBitmap scrollBitmap : ScrollBitmap.values())
        scrollBitmap.bitmap = sheet.getTile(scrollBitmap.tileX, scrollBitmap.tileY);
      initialized = true;
    }
  }

  /**
   * Getter for bitmap
   *
   * @return bitmap
   */
  public Bitmap getBitmap() {
    return bitmap;
  }
}
