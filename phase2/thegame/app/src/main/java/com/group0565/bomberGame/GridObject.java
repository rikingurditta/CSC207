package com.group0565.bomberGame;

import com.group0565.engine.gameobjects.GameObject;

public abstract class GridObject extends GameObject {
  /** The grid this object belongs to. */
  protected final SquareGrid grid;

  /** This object's position on the grid. */
  protected Coords gridCoords;

  /**
   * Create a new GridObject.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param grid The grid this object is within.
   */
  public GridObject(Coords position, double z, SquareGrid grid) {
    super(grid.gridCoordsToAbsolutePosition(position), z);
    this.gridCoords = position;
    this.grid = grid;
    grid.addItem(this, position);
  }
  /**
   * Create a new GridObject.
   *
   * @param position The position of this object on the grid.
   * @param grid The grid this object is within.
   */
  public GridObject(Coords position, SquareGrid grid) {
    this(position, 0, grid);
  }

  public Coords getGridCoords() {
    return gridCoords;
  }

  public void damage(int d) {}

  public abstract boolean isBomb();
}
