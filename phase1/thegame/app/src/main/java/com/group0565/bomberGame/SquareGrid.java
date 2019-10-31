package com.group0565.bomberGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

import java.util.HashSet;
import java.util.Set;

/** A grid with square-shaped tiles. */
public class SquareGrid extends GameObject {
  /** How many tiles wide the grid is. */
  private final int width;
  /** How many tiles tall the grid is. */
  private final int height;
  /** How wide each tile is, in pixels. */
  private int tileWidth;
  /** How wide the grid is, in pixels. */
  private float windowWidth;
  /** How tall the grid is, in pixels. */
  private float windowHeight;

  /** The items in this grid. */
  private final Set<GridObject> items = new HashSet<>();
  /** The items to be added to the grid on the next update. */
  private final Set<GridObject> itemsToBeAdded = new HashSet<>();
  /** The items to be removed from the grid on the next update. */
  private final Set<GridObject> itemsToBeRemoved = new HashSet<>();

  /**
   * Create a new SquareGrid.
   *
   * @param position The absolute position of this object.
   * @param z The z-level of the object.
   * @param width How many tiles wide this grid is.
   * @param height How many tiles tall this grid is.
   * @param tileWidth How wide each tile is, in pixels.
   */
  public SquareGrid(Vector position, double z, int width, int height, int tileWidth) {
    super(position, z);
    this.width = width;
    this.height = height;
    this.tileWidth = tileWidth;
    this.windowWidth = width * tileWidth;
    this.windowHeight = height * tileWidth;
  }

  /**
   * Create a new SquareGrid.
   *
   * @param position The absolute position of this object.
   * @param width How many tiles wide this grid is.
   * @param height How many tiles tall this grid is.
   * @param tileWidth How wide each tile is, in pixels.
   */
  public SquareGrid(Vector position, int width, int height, int tileWidth) {
    super(position);
    this.width = width;
    this.height = height;
    this.tileWidth = tileWidth;
    this.windowWidth = width * tileWidth;
    this.windowHeight = height * tileWidth;
  }

  /**
   * Add an item to the grid.
   *
   * @param g The object to be added.
   * @param gridPosition The position in the grid to add the object.
   * @return Whether the item is already in the grid or is already queued to be added.
   */
  public boolean addItem(GridObject g, Coords gridPosition) {
    if (items.contains(g)) {
      return false;
    }
    return itemsToBeAdded.add(g);
  }

  /**
   * Remove an item from the grid.
   *
   * @param g The item to be removed.
   * @return Whether the item is already queued to be removed.
   */
  public boolean remove(GridObject g) {
    return itemsToBeRemoved.add(g);
  }

  @Override
  public void update(long ms) {
    // update the items in the grid
    items.removeAll(itemsToBeRemoved);
    itemsToBeRemoved.clear();
    items.addAll(itemsToBeAdded);
    itemsToBeAdded.clear();
  }

  @Override
  public void draw(Canvas canvas) {
    Vector pos = this.getAbsolutePosition();
    float x = pos.getX();
    float y = pos.getY();
    float endX = x + windowWidth;
    float endY = y + windowHeight;
    Paint p = new Paint();
    p.setARGB(255, 0, 0, 0);
    p.setStrokeWidth(1f);
    // draw the grid border
    canvas.drawLine(x, y, endX, y, p);
    canvas.drawLine(endX, y, endX, endY, p);
    canvas.drawLine(endX, endY, x, endY, p);
    canvas.drawLine(x, endY, x, y, p);
    // draw the grid lines
    for (int i = 0; i < width; i += 1) {
      canvas.drawLine(x + i * tileWidth, y, x + i * tileWidth, endY, p);
    }
    for (int j = 0; j < height; j += 1) {
      canvas.drawLine(x, y + j * tileWidth, endX, y + j * tileWidth, p);
    }
  }

  /** @return true if there is a tile in the grid with coordinates p. */
  public boolean isValidTile(Coords p) {
    return p.x >= 0 && p.x < width && p.y >= 0 && p.y < height;
  }

  /** @return true if g can move to the tile with coordinates p. */
  public boolean isValidMove(GridObject g, Coords p) {
    for (GridObject item : items) {
      if (item != g && item.gridCoords.equals(p)) {
        return false;
      }
    }
    return isValidTile(p);
  }

  /** @return the absolute position of the the tile with coordinates p, whether or not it exists. */
  public Vector gridCoordsToAbsolutePosition(Coords p) {
    // TODO: maybe throw an error if there is no tile with coordinates p
    Vector pos = this.getAbsolutePosition();
    return new Vector(pos.getX() + p.x * tileWidth, pos.getY() + p.y * tileWidth);
  }
}
