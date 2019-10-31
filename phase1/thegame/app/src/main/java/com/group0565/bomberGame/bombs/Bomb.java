package com.group0565.bomberGame.bombs;

import android.graphics.Paint;

import com.group0565.bomberGame.BomberGame;
import com.group0565.bomberGame.Coords;
import com.group0565.bomberGame.GridObject;
import com.group0565.bomberGame.SquareGrid;

public abstract class Bomb extends GridObject {

  private int strength = 2;
  private int numSimultaneousBombs = 1;
  private long bombExplodeTime = 5000;
  private long explosionDuration = 1000;
  boolean duringExplosion = false;
  private long bombTimer = 0;
  Paint p;

  private BomberGame game;

  public Bomb(Coords position, int z, BomberGame game, SquareGrid grid) {
    super(position, z, grid);
    this.game = game;
    this.p = new Paint();
    p.setARGB(123, 255, 213, 0);
  }

  public void increaseStrength() {
    strength += 1;
  }

  public void decreaseStrength() {
    strength -= 1;
  }

  public void increaseNumSumultaneousBombs() {
    numSimultaneousBombs += 1;
  }

  public void decreaseNumSumultaneousBombs() {
    numSimultaneousBombs -= 1;
  }

  public int getStrength() {
    return strength;
  }

  public int getNumSimultaneousBombs() {
    return numSimultaneousBombs;
  }

  @Override
  public void update(long ms) {

    if (bombTimer < bombExplodeTime) {
      bombTimer += ms;
      if (bombTimer < bombExplodeTime / 3 * 2) {
        p.setARGB(150, 251, 163, 26);
      } else {
        p.setARGB(180, 243, 114, 32);
      }
    } else if (bombTimer < bombExplodeTime + explosionDuration) {
      // actual explosion
      p.setARGB(200, 255, 30, 32);
      bombTimer += ms;
      duringExplosion = true;
    } else {
      // remove from game
      grid.remove(this);
      game.removeLater(this);
    }
  }
}
