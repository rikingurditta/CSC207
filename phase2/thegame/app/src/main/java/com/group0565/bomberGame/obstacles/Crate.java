package com.group0565.bomberGame.obstacles;

import com.group0565.bomberGame.BomberGame;
import com.group0565.bomberGame.Droppable;
import com.group0565.bomberGame.GridObject;
import com.group0565.bomberGame.SquareGrid;
import com.group0565.bomberGame.bombs.NormalBomb;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

/** A destructable obstacle that takes up one grid block. */
public class Crate extends GridObject {

  /** The game this Crate belongs to. */
  private BomberGame game;

  /** PaintCan for this crate's fill. */
  private ThemedPaintCan paintCan = new ThemedPaintCan("Bomber", "Crate.Crate");

  /**
   * Constructs a new Crate.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param game The game this crate belongs to.
   * @param grid The grid this crate is within.
   */
  public Crate(Coords position, double z, SquareGrid grid, BomberGame game) {
    super(position, z, grid);
    this.game = game;
    this.grid.addItem(this, position);
  }

  /**
   * Constructs a new Crate.
   *
   * @param position The position of this object on the grid.
   * @param game The game this crate belongs to.
   * @param grid The grid this crate is within.
   */
  public Crate(Coords position, SquareGrid grid, BomberGame game) {
    this(position, 0, grid, game);
  }

  @Override
  public void init() {
    super.init();
    paintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }

  /** Destroy this crate if the damage done to it is positive. */
  @Override
  public void damage(int d) {
    if (d > 0) {
      grid.remove(this);
      game.removeLater(this);

      GameObject loot = new Droppable(gridCoords, -2, grid, this.game);
      game.adoptLater(loot);
    }

  }

  @Override
  public boolean isBomb() {
    return false;
  }

  @Override
  public boolean isDroppable() {
    return false;
  }

  /**
   * Draws ONLY this object to canvas. Its children are not drawn by this method.
   *
   * @param canvas The Canvas on which to draw this crate.
   */
  @Override
  public void draw(Canvas canvas) {
    Vector pos = getAbsolutePosition();
    // Draw a rectangle at our touch position
    canvas.drawRect(pos, new Vector(grid.getTileWidth(), grid.getTileWidth()), paintCan);
  }
}
