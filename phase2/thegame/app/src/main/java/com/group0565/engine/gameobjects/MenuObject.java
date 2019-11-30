package com.group0565.engine.gameobjects;

import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.GameEngine;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;

import java.util.ArrayList;
import java.util.List;

/** A class representation of a MenuObject for all in-game menu elements */
public class MenuObject extends GameObject implements Observable, Observer {
  /** The reference size */
  private static Vector referenceSize = new Vector();
  /** The actual object size */
  private Vector size;
  /** A vector for the buffer of the object */
  private Vector buffer = new Vector();
  /** A vector for the offsets of the object */
  private Vector offset = new Vector();
  /** A reference to the parent object's size */
  private Vector parentSize = null;
  /** A reference to the parent object's buffer */
  private Vector parentBuffer = null;
  /** The object's alignment */
  private Alignment alignment = null;
  /** The object's name */
  private String name;
  /** Set self to enabled */
  private Source<Boolean> selfEnable = () -> true;

  /**
   * Create a new MenuObject
   *
   * @param size The given size
   */
  public MenuObject(Vector size) {
    if (size == null && this.getEngine() != null) this.setSize(this.getEngine().getSize());
    else this.setSize(size);
  }

  /**
   * Begin the MenuObject building process
   *
   * @return A builder for menu objects
   */
  public MenuObjectBuilder build() {
    return new MenuObjectBuilder();
  }

  @Override
  public void update(long ms) {
    super.update(ms);
  }

  @Override
  public void renderAll(Canvas canvas) {
    if (!isEnable()) return;
    if (isSelfEnable()) this.draw(canvas);
    for (GameObject child : this.getChildren().values()) child.renderAll(canvas);
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    if (getSize() != null) this.draw(canvas, this.getAbsolutePosition(), this.getSize());
  }

  /**
   * Draw the current object
   *
   * @param canvas The target canvas to draw on
   * @param pos The position to draw in
   * @param size The size of the object
   */
  public void draw(Canvas canvas, Vector pos, Vector size) {}

  @Override
  public void observe(Observable observable) {
    if (alignment != null && observable == alignment.relativeTo) {
      parentSize = alignment.relativeTo.getSize();
      parentBuffer = alignment.relativeTo.getBuffer();
      updatePosition();
    }
  }

  @Override
  public void setEngine(GameEngine engine) {
    super.setEngine(engine);
    if (this.getSize() == null) this.setSize(engine.getSize());
  }

  /** Update the position of the current object */
  protected void updatePosition() {
    if (parentSize == null || parentBuffer == null) return;
    float pw = parentSize.getX();
    float ph = parentSize.getY();
    float pbw = parentBuffer.getX();
    float pbh = parentBuffer.getY();
    float w = getSize().getX();
    float h = getSize().getY();
    float x;
    switch (alignment.hAlign) {
      case Center:
        x = (pw - w) / 2;
        break;
      case LeftOf:
        x = -(w + pbw);
        break;
      case RightOf:
        x = (pw + pbw);
        break;
      case LeftAlign:
        x = 0;
        break;
      case RightAlign:
        x = pw - w;
        break;
      default:
        x = 0;
        break;
    }
    float y;
    switch (alignment.vAlign) {
      case Center:
        y = (ph - h) / 2;
        break;
      case TopOf:
        y = -(h + pbh);
        break;
      case BottomOf:
        y = (ph + pbh);
        break;
      case TopAlign:
        y = 0;
        break;
      case BottomAlign:
        y = ph - h;
        break;
      default:
        y = 0;
        break;
    }
    this.setRelativePosition(new Vector(x, y).add(this.offset));
  }

  /**
   * Get the name of the current object
   *
   * @return The object's name
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name
   *
   * @param name Target name
   */
  public void setName(String name) {
    this.name = name;
  }

