package com.group0565.bombergame.gridobjects.bombs;

import com.group0565.bombergame.core.BomberEngine;
import com.group0565.bombergame.grid.Grid;
import com.group0565.bombergame.gridobjects.BomberMan;
import com.group0565.bombergame.gridobjects.GridObject;
import com.group0565.engine.interfaces.Bitmap;
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

  /** This Bomb's current image. */
  Bitmap currImage;

  private Bitmap BUILDUP_2_IMAGE;
  private Bitmap BUILDUP_3_IMAGE;
  private Bitmap BUILDUP_4_IMAGE;
  private Bitmap EXPLOSION_IMAGE;
  /** Images of this Bomb in its various stages before explosion. */
  private Bitmap BUILDUP_1_IMAGE;

  /**
   * Constructs a new Bomb.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param game The game this crate belongs to.
   * @param grid The grid this crate is within.
   * @param placedBy The BomberMan who placed this bomb.
   */
  Bomb(Coords position, int z, BomberEngine game, Grid grid, BomberMan placedBy) {
    super(position, z, grid);
    this.game = game;
    this.placedBy = placedBy;
  }

  /** Initialize this bomb. Set up its images. */
  @Override
  public void init() {
    super.init();
    BUILDUP_1_IMAGE =
        getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(0, 1);
    BUILDUP_2_IMAGE =
        getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(1, 1);
    BUILDUP_3_IMAGE =
        getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(2, 1);
    BUILDUP_4_IMAGE =
        getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(3, 1);
    EXPLOSION_IMAGE =
        getEngine().getGameAssetManager().getTileSheet("Bomber", "Grid_Objects").getTile(0, 2);
    currImage = BUILDUP_1_IMAGE;
  }

  /**
   * Updates the bomb based how much time has passed since the bomb has been dropped. Update the
   * image corresponding to its current state.
   *
   * @param ms Elapsed time in milliseconds since last update.
   */
  @Override
  public void update(long ms) {
    if (bombTimer < BOMB_EXPLODE_TIME) {
      bombTimer += ms;
      if (bombTimer < BOMB_EXPLODE_TIME / 5 * 2) {
        currImage = BUILDUP_1_IMAGE;
      } else if (bombTimer < BOMB_EXPLODE_TIME / 5 * 3) {
        currImage = BUILDUP_2_IMAGE;
      } else if (bombTimer < BOMB_EXPLODE_TIME / 5 * 4) {
        currImage = BUILDUP_3_IMAGE;
      } else {
        currImage = BUILDUP_4_IMAGE;
      }
    } else if (bombTimer < BOMB_EXPLODE_TIME + EXPLOSION_DURATION) {
      if (!duringExplosion) {
        explode();
      }
      // actual explosion
      currImage = EXPLOSION_IMAGE;
      bombTimer += ms;
      duringExplosion = true;
    } else {
      // remove from game
      placedBy.removeBombFromBombList(this);
      grid.remove(this);
      game.removeLater(this);
    }
  }
  /** Decides what happens to a player when a bomb explodes */
  public abstract void explode();

  /** @return the object that placed this bomb. */
  BomberMan getPlacedBy() {
    return placedBy;
  }

  /** @return the radius of this Bomb's explosion. */
  int getStrength() {
    return placedBy.getBombStrength();
  }
}
