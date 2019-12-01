package com.group0565.bomberGame.gridobjects.bombs;

import com.group0565.bomberGame.core.BomberEngine;
import com.group0565.bomberGame.gridobjects.BomberMan;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.bomberGame.gridobjects.GridObject;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;

/** A bomb (abstract), a detonatable object. */
public abstract class Bomb extends GridObject {

  boolean duringExplosion = false;
  private BomberMan placedBy;

  /** The constant time it takes for a bomb to detonate */
  private long BOMB_EXPLODE_TIME = 5000;
  /** The constant time it takes for the bomb explosion */
  private long EXPLOSION_DURATION = 1000;
  /** The bomb fuse, if < 5000 (about to blow) if 5000 < bombTimer < 6000 (explosion) */
  private long bombTimer = 0;

  /** The game this Bomb belongs to. */
  private BomberEngine game;

  /** PaintCans for the various colour stages of the bomb's life cycle. */
  private ThemedPaintCan buildup1PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup1");

  private ThemedPaintCan buildup2PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup2");
  private ThemedPaintCan buildup3PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup3");
  private ThemedPaintCan buildup4PaintCan = new ThemedPaintCan("Bomber", "Bomb.Buildup4");
  private ThemedPaintCan explosionPaintCan = new ThemedPaintCan("Bomber", "Bomb.Explosion");

  /** PaintCan for the current state of the bomb. */
  ThemedPaintCan currPaintCan = buildup1PaintCan;

  /**
   * Constructs a new Bomb.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param game The game this crate belongs to.
   * @param grid The grid this crate is within.
   * @param placedBy The BomberMan who placed this bomb.
   */
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

  /**
   * Updates the bomb based how much time has passed since the bomb has been dropped.
   *
   * @param ms Elapsed time in milliseconds since last update.
   */
  @Override
  public void update(long ms) {
    if (bombTimer < BOMB_EXPLODE_TIME) {
      bombTimer += ms;
      if (bombTimer < BOMB_EXPLODE_TIME / 5 * 2) {
        currPaintCan = buildup1PaintCan;
      } else if (bombTimer < BOMB_EXPLODE_TIME / 5 * 3) {
        currPaintCan = buildup2PaintCan;
      } else if (bombTimer < BOMB_EXPLODE_TIME / 5 * 4) {
        currPaintCan = buildup3PaintCan;
      } else {
        currPaintCan = buildup4PaintCan;
      }
    } else if (bombTimer < BOMB_EXPLODE_TIME + EXPLOSION_DURATION) {
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
      placedBy.removeBombFromBombList(this);
      grid.remove(this);
      game.removeLater(this);
    }
  }

  public abstract void explode();

  public BomberMan getPlacedBy() {
    return placedBy;
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
}