  /** A builder for MenuObjects */
  protected class MenuObjectBuilder {
    /** Object buffer */
    private Vector buffer = null;
    /** Object offset */
    private Vector offset = null;
    /** Is object enabled */
    private boolean enable = true;
    /** Was enable set */
    private boolean enableSet = false;
    /** Enable self */
    private Source<Boolean> selfEnable = null;
    /** Object's z value */
    private float z = 0;
    /** Object's name */
    private String name = null;
    /** A list of all observers */
    private List<Observer> observerList = new ArrayList<>();
    /** A list of all event observers */
    private List<EventObserver> eventObserverList = new ArrayList<>();

    /** Create a new builder object */
    protected MenuObjectBuilder() {}

    /**
     * Add a buffer to the object
     *
     * @param buffer The vector of the buffer
     * @return This builder
     */
    public MenuObjectBuilder addBuffer(Vector buffer) {
      this.buffer = buffer;
      return this;
    }

    /**
     * Add a buffer to the object
     *
     * @param x The x of the buffer vector
     * @param y The y of the buffer vector
     * @return This builder
     */
    public MenuObjectBuilder addBuffer(float x, float y) {
      this.buffer = new Vector(x, y);
      return this;
    }

    /**
     * Add an offset to the object
     *
     * @param offset The vector of the offset
     * @return This builder
     */
    public MenuObjectBuilder addOffset(Vector offset) {
      this.offset = offset;
      return this;
    }

    /**
     * Add an offset to the object
     *
     * @param x The x of the offset vector
     * @param y The y of the offset vector
     * @return This builder
     */
    public MenuObjectBuilder addOffset(float x, float y) {
      this.offset = new Vector(x, y);
      return this;
    }

    /**
     * Sets enabled
     *
     * @param enable Is object enabled
     * @return This builder
     */
    public MenuObjectBuilder setEnable(boolean enable) {
      this.enable = enable;
      this.enableSet = true;
      return this;
    }

    /**
     * Sets self enabled to the given function
     *
     * @param enable The enabling function
     * @return This builder
     */
    public MenuObjectBuilder setSelfEnable(Source<Boolean> enable) {
      this.selfEnable = enable;
      return this;
    }

    /**
     * Register an observer to this object
     *
     * @param observer The observer to add
     * @return This builder
     */
    public MenuObjectBuilder registerObserver(Observer observer) {
      this.observerList.add(observer);
      return this;
    }

    /**
     * Register an event observer to this object
     *
     * @param observer The observer to add
     * @return This builder
     */
    public MenuObjectBuilder registerObserver(EventObserver observer) {
      this.eventObserverList.add(observer);
      return this;
    }

    /**
     * Sets the name of this object
     *
     * @param name The new name
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * Sets the z for the object
     *
     * @param z The new z value
     * @return This builder
     */
    public MenuObjectBuilder setZ(float z) {
      this.z = z;
      return this;
    }

    /**
     * Finish the building process
     *
     * @return The complete MenuObject
     */
    public MenuObject close() {
      if (buffer != null) MenuObject.this.setBuffer(this.buffer);
      if (offset != null) MenuObject.this.setOffset(this.offset);
      if (enableSet) MenuObject.this.setEnable(this.enable);
      if (selfEnable != null) MenuObject.this.setSelfEnable(this.selfEnable);
      MenuObject.this.setZ(this.z);
      if (name != null) MenuObject.this.setName(name);
      for (Observer observer : this.observerList) MenuObject.this.registerObserver(observer);
      for (EventObserver eventObserver : this.eventObserverList)
        MenuObject.this.registerObserver(eventObserver);
      notifyObservers();
      return MenuObject.this;
    }

    /** An exception for building process errors */
    protected class MenuObjectBuilderException extends IllegalStateException {
      /**
       * Create a new MenuObjectBuilderException
       *
       * @param s the error message
       */
      public MenuObjectBuilderException(String s) {
        super(s);
      }
    }
  }

