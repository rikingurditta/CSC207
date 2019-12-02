package com.group0565.bombergame.gridobjects.droppables;

import com.group0565.bombergame.core.BomberEngine;
import com.group0565.bombergame.grid.Grid;
import com.group0565.bombergame.gridobjects.BomberMan;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

/** A power up that increases the number of Bombs that can be held. */
public class MultiplebombPowerUp extends Droppable {
  /** The booleans that keep track of whether achievements have been unlocked. */
  private boolean multiple_bomb_5_unlocked = false;

  private boolean multiple_bomb_6_unlocked = false;
  /** The bitmap of this Firepower Power up */
  private Bitmap image;

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

  /**
   * Increases BomberMan bm's max number of bombs the can place collected and checks for
   * achievements related to Multiplebomb PowerUp
   */
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
    canvas.drawBitmap(image, getAbsolutePosition(), new Vector(grid.getTileWidth()));
  }

  /** Initialize this MultiplebombPowerUp. Set up its image. */
  @Override
  public void init() {
    super.init();
    image = getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(2, 3);
  }
}
