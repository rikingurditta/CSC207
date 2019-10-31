package com.group0565.bomberGame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.group0565.bomberGame.bombs.NormalBomb;
import com.group0565.bomberGame.input.BomberInput;
import com.group0565.bomberGame.input.InputSystem;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

/** A BomberMan, aka a player in the game. */
public class BomberMan extends GridObject {

  /** The game this BomberMan belongs to. */
  private BomberGame game;

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

  private int hp;

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
      Coords position,
      double z,
      InputSystem inputSystem,
      BomberGame game,
      SquareGrid grid,
      int hp) {
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
  public BomberMan(
      Coords position, InputSystem inputSystem, BomberGame game, SquareGrid grid, int hp) {
    super(position, grid);
    this.inputSystem = inputSystem;
    this.game = game;
    this.hp = hp;
  }

  /**
   * Draws ONLY this object to canvas. Its children are not drawn by this method.
   *
   * @param canvas The Canvas on which to draw this player.
   */
  @Override
  public void draw(Canvas canvas) {
    Vector pos = getAbsolutePosition();
    Paint p = new Paint();
    p.setColor(Color.GREEN);
    // Draw an rectangle at our touch position
    canvas.drawRect(pos.getX(), pos.getY(),pos.getX() + 100, pos.getY() + 100, p);
    Paint textPaint = new Paint();
    textPaint.setTextSize(50);
    textPaint.setColor(Color.BLACK);
    canvas.drawText(Integer.toString(hp), pos.getX(), pos.getY(), textPaint);
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

      readyToMove = false;
    }

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
    if (!grid.canPlaceBomb(gridCoords)) {
      return false;
    }
    GameObject bomb = new NormalBomb(gridCoords, -1, this.game, grid);
    game.adoptLater(bomb);
    return true;
  }

  public void damage(int d) {
    hp -= d;
  }

  @Override
  public boolean isBomb() {
    return false;
  }
}
