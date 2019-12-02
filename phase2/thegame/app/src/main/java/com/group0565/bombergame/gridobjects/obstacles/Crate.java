package com.group0565.bombergame.gridobjects.obstacles;

import com.group0565.bombergame.core.BomberEngine;
import com.group0565.bombergame.grid.Grid;
import com.group0565.bombergame.gridobjects.GridObject;
import com.group0565.bombergame.gridobjects.droppables.FirepowerPowerUp;
import com.group0565.bombergame.gridobjects.droppables.MultiplebombPowerUp;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

/** A destructable obstacle that takes up one grid block. */
public class Crate extends GridObject {

  /** The game this Crate belongs to. */
  private BomberEngine game;

  /** This Crate's image. */
  private Bitmap image;

  /**
   * Constructs a new Crate.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param game The game this crate belongs to.
   * @param grid The grid this crate is within.
   */
  public Crate(Coords position, double z, Grid grid, BomberEngine game) {
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
  public Crate(Coords position, Grid grid, BomberEngine game) {
    this(position, 0, grid, game);
  }

  /** Initialize this Crate. Set up its image. */
  @Override
  public void init() {
    super.init();
    image = getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(1, 2);
  }

  /** Destroy this crate if the damage done to it is positive. */
  @Override
  public void damage(int d) {
    if (d > 0) {
      grid.remove(this);
      game.removeLater(this);
      double r = Math.random();
      if (r < 0.33) {
        FirepowerPowerUp loot = new FirepowerPowerUp(gridCoords, 30, grid, this.game);
        game.adoptLater(loot);
      } else if (r < 0.66) {
        MultiplebombPowerUp loot = new MultiplebombPowerUp(gridCoords, 30, grid, this.game);
        game.adoptLater(loot);
      }
    }
  }

  /**
   * Draws ONLY this object to canvas. Its children are not drawn by this method.
   *
   * @param canvas The Canvas on which to draw this crate.
   */
  @Override
  public void draw(Canvas canvas) {
    canvas.drawBitmap(image, getAbsolutePosition(), new Vector(grid.getTileWidth()));
  }
}
