package com.group0565.engine.gameobjects;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.GameEngine;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;

/** A MenuObject representation of a Button */
public class Button extends MenuObject implements Observable {
  /** The button down event name */
  public static final String EVENT_DOWN = "Button_Pressed";
  /** The button up event name */
  public static final String EVENT_UP = "Button_Released";
  /** A boolean of whether the button is currently pressed */
  private boolean pressed;
  /** The bitmap of the button up state */
  private Bitmap up;
  /** The bitmap of the button down state */
  private Bitmap down;

  /**
   * Create a new button
   *
   * @param position The position to create in
   * @param size The size of the button
   */
  public Button(Vector position, Vector size) {
    this(position, size, null, null);
  }

  /**
   * Create a new button
   *
   * @param position The position to create in
   * @param size The size of the button
   * @param up The up state bitmap
   * @param down The down state bitmap
   */
  public Button(Vector position, Vector size, Bitmap up, Bitmap down) {
    super(size);
    this.setRelativePosition(position);
    this.up = up;
    this.down = down;
  }

  /**
   * Create a new button
   *
   * @param position The position to create in
   * @param size The size of the button
   */
  public Button(Vector position, Vector size, Bitmap image) {
    this(position, size, image, image);
  }

  /**
   * Create a new button
   *
   * @param size The size of the button
   */
  public Button(Vector size) {
    super(size);
  }

  /**
   * Create a new button
   *
   * @param size The size of the button
   * @param image The button bitmap
   */
  public Button(Vector size, Bitmap image) {
    super(size);
    this.up = image;
    this.down = image;
  }

  /**
   * Create a new button
   *
   * @param manager The asset manager
   * @param name The asset name
   * @param set The asset set
   * @param tileX The x tile of the image in the bitmap sheet
   * @param tileY The y tile of the image in the bitmap sheet
   * @param size The size of the button
   */
  public Button(
      Vector size, GameAssetManager manager, String set, String name, int tileX, int tileY) {
    this(size);
    this.up = manager.getTileSheet(set, name).getTile(tileX, tileY);
    this.down = up;
  }

  /**
   * Create a new button
   *
   * @param manager The asset manager
   * @param name The asset name
   * @param set The asset set
   * @param size The size of the button
   */
  public Button(Vector size, GameAssetManager manager, String set, String name) {
    this(size, manager, set, name, 0, 0);
  }

  @Override
  public void update(long ms) {
    super.update(ms);
    float sx = getSize().getX();
    float sy = getSize().getY();
    float ax = getAbsolutePosition().getX();
    float ay = getAbsolutePosition().getY();
    boolean f = false;
    for (InputEvent event : this.getCapturedEvents()) {
      float ex = event.getPos().getX();
      float ey = event.getPos().getY();
      if (ax <= ex && ex <= ax + sx) {
        if (ay <= ey && ey <= ax + sy) {
          f = true;
          break;
        }
      }
    }
    if (!f && pressed) {
      setPressed(false);
      notifyObservers();
    }
  }

  @Override
  public void draw(Canvas canvas, Vector pos, Vector size) {
    super.draw(canvas, pos, size);
    Bitmap drawable = (pressed ? down : up);
    if (drawable != null) {
      canvas.drawBitmap(drawable, pos, size);
    }
  }

  @Override
  protected void onEventCapture(InputEvent event) {
    super.onEventCapture(event);
    setPressed(true);
  }

  @Override
  public boolean processInput(InputEvent event) {
    if (!isEnable() || !isSelfEnable()) return super.processInput(event);
    Vector pos = event.getPos();
    float ex = pos.getX();
    float ey = pos.getY();
    float sx = getSize().getX();
    float sy = getSize().getY();
    float ax = getAbsolutePosition().getX();
    float ay = getAbsolutePosition().getY();
    if (ax <= ex && ex <= ax + sx) {
      if (ay <= ey && ey <= ay + sy) {
        captureEvent(event);
        return true;
      }
    }
    return super.processInput(event);
  }

  /**
   * Get button pressed state
   *
   * @return Return true if the button is pressed, false otherwise
   */
  public boolean isPressed() {
    return pressed;
  }

  /**
   * Sets the pressed state and notify observers
   *
   * @param pressed The new pressed state
   */
  public void setPressed(boolean pressed) {
    this.pressed = pressed;
    this.notifyObservers(new ObservationEvent(pressed ? EVENT_DOWN : EVENT_UP));
  }

  /**
   * Get the bitmap of the up state
   *
   * @return The bitmap up
   */
  public Bitmap getUp() {
    return up;
  }

  /**
   * Sets the bitmap of up state
   *
   * @param up The bitmap of the up state
   */
  public void setUp(Bitmap up) {
    this.up = up;
  }

  /**
   * Get the bitmap of the down state
   *
   * @return The bitmap down
   */
  public Bitmap getDown() {
    return down;
  }

  /**
   * Sets the bitmap of down state
   *
   * @param down The bitmap of the up state
   */
  public void setDown(Bitmap down) {
    this.down = down;
  }
}
