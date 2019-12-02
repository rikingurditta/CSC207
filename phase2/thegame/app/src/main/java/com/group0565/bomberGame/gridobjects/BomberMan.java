package com.group0565.bomberGame.gridobjects;

import com.group0565.bomberGame.core.BomberEngine;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.bomberGame.gridobjects.bombs.Bomb;
import com.group0565.bomberGame.gridobjects.bombs.NormalBomb;
import com.group0565.bomberGame.gridobjects.droppables.Droppable;
import com.group0565.bomberGame.input.BomberInput;
import com.group0565.bomberGame.input.InputSystem;
import com.group0565.bomberGame.input.RandomInput;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Coords;
import com.group0565.math.Vector;

import java.util.ArrayList;

/** A BomberMan, aka a player in the game. */
public class BomberMan extends GridObject {

  /** The game this BomberMan belongs to. */
  private BomberEngine game;

  /**
   * The object processing the input for this player. Is adopted by this BomberMan, so all input
   * events get passed down to it. (maybe get rid of this field because we p much never need it)
   */
  private InputSystem inputSystem;

  /** Whether or not this player is ready to choose the next target to move to. */
  private boolean readyToMove = true;

  /** The direction this player is moving in. */
  private Vector direction = new Vector();

  /** The point that this player is trying to move to. */
  private Vector target;

  /** The speed at which this player moves, in units per millisecond. */
  private float speed = 2.0f / 1000;

  /** The number of health points this player has. */
  private int hp;

  /** The number of bombs this player has placed. */
  private int numBombsPlaced;

  /** The number of points of damage this player has dealt. */
  private int damageDealt;

  /** PaintCans for this player's appearance and status text. */
  private Bitmap image;

  private final ThemedPaintCan textPaintCan = new ThemedPaintCan("Bomber", "Text.Text");

  /** The strength of the bombs this player can place. */
  private int bombStrength = 2;
  /** The number of simultaneous bombs this player can place at once. */
  private int numSimultaneousBombs = 1;

  /** The list of bombs this BomberMan has placed. */
  private ArrayList<Bomb> bombs = new ArrayList<>();

  private boolean two_bombs_at_once_unlocked = false;
  private boolean three_bombs_at_once_unlocked = false;

  /**
   * Constructs a new BomberMan.
   *
   * @param position The position of this object on the grid.
   * @param z The z-level of the object.
   * @param inputSystem The object managing the inputs controlling this player.
   * @param game The game this player belongs to.
   * @param grid The grid this player is within.
   */
  public BomberMan(
      Coords position, double z, InputSystem inputSystem, BomberEngine game, Grid grid, int hp) {
    super(position, z, grid);
    this.inputSystem = inputSystem;
    this.game = game;
    this.hp = hp;
  }

  /**
   * Constructs a new BomberMan.
   *
   * @param position The position of this object on the grid.
   * @param inputSystem The object managing the inputs controlling this player.
   * @param game The game this player belongs to.
   * @param grid The grid this player is within.
   */
  public BomberMan(Coords position, InputSystem inputSystem, BomberEngine game, Grid grid, int hp) {
    this(position, 0, inputSystem, game, grid, hp);
  }

  @Override
  public void init() {
    super.init();
    image = getEngine().getGameAssetManager().getTileSheet("Bomber", "BomberMan").getTile(0, 0);
    textPaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
  }

  /**
   * Draws ONLY this object to canvas. Its children are not drawn by this method.
   *
   * @param canvas The Canvas on which to draw this player.
   */
  @Override
  public void draw(Canvas canvas) {
    Vector pos = getAbsolutePosition();
    canvas.drawBitmap(image, pos, new Vector(grid.getTileWidth()));
    canvas.drawText("hp: " + hp, pos.add(new Vector(0, -32)), textPaintCan);
  }

