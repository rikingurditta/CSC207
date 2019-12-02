package com.group0565.bombergame.grid;

import com.group0565.bombergame.core.BomberEngine;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

import java.util.ArrayList;
import java.util.List;

/** A grid with square-shaped tileImages. */
public class SquareGrid extends Grid {
  /** How many tileImages wide the grid is. */
  private final int width;
  /** How many tileImages tall the grid is. */
  private final int height;
  /** How wide the grid is, in pixels. */
  private float windowWidth;
  /** How tall the grid is, in pixels. */
  private float windowHeight;
  /** PaintCan for this grid's lines. */
  private final ThemedPaintCan paintCan = new ThemedPaintCan("Bomber", "Grid.Line");

  private List<Bitmap> tileImages;
  private Bitmap[][] tiles;

  /**
   * Create a new SquareGrid.
   *
   * @param position The absolute position of this object.
   * @param z The z-level of the object.
   * @param width How many tileImages wide this grid is.
   * @param height How many tileImages tall this grid is.
   * @param tileWidth How wide each tile is, in pixels.
   * @param game The game this SquareGrid belongs to.
   */
  public SquareGrid(
      Vector position, double z, int width, int height, int tileWidth, BomberEngine game) {
    super(position, z, tileWidth, game);
    this.width = width;
    this.height = height;
    this.windowWidth = width * tileWidth;
    this.windowHeight = height * tileWidth;
    tiles = new Bitmap[width][height];
  }

  /**
   * Create a new SquareGrid.
   *
   * @param position The absolute position of this object.
   * @param width How many tileImages wide this grid is.
   * @param height How many tileImages tall this grid is.
   * @param tileWidth How wide each tile is, in pixels.
   * @param game Main Game gameOBJ
   */
  public SquareGrid(Vector position, int width, int height, int tileWidth, BomberEngine game) {
    this(position, 0, width, height, tileWidth, game);
  }

  /** Initialize this SquareGrid. Set up its PaintCans and tile images. */
  @Override
  public void init() {
    super.init();
    paintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    tileImages = new ArrayList<>();
    for (int i = 0; i < 4; i += 1) {
      tileImages.add(
          getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(i, 0));
    }
    for (int i = 0; i < width; i += 1) {
      for (int j = 0; j < height; j += 1) {
        int r = (int) (Math.random() * tileImages.size());
        tiles[i][j] = tileImages.get(r);
      }
    }
  }

  /**
   * Draw this SquareGrid, including its background tiles and grid lines.
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {
    for (int i = 0; i < width; i += 1) {
      for (int j = 0; j < height; j += 1) {
        canvas.drawBitmap(
            tiles[i][j],
            getAbsolutePosition().add(new Vector(i, j).multiply(tileWidth)),
            new Vector(tileWidth));
      }
    }

    Vector topLeft = this.getAbsolutePosition();
    Vector topRight = topLeft.add(new Vector(windowWidth, 0));
    Vector bottomLeft = topLeft.add(new Vector(0, windowHeight));
    Vector bottomRight = topLeft.add(new Vector(windowWidth, windowHeight));

    // draw the grid border
    canvas.drawLine(topLeft, topRight, paintCan);
    canvas.drawLine(topRight, bottomRight, paintCan);
    canvas.drawLine(bottomRight, bottomLeft, paintCan);
    canvas.drawLine(bottomLeft, topLeft, paintCan);

    // draw the grid lines
    for (int i = 0; i < width; i += 1) {
      Vector hShift = new Vector(i * tileWidth, 0);
      canvas.drawLine(topLeft.add(hShift), bottomLeft.add(hShift), paintCan);
    }
    for (int j = 0; j < height; j += 1) {
      Vector vShift = new Vector(0, j * tileWidth);
      canvas.drawLine(topLeft.add(vShift), topRight.add(vShift), paintCan);
    }
  }

  /** @return true if there is a tile in the grid with coordinates p. */
  public boolean isValidTile(Coords p) {
    return p.x >= 0 && p.x < width && p.y >= 0 && p.y < height;
  }

  /** @return the absolute position of the the tile with coordinates p, whether or not it exists. */
  public Vector gridCoordsToAbsolutePosition(Coords p) {
    if (!isValidTile(p)) {
      throw new IllegalArgumentException("Coordinates must be valid coordinates on the grid.");
    }
    Vector pos = this.getAbsolutePosition();
    return new Vector(pos.getX() + p.x * tileWidth, pos.getY() + p.y * tileWidth);
  }

  /** @return The position of a random valid tile on the grid. */
  @Override
  public Coords randomCoordsInGrid() {
    return Coords.random(0, 0, width, height);
  }
}
