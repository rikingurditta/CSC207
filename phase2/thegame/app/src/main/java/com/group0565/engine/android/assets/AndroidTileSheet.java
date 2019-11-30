package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.group0565.engine.android.AndroidBitmap;
import com.group0565.engine.assets.TileSheet;

import java.io.IOException;

/** Android-specific implementation of TileSheet */
public class AndroidTileSheet extends TileSheet {
  /** TileSheets folder */
  public static final String TILE_FOLDER = "tilesets/";
  /** Class tag constant */
  private static final String TAG = "AndroidTileSheet";
  /** The TileSheet BitMap */
  private Bitmap bitmap = null;
  /** A submap of the tileSheet into tiles */
  private Bitmap[][] submap;
  /** Height and Width of a tile */
  private int width = -1, height = -1;
  /** A reference to an Asset Manager */
  private AssetManager assetManager;

  /**
   * Create a new AndroidTileSheet
   *
   * @param name TileSheet name
   * @param path TileSheet location
   * @param tileWidth A single tile width
   * @param tileHeight A single tile width
   * @param assetManager The owner asset manager
   */
  public AndroidTileSheet(
      String name, String path, int tileWidth, int tileHeight, AssetManager assetManager) {
    super(name, path, tileWidth, tileHeight);
    this.assetManager = assetManager;
  }

  @Override
  public void init() {
    try {
      bitmap = BitmapFactory.decodeStream(assetManager.open(TILE_FOLDER + this.getPath()));
      this.width = bitmap.getWidth();
      this.height = bitmap.getHeight();
      this.submap = new Bitmap[width / getTileWidth()][height / getTileHeight()];
    } catch (IOException e) {
      Log.e(
          TAG, "Tilesheet " + this.getName() + " at path " + this.getPath() + " is not found.", e);
    }
  }

  /**
   * Create a submap of the tiles from a TileSheet
   *
   * @param x The starting x coordinate
   * @param y The starting y coordinate
   * @return The submap
   */
  private Bitmap createSubMap(int x, int y) {
    return Bitmap.createBitmap(
        bitmap, x * getTileWidth(), y * getTileHeight(), getTileWidth(), getTileHeight());
  }

  /**
   * Get the tile at given position
   *
   * @param x The x component of position
   * @param y The y component of position
   * @return The bitmap at given location
   */
  public AndroidBitmap getTile(int x, int y) {
    if (submap[x][y] == null) submap[x][y] = createSubMap(x, y);
    return new AndroidBitmap(submap[x][y]);
  }

  /**
   * Get the width of a single tile
   *
   * @return The width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of a single tile
   *
   * @return The height
   */
  public int getHeight() {
    return height;
  }
}
