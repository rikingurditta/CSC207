package com.group0565.bomberGame;

import android.util.Log;

import com.group0565.bomberGame.bombs.Bomb;
import com.group0565.bomberGame.bombs.NormalBomb;
import com.group0565.bomberGame.droppables.Droppable;
import com.group0565.bomberGame.grid.Grid;
import com.group0565.bomberGame.grid.GridObject;
import com.group0565.bomberGame.input.BomberInput;
import com.group0565.bomberGame.input.InputSystem;
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
  private ThemedPaintCan bodyPaintCan = new ThemedPaintCan("Bomber", "BomberMan.Body");

  private ThemedPaintCan textPaintCan = new ThemedPaintCan("Bomber", "Text.Text");

  /** The strength of the bombs this player can place. */
  private int bombStrength = 1;
  /** The number of simultaneous bombs this player can place at once. */
  private int numSimultaneousBombs = 1;

  /** The list of bombs this BomberMan has placed. */
  private ArrayList<Bomb> bombs = new ArrayList<>();

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
    bodyPaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
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
    // Draw an rectangle at our touch position
    canvas.drawRect(pos, new Vector(grid.getTileWidth(), grid.getTileWidth()), bodyPaintCan);
    canvas.drawText("hp: " + hp, pos, textPaintCan);
  }

  /**
   * Updates the player based on input, as processed by this player's InputSystem.
   *
   * @param ms Elapsed time in milliseconds since last update.
   */
  @Override
  public void update(long ms) {

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
      Log.i("bombs size", "size : " + bombs.size());

      readyToMove = false;
    }

    collectDroppable();
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
    NormalBomb bomb = new NormalBomb(gridCoords, -1, this.game, grid, this);
    game.adoptLater(bomb);
    this.bombs.add(bomb);

    // TODO make stats tracking nicer
    numBombsPlaced += 1;
    return true;
  }

  public void collectDroppable() {
    Coords pos = gridCoords;

    for (GridObject g : grid.getItems()) {
      Coords gPos = g.getGridCoords();
      if (pos.equals(gPos) && g.isDroppable()) {
        Log.i("Game Logic", "Recieved droppable");
        // ask rikin better approach
        // approach rn is casting. but i can move affect player
        // to grid obj and just have droppable as an interface
        Droppable d = (Droppable) g;
        d.affectPlayer(this);
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

  @Override
  public boolean isBomb() {
    return false;
  }

  @Override
  public boolean isDroppable() {
    return false;
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