  /**
   * Sets the object's alignment
   *
   * @param alignment The target alignment
   */
  public void setAlignment(Alignment alignment) {
    if (this.alignment != null && this.alignment.relativeTo != null)
      this.alignment.relativeTo.unregisterObserver(this);
    this.alignment = alignment;
    this.alignment.relativeTo.registerObserver(this);
    this.alignment.relativeTo.adopt(this);
  }

  /**
   * Get the buffer vector
   *
   * @return The buffer vector
   */
  public Vector getBuffer() {
    return buffer;
  }

  /**
   * Sets the buffer
   *
   * @param buffer The new buffer vector
   */
  public void setBuffer(Vector buffer) {
    if (referenceSize != null && referenceSize.getX() != 0 && referenceSize.getY() != 0)
      this.buffer = buffer.elementMultiply(getEngine().getSize().elementDivide(referenceSize));
    else this.buffer = buffer;
    this.notifyObservers();
  }

  /**
   * Get the current alignment
   *
   * @return The alignment of this object
   */
  public Alignment getAlignment() {
    return alignment;
  }

  /**
   * Get the object's size
   *
   * @return The size
   */
  public Vector getSize() {
    return size;
  }

  /**
   * Sets the size of the object
   *
   * @param size The new size
   */
  public void setSize(Vector size) {
    if (referenceSize != null && referenceSize.getX() != 0 && referenceSize.getY() != 0)
      this.size = size.elementMultiply(getEngine().getSize().elementDivide(referenceSize));
    else this.size = size;
    this.updatePosition();
    this.notifyObservers();
  }

  /**
   * Get the object's offset
   *
   * @return Offset of the object
   */
  public Vector getOffset() {
    return offset;
  }

  /**
   * Set the object's offset
   *
   * @param offset The new offset
   */
  public void setOffset(Vector offset) {
    if (referenceSize != null && referenceSize.getX() != 0 && referenceSize.getY() != 0)
      this.offset = offset.elementMultiply(getEngine().getSize().elementDivide(referenceSize));
    else this.offset = offset;
    this.updatePosition();
    this.notifyObservers();
  }

  /** Notify all observers and refresh all children */
  public void refreshAll() {
    this.notifyObservers();
    for (GameObject object : this.getChildren().values())
      if (object instanceof MenuObject) ((MenuObject) object).refreshAll();
  }

  /**
   * Get the referenceSize
   *
   * @return The referenceSize
   */
  public static Vector getReferenceSize() {
    return referenceSize;
  }

  /**
   * Set the referenceSize
   *
   * @param referenceSize The new referenceSize
   */
  public static void setReferenceSize(Vector referenceSize) {
    MenuObject.referenceSize = referenceSize;
  }

  /**
   * Get selfEnable
   *
   * @return True if selfEnabled, false otherwise
   */
  public boolean isSelfEnable() {
    return selfEnable.getValue();
  }

  /**
   * Sets selfEnable
   *
   * @param selfEnable The target enabled bool
   */
  public void setSelfEnable(boolean selfEnable) {
    this.selfEnable = () -> selfEnable;
  }

  /**
   * Sets selfEnable
   *
   * @param selfEnable The target selfEnabled method
   */
  public void setSelfEnable(Source<Boolean> selfEnable) {
    this.selfEnable = selfEnable;
  }

  /** A class representation for MenuObject Alignment */
  protected class Alignment {
    /** The related MenuObject */
    public MenuObject relativeTo;
    /** Horizontal alignment */
    public HorizontalAlignment hAlign;
    /** Vertical alignment */
    public VerticalAlignment vAlign;

    /**
     * Create a new Alignment
     *
     * @param relativeTo The relative object
     * @param hAlign The horizontal alignment
     * @param vAlign The vertical alignment
     */
    public Alignment(MenuObject relativeTo, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
      this.relativeTo = relativeTo;
      this.hAlign = hAlign;
      this.vAlign = vAlign;
    }
  }
}
