package com.group0565.bomberGame.bombs;

import com.group0565.bomberGame.BomberGame;
import com.group0565.bomberGame.BomberMan;
import com.group0565.bomberGame.GridObject;
import com.group0565.bomberGame.SquareGrid;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;

public abstract class Bomb extends GridObject {

  boolean duringExplosion = false;
  BomberMan placedBy;
  private int strength = 2;
  private int numSimultaneousBombs = 1;
  private long bombExplodeTime = 5000;
  private long explosionDuration = 1000;
  private long bombTimer = 0;
  private BomberGame game;

  /** PaintCans for the various colour stages of the bomb's life cycle. */
  private ThemedPaintCan buildup1PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup1");
  private ThemedPaintCan buildup2PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup2");
  private ThemedPaintCan buildup3PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup3");
  private ThemedPaintCan buildup4PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup4");
  private ThemedPaintCan explosionPaintCan = new ThemedPaintCan("Bomber", "Bomb.Explosion");

  /** PaintCan for the current state of the bomb. */
  protected ThemedPaintCan currPaintCan = buildup1PaintCan;

  public Bomb(Coords position, int z, BomberGame game, SquareGrid grid, BomberMan placedBy) {
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
        currPaintCan = buildup1PaintCan;
//        p.setARGB(255, 240, 255, 0);
      } else if (bombTimer < bombExplodeTime / 5 * 3) {
        currPaintCan = buildup2PaintCan;
//        p.setARGB(255, 255, 206, 0);
      } else if (bombTimer < bombExplodeTime / 5 * 4) {
        currPaintCan = buildup3PaintCan;
//        p.setARGB(255, 255, 154, 0);
      } else {
        currPaintCan = buildup4PaintCan;
//        p.setARGB(250, 255, 90, 0);
      }
    } else if (bombTimer < bombExplodeTime + explosionDuration) {
      if (!duringExplosion) {
        // System.out.println("explosion " + this.getUUID());
        explode();
      }
      // actual explosion
      currPaintCan = explosionPaintCan;
//      p.setARGB(200, 255, 0, 0);
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
