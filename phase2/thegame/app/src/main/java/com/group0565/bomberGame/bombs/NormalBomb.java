package com.group0565.bomberGame.bombs;

import com.group0565.bomberGame.BomberEngine;
import com.group0565.bomberGame.BomberMan;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.bomberGame.grid.GridObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

public class NormalBomb extends Bomb {

  public NormalBomb(Coords position, int z, BomberEngine game, Grid grid, BomberMan placedBy) {
    super(position, z, game, grid, placedBy);
  }

  /** Explodes bombs and handles explosion collision by checking gridObjects within the bomb strength.*/
  @Override
  public void explode() {
    for (GridObject g : grid.getItems()) {
      Coords pos = gridCoords;
      Coords gPos = g.getGridCoords();

      // this if statement needs to bixed it should be && not bombs. This way it deals damage to
      // everything non bomb rather than only crate
      if ((Math.abs(gPos.x - pos.x) <= getStrength() && gPos.y == pos.y)
          || (Math.abs(gPos.y - pos.y) <= getStrength() && gPos.x == pos.x)) {
        if (g != this) {
          g.damage(1);
          this.getPlacedBy().increaseDamageDealt();
        }
      }
    }
  }

  @Override
  public void draw(Canvas canvas) {
    int scale = grid.getTileWidth();
    if (!duringExplosion) {
      // if not exploding, draw bomb as single tile
      canvas.drawRect(getAbsolutePosition(), new Vector(scale, scale), currPaintCan);
    } else {
      // if exploding, draw + shaped explosion
      // horizontal rectangle 5 tiles wide and 1 tile tall
      canvas.drawRect(
          getAbsolutePosition().add(new Vector(-getStrength() * scale, 0)),
          new Vector(2 * getStrength() * scale + scale, scale),
          currPaintCan);
      canvas.drawRect(
          getAbsolutePosition().add(new Vector(0, -getStrength() * scale)),
          new Vector(scale, 2 * getStrength() * scale + scale),
          currPaintCan);
    }
  }
}
