package com.group0565.bomberGame.gridobjects.droppables;

import com.group0565.bomberGame.core.BomberEngine;
import com.group0565.bomberGame.gridobjects.BomberMan;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

public class MultiplebombPowerUp extends Droppable {
  /** The booleans that determines whether achievements have been unclocked. */
  private boolean multiple_bomb_5_unlocked = false;
  private boolean multiple_bomb_6_unlocked = false;
  /** The bitmap of this Firepower Power up */
  private Bitmap IMAGE;

  /**
   * Constructs a new MultiplebombPowerUp.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param grid The grid this crate is within.
   * @param game The game this crate belongs to.
   */
  public MultiplebombPowerUp(Coords position, double z, Grid grid, BomberEngine game) {
    super(position, z, grid, game);
  }

  /** Increases BomberMan bm's max number of bombs the can place
   * collected and checks for achievements related to Multiplebomb PowerUp */
  public void affectPlayer(BomberMan bm) {
    bm.setNumSimultaneousBombs(bm.getNumSimultaneousBombs() + 1);

    if (bm.getNumSimultaneousBombs() == 5 && !multiple_bomb_5_unlocked) {
      getEngine().getAchievementManager().unlockAchievement("BomberMan", "Bomber_Multiple_Bomb_5");
      multiple_bomb_5_unlocked = true;
    }
    if (bm.getNumSimultaneousBombs() == 6 && !multiple_bomb_6_unlocked) {
      getEngine().getAchievementManager().unlockAchievement("BomberMan", "Bomber_Multiple_Bomb_6");
      multiple_bomb_6_unlocked = true;
    }
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
    canvas.drawBitmap(IMAGE, getAbsolutePosition(), new Vector(grid.getTileWidth()));
  }

  @Override
  public void init() {
    super.init();
    IMAGE = getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(2, 3);
  }
}
