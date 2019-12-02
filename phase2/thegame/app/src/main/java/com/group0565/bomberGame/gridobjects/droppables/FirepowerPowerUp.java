package com.group0565.bomberGame.gridobjects.droppables;

import com.group0565.bomberGame.core.BomberEngine;
import com.group0565.bomberGame.gridobjects.BomberMan;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

public class FirepowerPowerUp extends Droppable {
  /** The booleans that determines whether achievements have been unclocked. */
  private boolean fire_power_6_unlocked = false;
  private boolean fire_power_max_unlocked = false;
  /** The bitmap of this Firepower Power up */
  private Bitmap IMAGE;

  /**
   * Constructs a new FirepowerPowerUp.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param grid The grid this crate is within.
   * @param game The game this crate belongs to.
   */
  public FirepowerPowerUp(Coords position, double z, Grid grid, BomberEngine game) {
    super(position, z, grid, game);

  }

  /** Increases BomberMan bm's bomb strength
   * collected and checks for achievements related to FirepowerPowerUp */
  public void affectPlayer(BomberMan bm) {
    if (bm.getBombStrength() < 7) {
      bm.setBombStrength(bm.getBombStrength() + 1);
    }

    if (bm.getBombStrength() == 6 && !fire_power_6_unlocked) {
      getEngine().getAchievementManager().unlockAchievement("BomberMan", "Bomber_Fire_Power_6");
      fire_power_6_unlocked = true;
    }
    if (bm.getBombStrength() == 7 && !fire_power_max_unlocked) {
      getEngine().getAchievementManager().unlockAchievement("BomberMan", "Bomber_Fire_Power_MAX");
      fire_power_max_unlocked = true;
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
    IMAGE = getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(3, 3);
  }
}
