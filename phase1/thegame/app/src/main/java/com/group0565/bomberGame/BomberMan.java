package com.group0565.bomberGame;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.bomberGame.bombs.NormalBomb;
import com.group0565.bomberGame.input.BomberInput;
import com.group0565.bomberGame.input.InputSystem;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.math.Vector;

/** A BomberMan, aka a player in the game. */
public class BomberMan extends GameObject {
  /** The object representing the state of the inputs for this player. */
  private BomberInput input = new BomberInput();

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

  private SquareGrid grid;

  /**
   * Constructs a new BomberMan.
   *
   * @param position The position (relative or absolute) of this object.
   * @param z The z-level of the object.
   * @param inputSystem The object managing the inputs controlling this player.
   * @param grid
   */
  public BomberMan(
      Vector position, double z, InputSystem inputSystem, BomberGame game, SquareGrid grid) {
    super(position, z);
    this.inputSystem = inputSystem;
    this.game = game;
    this.grid = grid;
  }

  /**
   * Constructs a new BomberMan.
   *
   * @param position The position (relative or absolute) of this object.
   * @param inputSystem The object managing the inputs controlling this player.
   */
  public BomberMan(Vector position, InputSystem inputSystem, BomberGame game, SquareGrid grid) {
    super(position);
    this.inputSystem = inputSystem;
    this.game = game;
    this.grid = grid;
  }

  /**
   * Draws ONLY this object to canvas. Its children are not drawn by this method.
   *
   * @param canvas The Canvas on which to draw this player.
   */
  @Override
  public void draw(Canvas canvas) {
    // Set our color to Red
    Paint p = new Paint();
    p.setARGB(255, 0, 255, 0);
    // Draw an rectangle at our touch position
    canvas.drawRect(
        getAbsolutePosition().getX(),
        getAbsolutePosition().getY(),
        getAbsolutePosition().getX() + 100,
        getAbsolutePosition().getY() + 100,
        p);
  }

  /**
   * Updates the player based on input, as processed by this player's InputSystem.
   *
   * @param ms Elapsed time in milliseconds since last update.
   */
  @Override
  public void update(long ms) {

    Vector pos = this.getAbsolutePosition();

    // if the player is ready for the next direction input
    if (readyToMove) {
      // get the next input from inputSystem
      input = inputSystem.nextInput();

      float dist = 100; // temp, will be updated later to use actual distance between tiles

      // calculate the position to move to
      direction = new Vector();
      if (input.up) direction = new Vector(0, -dist);
      if (input.down) direction = new Vector(0, dist);
      if (input.left) direction = new Vector(-dist, 0);
      if (input.right) direction = new Vector(dist, 0);
      target = pos.add(direction);
      if (!grid.isValidMove(target)) target = this.getAbsolutePosition();

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
  private void dropBomb() {
    GameObject bomb = new NormalBomb(getAbsolutePosition(), -1, this.game);
    game.adoptLater(bomb);
  }
}
