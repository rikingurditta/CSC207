package com.group0565.bombergame.gridobjects.droppables;

import com.group0565.bombergame.core.BomberEngine;
import com.group0565.bombergame.grid.Grid;
import com.group0565.bombergame.gridobjects.BomberMan;
import com.group0565.bombergame.gridobjects.GridObject;
import com.group0565.math.Coords;

/** An object that can be dropped onto the map and picked up. */
public abstract class Droppable extends GridObject {
  /** The game this Crate belongs to. */
  private BomberEngine game;

  /**
   * Constructs a new Crate.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param game The game this crate belongs to.
   * @param grid The grid this crate is within.
   */
  Droppable(Coords position, double z, Grid grid, BomberEngine game) {
    super(position, z, grid);
    this.game = game;
    this.grid.addItem(this);
  }

  /**
   * Constructs a new Crate.
   *
   * @param position The position of this object on the grid.
   * @param game The game this crate belongs to.
   * @param grid The grid this crate is within.
   */
  Droppable(Coords position, Grid grid, BomberEngine game) {
    this(position, 0, grid, game);
  }

  /** Decides what happens to a player when this droppable is collected */
  public abstract void affectPlayer(BomberMan bm);

  /** Destroy this crate if the damage done to it is positive. */
  @Override
  public void damage(int d) {
    if (d > 0) {
      grid.remove(this);
      game.removeLater(this);
    }
  }
}
