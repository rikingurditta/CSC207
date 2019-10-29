package com.group0565.bomberGame.bombs;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.group0565.bomberGame.BomberGame;
import com.group0565.math.Vector;

public class NormalBomb extends Bomb {

  public NormalBomb(Vector position, int z, BomberGame game) {
    super(position, z, game);
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
    }
    else{
        canvas.drawRect(
                getAbsolutePosition().getX() - (getStrength()*100),
                getAbsolutePosition().getY(),
                getAbsolutePosition().getX() + (getStrength()*100) + 100,
                getAbsolutePosition().getY() + 100,
                p);
        canvas.drawRect(
                getAbsolutePosition().getX(),
                getAbsolutePosition().getY() - (getStrength()*100),
                getAbsolutePosition().getX()+100,
                getAbsolutePosition().getY() + ((getStrength()*100) + 100),
                p);


    }
  }
}
