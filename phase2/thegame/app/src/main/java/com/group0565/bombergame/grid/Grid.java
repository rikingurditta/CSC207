package com.group0565.bombergame.grid;

import com.group0565.bombergame.core.BomberEngine;
import com.group0565.bombergame.gridobjects.GridObject;
import com.group0565.bombergame.gridobjects.bombs.Bomb;
import com.group0565.bombergame.gridobjects.droppables.Droppable;
import com.group0565.bombergame.gridobjects.obstacles.Crate;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

import java.util.HashSet;
import java.util.Set;

/** An abstract class for Grids, so that they may be implemented in different shapes. */
public abstract class Grid extends GameObject {

  /** How wide each tile is, in pixels. */
  final int tileWidth;

  /** The game that this SquareGrid is for. * */
  protected final BomberEngine game;

  /** The items in this grid. */
  private final Set<GridObject> items = new HashSet<>();
  /** The items to be added to the grid on the next update. */
  private final Set<GridObject> itemsToBeAdded = new HashSet<>();
  /** The items to be removed from the grid on the next update. */
  private final Set<GridObject> itemsToBeRemoved = new HashSet<>();

  /**
   * Create a new Grid.
   *
   * @param position The absolute position of this object.
   * @param z The z-level of the object.
   * @param tileWidth How wide each tile is, in pixels.
   * @param game The game this Grid belongs to.
   */
  Grid(Vector position, double z, int tileWidth, BomberEngine game) {
    super(position, z);
    this.tileWidth = tileWidth;
    this.game = game;
  }

  /**
   * Add an item to the grid.
   *
   * @param g The object to be added.
   * @return Whether the item is already in the grid or is already queued to be added.
   */
  public boolean addItem(GridObject g) {
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

  /** Update this grid, by managing the set of items it is responsible for. */
  @Override
  public void update(long ms) {
    // update the items in the grid
    items.removeAll(itemsToBeRemoved);
    itemsToBeRemoved.clear();
    items.addAll(itemsToBeAdded);
    itemsToBeAdded.clear();
  }

  /** @return true if g can move to the tile with coordinates p. */
  public boolean isValidMove(GridObject g, Coords p) {
    for (GridObject item : items) {
      if (!(item instanceof Droppable) && item != g && item.getGridCoords().equals(p)) {
        return false;
      }
    }
    return isValidTile(p);
  }

  /**
   * @param p Coords representing the position of a tile.
   * @return true iff there is a tile on the grid with position p.
   */
  public abstract boolean isValidTile(Coords p);

  /** @return The position of a random valid tile on the grid. */
  public abstract Coords randomCoordsInGrid();

  /**
   * @param p The coordinates of a tile.
   * @return The vector representing the position fo the tile on the screen.
   */
  public abstract Vector gridCoordsToAbsolutePosition(Coords p);

  /** Create a Crate in a random position on the grid. */
  public void makeRandomCrate() {
    boolean done;
    Coords r;
    do {
      done = true;
      // choose random coordinates to place the crate on
      r = randomCoordsInGrid();

      for (GridObject g : items) {
        // r is invalid if the tile with position r is not empty
        if (g.getGridCoords().equals(r)) {
          done = false;
        }
      }
      for (GridObject g : itemsToBeAdded) {
        // r is invalid if the tile with position r will soon be not empty
        if (g.getGridCoords().equals(r)) {
          done = false;
        }
      }
    } while (!done);
    Crate c = new Crate(r, 10, this, game);
    game.adoptLater(c);
  }

  /**
   * @param p The position where a bomb is desired to be placed.
   * @return true iff a bomb can be placed there, i.e. the spot is valid and unoccupied.
   */
  public boolean canPlaceBomb(Coords p) {
    for (GridObject g : items) {
      if (g.getGridCoords().equals(p) && g instanceof Bomb) {
        return false;
      }
    }
    for (GridObject g : itemsToBeAdded) {
      if (g.getGridCoords().equals(p) && g instanceof Bomb) {
        return false;
      }
    }
    return true;
  }

  /** @return all the Droppable items that this Grid manages. */
  public Set<Droppable> getDroppables() {
    Set<Droppable> droppables = new HashSet<>();
    for (GridObject g : items) {
      if (g instanceof Droppable) {
        droppables.add((Droppable) g);
      }
    }
    return droppables;
  }

  /** @return the items this Grid manages. */
  public Set<GridObject> getItems() {
    return items;
  }

  /** @return the width of the tiles in this grid. */
  public int getTileWidth() {
    return this.tileWidth;
  }
}
