package com.group0565.tsu.enums;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.interfaces.Bitmap;

/** Enum representing a Hit Score. Also have Bitmaps and other information attached. */
public enum Scores {
  S300(300, 0, 0),
  S150(150, 0, 1),
  S50(50, 0, 2),
  S0(0, 0, 3);

  // Asset Constants
  private static final String SET = "Tsu";
  private static final String SCORES_SHEET = "Scores";
  /** Whether or not we have already initialized */
  private static boolean initialized = false;
  /** The x, y coordinates of the tile on the tilesheet */
  private int tileX, tileY;
  /** The bitmap for this Button */
  private Bitmap bitmap = null;
  /** The numeric score */
  private int score;

  /**
   * Initializes coordinates and score
   *
   * @param score The numeric score of the Score
   * @param tileX The x coordinate of the tile
   * @param tileY The y coordinate of the tile
   */
  Scores(int score, int tileX, int tileY) {
    this.score = score;
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
      TileSheet sheet = manager.getTileSheet(SET, SCORES_SHEET);
      for (Scores score : Scores.values()) score.bitmap = sheet.getTile(score.tileX, score.tileY);
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

  /**
   * Getter for score
   *
   * @return score
   */
  public int getScore() {
    return score;
  }
}
