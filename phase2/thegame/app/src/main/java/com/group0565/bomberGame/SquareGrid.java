package com.group0565.bomberGame;

import com.group0565.bomberGame.obstacles.Crate;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

import java.util.HashSet;
import java.util.Set;

/** A grid with square-shaped tiles. */
public class SquareGrid extends GameObject {
  /** How many tiles wide the grid is. */
  private final int width;
  /** How many tiles tall the grid is. */
  private final int height;
  /** The items in this grid. */
  private final Set<GridObject> items = new HashSet<>();
  /** The items to be added to the grid on the next update. */
  private final Set<GridObject> itemsToBeAdded = new HashSet<>();
  /** The items to be removed from the grid on the next update. */
  private final Set<GridObject> itemsToBeRemoved = new HashSet<>();
  /** How wide each tile is, in pixels. */
  private int tileWidth;
  /** How wide the grid is, in pixels. */
  private float windowWidth;
  /** How tall the grid is, in pixels. */
  private float windowHeight;
  /** The game that this SquareGrid is for. * */
  private BomberGame game;
  /** PaintCan for this grid's lines. */
  private ThemedPaintCan paintCan = new ThemedPaintCan("Bomber", "Grid.Line");

  /**
   * Create a new SquareGrid.
   *
   * @param position The absolute position of this object.
   * @param z The z-level of the object.
   * @param width How many tiles wide this grid is.
   * @param height How many tiles tall this grid is.
   * @param tileWidth How wide each tile is, in pixels.
   * @param game
   */
  public SquareGrid(
      Vector position, double z, int width, int height, int tileWidth, BomberGame game) {
    super(position, z);
    this.width = width;
    this.height = height;
    this.tileWidth = tileWidth;
    this.game = game;
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
   * @param game Main Game gameOBJ
   */
  public SquareGrid(Vector position, int width, int height, int tileWidth, BomberGame game) {
    this(position, 0, width, height, tileWidth, game);
  }

  @Override
  public void init() {
    super.init();
    paintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
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
    Vector topLeft = pos;
    Vector topRight = pos.add(new Vector(windowWidth, 0));
    Vector bottomLeft = pos.add(new Vector(0, windowHeight));
    Vector bottomRight = pos.add(new Vector(windowWidth, windowHeight));

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

  /** @return true if g can move to the tile with coordinates p. */
  public boolean isValidMove(GridObject g, Coords p) {
    for (GridObject item : items) {
      if (!item.isDroppable() && item != g && item.gridCoords.equals(p)) {
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

  public Set<GridObject> getItems() {
    return items;
  }

  public boolean canPlaceBomb(Coords p) {
    for (GridObject g : items) {
      if (g.gridCoords.equals(p) && g.isBomb()) {
        return false;
      }
    }
    for (GridObject g : itemsToBeAdded) {
      if (g.gridCoords.equals(p) && g.isBomb()) {
        return false;
      }
    }
    return true;
  }

  public void makeRandomCrate() {
    boolean done;
    Coords r;
    do {
      done = true;
      r = Coords.random(0, 0, width, height);
      for (GridObject g : items) {
        if (g.gridCoords.equals(r)) {
          done = false;
        }
      }
      for (GridObject g : itemsToBeAdded) {
        if (g.gridCoords.equals(r)) {
          done = false;
        }
      }
    } while (!done);
    Crate c = new Crate(r, 10, this, game);
    game.adoptLater(c);
  }

  public int getTileWidth() {
    return this.tileWidth;
  }
}
