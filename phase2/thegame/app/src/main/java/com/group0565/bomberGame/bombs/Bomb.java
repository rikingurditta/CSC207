package com.group0565.bomberGame.bombs;

import com.group0565.bomberGame.BomberEngine;
import com.group0565.bomberGame.BomberMan;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.bomberGame.grid.GridObject;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;

public abstract class Bomb extends GridObject {

  boolean duringExplosion = false;
  private BomberMan placedBy;

  private long bombExplodeTime = 5000;
  private long explosionDuration = 1000;
  private long bombTimer = 0;
  private BomberEngine game;

  /** PaintCans for the various colour stages of the bomb's life cycle. */
  private ThemedPaintCan buildup1PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup1");
  private ThemedPaintCan buildup2PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup2");
  private ThemedPaintCan buildup3PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup3");
  private ThemedPaintCan buildup4PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup4");
  private ThemedPaintCan explosionPaintCan = new ThemedPaintCan("Bomber", "Bomb.Explosion");

  /** PaintCan for the current state of the bomb. */
  protected ThemedPaintCan currPaintCan = buildup1PaintCan;

  public Bomb(Coords position, int z, BomberEngine game, Grid grid, BomberMan placedBy) {
    super(position, z, grid);
    this.game = game;
    this.placedBy = placedBy;
  }

  @Override
  public void init() {
    super.init();
    buildup1PaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    buildup2PaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    buildup3PaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    buildup4PaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    explosionPaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }

  public void increaseStrength() {
    placedBy.setBombStrength(placedBy.getBombStrength() + 1);
  }

  public void decreaseStrength() {
    placedBy.setBombStrength(placedBy.getBombStrength() - 1);
  }

  public void increaseNumSumultaneousBombs() {
    placedBy.setNumSimultaneousBombs(placedBy.getNumSimultaneousBombs() + 1);
  }

  public void decreaseNumSumultaneousBombs() {
    placedBy.setNumSimultaneousBombs(placedBy.getNumSimultaneousBombs() + 1);
  }

  public int getStrength() {
    return placedBy.getBombStrength();
  }

  public int getNumSimultaneousBombs() {
    return placedBy.getNumSimultaneousBombs();
  }

  @Override
  public void update(long ms) {
    if (bombTimer < bombExplodeTime) {
      bombTimer += ms;
      if (bombTimer < bombExplodeTime / 5 * 2) {
        currPaintCan = buildup1PaintCan;
      } else if (bombTimer < bombExplodeTime / 5 * 3) {
        currPaintCan = buildup2PaintCan;
      } else if (bombTimer < bombExplodeTime / 5 * 4) {
        currPaintCan = buildup3PaintCan;
      } else {
        currPaintCan = buildup4PaintCan;
      }
    } else if (bombTimer < bombExplodeTime + explosionDuration) {
      if (!duringExplosion) {
        // System.out.println("explosion " + this.getUUID());
        explode();
      }
      // actual explosion
      currPaintCan = explosionPaintCan;
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

  public boolean isDroppable() { return false; }

  public BomberMan getPlacedBy() {
    return placedBy;
  }

}
