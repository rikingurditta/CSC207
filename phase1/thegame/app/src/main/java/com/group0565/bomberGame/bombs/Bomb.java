package com.group0565.bomberGame.bombs;

import android.graphics.Paint;

import com.group0565.bomberGame.BomberGame;
import com.group0565.bomberGame.BomberMan;
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
  BomberMan placedBy;

  private BomberGame game;

  public Bomb(Coords position, int z, BomberGame game, SquareGrid grid, BomberMan placedBy) {
    super(position, z, grid);
    this.game = game;
    this.p = new Paint();
    p.setARGB(123, 255, 213, 0);
    this.placedBy = placedBy;
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
      if (bombTimer < bombExplodeTime / 5 * 2) {
        p.setARGB(255, 240, 255, 0);
      } else if (bombTimer < bombExplodeTime / 5 * 3) {
        p.setARGB(255, 255, 206, 0);
      } else if (bombTimer < bombExplodeTime / 5 * 4) {
        p.setARGB(255, 255, 154, 0);
      } else {
        p.setARGB(250, 255, 90, 0);
      }
    } else if (bombTimer < bombExplodeTime + explosionDuration) {
      if (!duringExplosion) {
        // System.out.println("explosion " + this.getUUID());
        explode();
      }
      // actual explosion
      p.setARGB(200, 255, 0, 0);
      bombTimer += ms;
      duringExplosion = true;
    } else {
      // remove from game
      grid.remove(this);
      game.removeLater(this);
    }
  }

  public abstract void explode();

  public boolean isBomb() {
    return true;
  }
}
