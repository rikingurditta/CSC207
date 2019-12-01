package com.group0565.bomberGame.gridobjects.droppables;

import com.group0565.bomberGame.core.BomberEngine;
import com.group0565.bomberGame.gridobjects.BomberMan;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

public class MultiplebombPowerUp extends Droppable {

  /** PaintCan for this crate's fill. */
  private final ThemedPaintCan paintCan;

  public MultiplebombPowerUp(Coords position, double z, Grid grid, BomberEngine game) {
    super(position, z, grid, game);
    paintCan = new ThemedPaintCan("Bomber", "Droppable.MultiplebombDroppable");
  }

  public void affectPlayer(BomberMan bm) {
    bm.setNumSimultaneousBombs(bm.getNumSimultaneousBombs() + 1);

    if (bm.getNumSimultaneousBombs() == 5) {
      getEngine().getAchievementManager().unlockAchievement("BomberMan", "Multiple Bomb 5");
    }
    if (bm.getNumSimultaneousBombs() == 6) {
      getEngine().getAchievementManager().unlockAchievement("BomberMan", "Multiple Bomb 6");
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
    canvas.drawRect(pos, new Vector(grid.getTileWidth(), grid.getTileWidth()), paintCan);
  }

  @Override
  public void init() {
    super.init();
    paintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }
}
