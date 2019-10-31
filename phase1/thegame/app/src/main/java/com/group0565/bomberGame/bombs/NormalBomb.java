package com.group0565.bomberGame.bombs;

import android.graphics.Canvas;

import com.group0565.bomberGame.BomberGame;
import com.group0565.bomberGame.Coords;
import com.group0565.bomberGame.GridObject;
import com.group0565.bomberGame.SquareGrid;

public class NormalBomb extends Bomb {

  public NormalBomb(Coords position, int z, BomberGame game, SquareGrid grid) {
    super(position, z, game, grid);
  }

  @Override
  public void explode() {
    for (GridObject g : grid.getItems()) {
      Coords pos = gridCoords;
      Coords gPos = g.getGridCoords();
      if ((Math.abs(gPos.x - pos.x) <= 2 && gPos.y == pos.y)
          || (Math.abs(gPos.y - pos.y) <= 2 && gPos.x == pos.x)) {
        g.damage(1);
      }
    }
  }

  @Override
  public void draw(Canvas canvas) {
    if (!duringExplosion) {
      canvas.drawRect(
          getAbsolutePosition().getX(),
          getAbsolutePosition().getY(),
          getAbsolutePosition().getX() + 100,
          getAbsolutePosition().getY() + 100,
          p);
    } else {
      canvas.drawRect(
          getAbsolutePosition().getX() - (getStrength() * 100),
          getAbsolutePosition().getY(),
          getAbsolutePosition().getX() + (getStrength() * 100) + 100,
          getAbsolutePosition().getY() + 100,
          p);
      canvas.drawRect(
          getAbsolutePosition().getX(),
          getAbsolutePosition().getY() - (getStrength() * 100),
          getAbsolutePosition().getX() + 100,
          getAbsolutePosition().getY() + ((getStrength() * 100) + 100),
          p);
    }
  }
}
