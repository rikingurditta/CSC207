package com.group0565.bomberGame.input;

import android.util.Log;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/** On-screen joystick and buttons input system. */
public class JoystickInput extends InputSystem {

  /** The absolute position of the joystick. */
  private Vector stickPosition;

  /** The absolute position of the bomb button. */
  private Vector buttonPosition;

  /** The width of one segment of the joystick, the width of the bomb button. */
  private int scale;

  /** Timer since last input. */
  private long inputRecentTimer;

  /** Time before input expires. */
  private long timerLimit = 100;
  // was 200, i felt it was a bit too long so 100.

  /**
   * Constructs a new JoystickInput.
   *
   * @param position The absolute position of this object.
   * @param z The z-level of the object.
   * @param stickRelativePosition The position of the joystick relative to absolute position.
   * @param buttonRelativePosition The position of the bomb button relative to absolute position.
   * @param scale The width of one segment of the joystick, the width of the bomb button.
   */
  public JoystickInput(
      Vector position,
      double z,
      Vector stickRelativePosition,
      Vector buttonRelativePosition,
      int scale) {
    super(position, z);
    this.scale = scale;
    this.stickPosition = position.add(stickRelativePosition);
    this.buttonPosition = position.add(buttonRelativePosition);
  }

  /**
   * Constructs a new JoystickInput.
   *
   * @param position The position (relative or absolute) of this object.
   * @param stickRelativePosition The position of the joystick relative to absolute position.
   * @param buttonRelativePosition The position of the bomb button relative to absolute position.
   * @param scale The width of one segment of the joystick, the width of the bomb button.
   */
  public JoystickInput(
      Vector position, Vector stickRelativePosition, Vector buttonRelativePosition, int scale) {
    this(position, 0, stickRelativePosition, buttonRelativePosition, scale);
  }

  /** @return the last input if it has not expired. */
  @Override
  public BomberInput nextInput() {
    if (inputRecentTimer >= timerLimit) input = new BomberInput();
    return input;
  }

  /**
   * Processes touch-based input to check how the joystick has been interacted with.
   *
   * @param event The input to be processed.
   * @return true
   */
  @Override
  public boolean processInput(InputEvent event) {
    input.reset();
    Vector eventPos = event.getPos();
    if (stickPosition.getX() < eventPos.getX()
        && eventPos.getX() < stickPosition.getX() + scale
        && stickPosition.getY() < eventPos.getY()
        && eventPos.getY() < stickPosition.getY() + scale) {
      input.up = true;
      inputRecentTimer = 0;
      Log.i("input received", "Up");
    } else if (stickPosition.getX() - scale < eventPos.getX()
        && eventPos.getX() < stickPosition.getX()
        && stickPosition.getY() + scale < eventPos.getY()
        && eventPos.getY() < stickPosition.getY() + 2 * scale) {
      input.left = true;
      inputRecentTimer = 0;
      Log.i("input received", "Left");
    } else if (stickPosition.getX() + scale < eventPos.getX()
        && eventPos.getX() < stickPosition.getX() + 2 * scale
        && stickPosition.getY() + scale < eventPos.getY()
        && eventPos.getY() < stickPosition.getY() + 2 * scale) {
      input.right = true;
      inputRecentTimer = 0;
      Log.i("input received", "Right");
    } else if (stickPosition.getX() < eventPos.getX()
        && eventPos.getX() < stickPosition.getX() + scale
        && stickPosition.getY() + 2 * scale < eventPos.getY()
        && eventPos.getY() < stickPosition.getY() + 3 * scale) {
      input.down = true;
      inputRecentTimer = 0;
      Log.i("input received", "Down");
    }

    // drop bomb
    if (buttonPosition.getX() < eventPos.getX()
        && eventPos.getX() < buttonPosition.getX() + scale
        && buttonPosition.getY() < eventPos.getY()
        && eventPos.getY() < buttonPosition.getY() + scale) {
      input.bomb = true;
      inputRecentTimer = 0;
      Log.i("input received", "Dropped Bomb");
    }

    return true;
  }

  /**
   * Draws the Joystick and buttons on the screen.
   *
   * @param canvas The Canvas on which to draw this player.
   */
  @Override
  public void draw(Canvas canvas) {
    Paint joyStickPaint = Paint.createInstance();
    joyStickPaint.setARGB(128, 0, 0, 128);

    // LEFT RIGHT UP DOWN PAD (draws a long vertical rectangle and two smaller squares beside it to
    // make a + sign)
    canvas.drawRect(
        stickPosition.getX(),
        stickPosition.getY(),
        stickPosition.getX() + scale,
        stickPosition.getY() + 3 * scale,
        joyStickPaint);
    canvas.drawRect(
        stickPosition.getX() + scale,
        stickPosition.getY() + scale,
        stickPosition.getX() + 2 * scale,
        stickPosition.getY() + 2 * scale,
        joyStickPaint);
    canvas.drawRect(
        stickPosition.getX() - scale,
        stickPosition.getY() + scale,
        stickPosition.getX(),
        stickPosition.getY() + 2 * scale,
        joyStickPaint);

    // DROP BOMB BUTTON
    Paint bombButtonPaint = Paint.createInstance();
    bombButtonPaint.setARGB(128, 255, 0, 0);
    canvas.drawRect(
        buttonPosition.getX(),
        buttonPosition.getY(),
        buttonPosition.getX() + scale,
        buttonPosition.getY() + scale,
        bombButtonPaint);
  }

  /** @param ms Elapsed time in milliseconds since last update. */
  @Override
  public void update(long ms) {
    if (inputRecentTimer < timerLimit) inputRecentTimer += ms;
  }
}
