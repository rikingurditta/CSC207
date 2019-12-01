package com.group0565.bomberGame.bombs;

import com.group0565.bomberGame.BomberGame;
import com.group0565.bomberGame.BomberMan;
import com.group0565.math.Coords;
import com.group0565.bomberGame.GridObject;
import com.group0565.bomberGame.SquareGrid;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Vector;

public class NormalBomb extends Bomb {

  public NormalBomb(Coords position, int z, BomberGame game, SquareGrid grid, BomberMan placedBy) {
    super(position, z, game, grid, placedBy);
  }

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
          placedBy.increaseDamageDealt();
        }
      }
    }
  }

  @Override
  public void draw(Canvas canvas) {
    if (!duringExplosion) {
      canvas.drawRect(getAbsolutePosition(), new Vector(100, 100), currPaintCan);
    } else {
      canvas.drawRect(
          getAbsolutePosition().add(new Vector(-(getStrength() * 100), 0)),
          new Vector(2 * getStrength() * 100 + 100, 100),
          currPaintCan);
      canvas.drawRect(
          getAbsolutePosition().add(new Vector(0, -(getStrength() * 100))),
          new Vector(100, 2 * (getStrength() * 100) + 100),
          currPaintCan);
    }
  }
}