  public void checkAchievements() {
    if (!(inputSystem instanceof RandomInput)) {
      if (bombs.size() == 2 && !two_bombs_at_once_unlocked) {
        getEngine()
            .getAchievementManager()
            .unlockAchievement("BomberMan", "Bomber_Two_bombs_at_once");
        two_bombs_at_once_unlocked = true;
      }
      if (bombs.size() == 3 && !three_bombs_at_once_unlocked) {
        getEngine()
            .getAchievementManager()
            .unlockAchievement("BomberMan", "Bomber_Three_bombs_at_once");
        three_bombs_at_once_unlocked = true;
      }
    }
  }
  /**
   * Updates the player based on input, as processed by this player's InputSystem.
   *
   * @param ms Elapsed time in milliseconds since last update.
   */
  @Override
  public void update(long ms) {

    checkAchievements();

    if (hp <= 0) {
      grid.remove(this);
      game.removeLater(this);
    }

    Vector pos = this.getAbsolutePosition();

    // if the player is ready for the next direction input
    if (readyToMove) {
      // get the next input from inputSystem
      BomberInput input = inputSystem.nextInput();

      // calculate the grid position to move to
      Coords gridDir = new Coords();
      if (input.up) gridDir = new Coords(0, -1);
      if (input.down) gridDir = new Coords(0, 1);
      if (input.left) gridDir = new Coords(-1, 0);
      if (input.right) gridDir = new Coords(1, 0);

      Coords targetGridCoords = gridCoords.add(gridDir);
      if (!grid.isValidMove(this, targetGridCoords)) {
        target = this.getAbsolutePosition();
        direction = new Vector();
      } else {
        target = grid.gridCoordsToAbsolutePosition(targetGridCoords);
        gridCoords = targetGridCoords;
        direction = target.subtract(this.getAbsolutePosition());
      }
      if (input.bomb) dropBomb();

      readyToMove = false;
    }
    // after u move to new tile check if theres a droppable there
    collectDroppable();

    // graphic move
    Vector newPos = pos.add(direction.multiply((float) ms * speed));

    // if the calculated position is past the target, only move to the target
    if (newPos.subtract(pos).norm() <= target.subtract(pos).norm()) {
      this.setAbsolutePosition(newPos);
    } else {
      this.setAbsolutePosition(target);
    }

    if (this.getAbsolutePosition().equals(target)) readyToMove = true;
  }

  /** Drops bomb at current location. */
  private boolean dropBomb() {
    if (!grid.canPlaceBomb(gridCoords) | bombs.size() >= numSimultaneousBombs) {
      return false;
    }
    NormalBomb bomb = new NormalBomb(gridCoords, 25, this.game, grid, this);
    game.adoptLater(bomb);
    this.bombs.add(bomb);

    // TODO make stats tracking nicer
    numBombsPlaced += 1;
    return true;
  }
  /** Helper method in update responsible for collecting Droppables and affecting this bomberMan */
  public void collectDroppable() {
    Coords pos = gridCoords;

    for (Droppable g : grid.getDroppables()) {
      if (g.getGridCoords().equals(pos)) {
        g.affectPlayer(this);
        grid.remove(g);
        game.removeLater(g);
      }
    }
  }

  public void damage(int d) {
    hp -= d;
  }

  public int getNumBombsPlaced() {
    return numBombsPlaced;
  }

  public int getDamageDealt() {
    return damageDealt;
  }

  public void increaseDamageDealt() {
    damageDealt += 1;
  }

  public int getHp() {
    return hp;
  }

  public int getBombStrength() {
    return bombStrength;
  }

  public int getNumSimultaneousBombs() {
    return numSimultaneousBombs;
  }

  public void setBombStrength(int bombStrength) {
    this.bombStrength = bombStrength;
  }

  public void setNumSimultaneousBombs(int numSimultaneousBombs) {
    this.numSimultaneousBombs = numSimultaneousBombs;
  }

  public void removeBombFromBombList(Bomb bomb) {
    bombs.remove(bomb);
  }
}
