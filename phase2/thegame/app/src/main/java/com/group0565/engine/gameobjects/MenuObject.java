package com.group0565.engine.gameobjects;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.enums.HorizontalEdge;
import com.group0565.engine.enums.VerticalEdge;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.GameEngine;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Source;
import com.group0565.math.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.group0565.engine.enums.HorizontalEdge.HCenter;
import static com.group0565.engine.enums.HorizontalEdge.Left;
import static com.group0565.engine.enums.HorizontalEdge.Right;
import static com.group0565.engine.enums.VerticalEdge.Bottom;
import static com.group0565.engine.enums.VerticalEdge.Top;
import static com.group0565.engine.enums.VerticalEdge.VCenter;

/** An in-game menu object */
public class MenuObject extends GameObject implements Observable {
  /** Menu object update notification */
  public static final String MENUOBJ_UPDATE = "Menu Object Updated";
  /** The reference size */
  private static Vector referenceSize = new Vector();
  /** A debug option to show object edges */
  public boolean debug = false;
  /** The size of the object */
  private Vector size;
  /** Alignment offset */
  private Vector offset = new Vector();
  /** The horizontal alignments */
  private HorizontalAlignment[] hAligns = new HorizontalAlignment[2];
  /** The vertical alignments */
  private VerticalAlignment[] vAligns = new VerticalAlignment[2];
  /** The object's name */
  private String name;
  /** Set self to enabled */
  private Source<Boolean> selfEnable = () -> true;

  /**
   * Create a new MenuObject
   *
   * @param size The object's size
   */
  public MenuObject(Vector size) {
    this.setSize(size);
  }

  /** Create a new MenuObject */
  public MenuObject() {
    this(null);
  }

  /**
   * Get the reference size
   *
   * @return The reference size
   */
  public static Vector getReferenceSize() {
    return referenceSize;
  }

  /**
   * Set the reference size
   *
   * @param referenceSize The new reference size
   */
  public static void setReferenceSize(Vector referenceSize) {
    MenuObject.referenceSize = referenceSize;
  }

  /**
   * Begin build process
   *
   * @return A builder
   */
  public MenuObjectBuilder build() {
    return new MenuObjectBuilder();
  }

  /**
   * Observe for a change in preferences
   *
   * @param observable The observed object
   * @param observationEvent The observed event
   */
  protected void observePreferences(Observable observable, ObservationEvent observationEvent) {
    this.updatePosition();
  }

  @Override
  public void init() {
    super.init();
    if (size == null && this.getEngine() != null) this.size = (this.getEngine().getSize());
    else this.size = (size == null ? new Vector() : size);
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
    if (debug) {
      Paint p = new AndroidPaint();
      p.setARGB(255, 255, 0, 0);
      p.setStrokeWidth(10);
      canvas.drawLine(
          (new Vector(getEdgePosition(Left), getEdgePosition(Top))),
          (new Vector(getEdgePosition(Left), getEdgePosition(Bottom))),
          p);
      canvas.drawLine(
          (new Vector(getEdgePosition(Left), getEdgePosition(Top))),
          (new Vector(getEdgePosition(Right), getEdgePosition(Top))),
          p);
      canvas.drawLine(
          (new Vector(getEdgePosition(Right), getEdgePosition(Top))),
          (new Vector(getEdgePosition(Right), getEdgePosition(Bottom))),
          p);
      canvas.drawLine(
          (new Vector(getEdgePosition(Left), getEdgePosition(Bottom))),
          (new Vector(getEdgePosition(Right), getEdgePosition(Bottom))),
          p);
      canvas.drawLine(
          (new Vector(getEdgePosition(HCenter), getEdgePosition(Top))),
          (new Vector(getEdgePosition(HCenter), getEdgePosition(Bottom))),
          p);
      canvas.drawLine(
          (new Vector(getEdgePosition(Left), getEdgePosition(VCenter))),
          (new Vector(getEdgePosition(Right), getEdgePosition(VCenter))),
          p);
    }
  }

  /**
   * Observe for a change in target
   *
   * @param observable The observed object
   * @param event The observed event
   */
  private void observeUpdate(Observable observable, ObservationEvent event) {
    if (event.isEvent(MENUOBJ_UPDATE)) this.updatePosition();
  }

  /**
   * Draw the object on the screen
   *
   * @param canvas The target canvas
   * @param pos The target position to draw in
   * @param size The size of the object
   */
  public void draw(Canvas canvas, Vector pos, Vector size) {}

  @Override
  public void setEngine(GameEngine engine) {
    super.setEngine(engine);
    if (this.getSize() == null) this.setSize(engine.getSize());
  }

  /** Update the position of the object and adjust alignments */
  protected void updatePosition() {
    if (this.size == null) return;
    for (int i = 0; i < 2; i++) {
      if (hAligns[i] != null) hAligns[i].relativeTo.updatePosition();
      if (vAligns[i] != null) vAligns[i].relativeTo.updatePosition();
    }
    updateHorizontal();
    updateVertical();
    this.setRelativePosition(getRelativePosition().add(offset));
  }

  /** Adjust horizontal alignment */
  private void updateHorizontal() {
    Arrays.sort(
        hAligns,
        (o1, o2) -> {
          if (o1 == null && o2 == null) return 0;
          if (o1 == null) return 1;
          if (o2 == null) return -1;
          return o1.sourceEdge.ordinal() - o2.sourceEdge.ordinal();
        });
    if (hAligns[0] == null) return;
    // Sets The X coordinate
    {
      float x;
      switch (hAligns[0].sourceEdge) {
        case Left:
          x = hAligns[0].getTargetEdgePosition();
          break;
        case HCenter:
          x = hAligns[0].getTargetEdgePosition() - size.getX() / 2f;
          break;
        case Right:
          x = hAligns[0].getTargetEdgePosition() - size.getX();
          break;
        default:
          x = getAbsolutePosition().getX();
      }
      setAbsolutePositionX(x);
    }
    if (hAligns[1] != null) {
      if (hAligns[0].sourceEdge == HorizontalEdge.Left) {
        if (hAligns[1].sourceEdge == HorizontalEdge.HCenter) {
          this.size =
              this.size.newSetX(
                  2 * (hAligns[1].getTargetEdgePosition() - this.getAbsolutePosition().getX()));
          this.initialSizeUpdate(size);
        } else if (hAligns[1].sourceEdge == HorizontalEdge.Right) {
          this.size =
              this.size.newSetX(
                  hAligns[1].getTargetEdgePosition() - this.getAbsolutePosition().getX());
          this.initialSizeUpdate(size);
        }
      } else if (hAligns[0].sourceEdge == HorizontalEdge.HCenter) {
        this.size =
            this.size.newSetX(
                hAligns[1].getTargetEdgePosition() - hAligns[0].getTargetEdgePosition());
        this.initialSizeUpdate(size);
        setAbsolutePositionX(hAligns[0].getTargetEdgePosition() - size.getX() / 2f);
      }
    }
  }

  /**
   * Update teh size of the object
   *
   * @param size The new size
   */
  protected void initialSizeUpdate(Vector size) {}

  /** Adjust vertical alignment */
  private void updateVertical() {
    Arrays.sort(
        vAligns,
        (o1, o2) -> {
          if (o1 == null && o2 == null) return 0;
          if (o1 == null) return -1;
          if (o2 == null) return 1;
          return o1.sourceEdge.ordinal() - o2.sourceEdge.ordinal();
        });
    Arrays.sort(
        vAligns,
        (o1, o2) -> {
          if (o1 == null && o2 == null) return 0;
          if (o1 == null) return 1;
          if (o2 == null) return -1;
          return o1.sourceEdge.ordinal() - o2.sourceEdge.ordinal();
        });
    if (vAligns[0] == null) return;
    // Sets The Y coordinate
    {
      float y;
      switch (vAligns[0].sourceEdge) {
        case Top:
          y = vAligns[0].getTargetEdgePosition();
          break;
        case VCenter:
          y = vAligns[0].getTargetEdgePosition() - size.getY() / 2f;
          break;
        case Bottom:
          y = vAligns[0].getTargetEdgePosition() - size.getY();
          break;
        default:
          y = getAbsolutePosition().getY();
      }
      setAbsolutePositionY(y);
    }
    if (vAligns[1] != null) {
      if (vAligns[0].sourceEdge == VerticalEdge.Top) {
        if (vAligns[1].sourceEdge == VerticalEdge.VCenter) {
          this.size =
              this.size.newSetY(
                  2 * (vAligns[1].getTargetEdgePosition() - this.getAbsolutePosition().getY()));
          this.initialSizeUpdate(size);
        } else if (vAligns[1].sourceEdge == VerticalEdge.Bottom) {
          this.size =
              this.size.newSetY(
                  vAligns[1].getTargetEdgePosition() - this.getAbsolutePosition().getY());
          this.initialSizeUpdate(size);
        }
      } else if (vAligns[0].sourceEdge == VerticalEdge.VCenter) {
        this.size =
            this.size.newSetY(
                vAligns[1].getTargetEdgePosition() - vAligns[0].getTargetEdgePosition());
        this.initialSizeUpdate(size);
        setAbsolutePositionY(vAligns[0].getTargetEdgePosition() - size.getY() / 2f);
      }
    }
  }

  /**
   * Get the object's name
   *
   * @return This object's name
   */
  public String getName() {
    return name;
  }

  /**
   * Set the name of the object
   *
   * @param name The new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Add horizontal alignment to the object
   *
   * @param alignment The alignment to add
   */
  public void addAlignment(HorizontalAlignment alignment) {
    alignment.relativeTo.registerObserver(this::observeUpdate);
    if (hAligns[0] == null && !alignment.sameEdge(hAligns[1])) this.hAligns[0] = alignment;
    else if (hAligns[0] != null && alignment.sameEdge(hAligns[0])) this.hAligns[0] = alignment;
    else if (hAligns[1] == null) this.hAligns[1] = alignment;
    else if (alignment.sameEdge(hAligns[1])) this.hAligns[1] = alignment;
    else throw new IllegalStateException("A MenuObject can have at most 2 Horizontal Alignments");
  }

  /**
   * Add vertical alignment to the object
   *
   * @param alignment The alignment to add
   */
  public void addAlignment(VerticalAlignment alignment) {
    if (vAligns[0] == null && !alignment.sameEdge(vAligns[1])) this.vAligns[0] = alignment;
    else if (vAligns[0] != null && alignment.sameEdge(vAligns[0])) this.vAligns[0] = alignment;
    else if (vAligns[1] == null) this.vAligns[1] = alignment;
    else if (alignment.sameEdge(vAligns[1])) this.vAligns[1] = alignment;
    else throw new IllegalStateException("A MenuObject can have at most 2 Horizontal Alignments");
  }

  /**
   * Get the object's horizontal alignments
   *
   * @return The horizontal alignments
   */
  public HorizontalAlignment[] getHorizontalAlignments() {
    return this.hAligns;
  }

  /**
   * Get the object's vertical alignments
   *
   * @return The horizontal alignments
   */
  public VerticalAlignment[] getVerticalAlignments() {
    return this.vAligns;
  }

  /**
   * Check if object is horizontally aligned to target
   *
   * @param other The target
   * @return True if aligned and false otherwise
   */
  public boolean isHorizontalAlignedTo(MenuObject other) {
    if (other == null) return false;
    for (int i = 0; i < 2; i++)
      if (hAligns[i] != null && hAligns[i].relativeTo.equals(other)) return true;
    return false;
  }

  /**
   * Check if object is vertically aligned to target
   *
   * @param other The target
   * @return True if aligned and false otherwise
   */
  public boolean isVerticalAlignedTo(MenuObject other) {
    if (other == null) return false;
    for (int i = 0; i < 2; i++)
      if (vAligns[i] != null && vAligns[i].relativeTo.equals(other)) return true;
    return false;
  }

  /**
   * Get the position of an edge
   *
   * @param edge The target edge
   * @return The position of the edge on the screen
   */
  private float getEdgePosition(HorizontalEdge edge) {
    switch (edge) {
      case Left:
        return getAbsolutePosition().getX();
      case HCenter:
        return getAbsolutePosition().getX() + getSize().getX() / 2f;
      case Right:
        return getAbsolutePosition().getX() + getSize().getX();
    }
    return 0;
  }

  /**
   * Get the position of an edge
   *
   * @param edge The target edge
   * @return The position of the edge on the screen
   */
  private float getEdgePosition(VerticalEdge edge) {
    switch (edge) {
      case Top:
        return getAbsolutePosition().getY();
      case VCenter:
        return getAbsolutePosition().getY() + getSize().getY() / 2f;
      case Bottom:
        return getAbsolutePosition().getY() + getSize().getY();
    }
    return 0;
  }

  /**
   * Get the size of the object
   *
   * @return The object's size
   */
  public Vector getSize() {
    return size;
  }

  /**
   * Set the size of the object
   *
   * @param size The new size
   */
  public void setSize(Vector size) {
    if (referenceSize != null && referenceSize.getX() != 0 && referenceSize.getY() != 0)
      this.size = size.elementMultiply(getEngine().getSize().elementDivide(referenceSize));
    else this.size = size;
    this.updatePosition();
    this.notifyObservers(new ObservationEvent(MENUOBJ_UPDATE));
  }

  /**
   * Get the object's offset
   *
   * @return The offset
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

  @Override
  public MenuObject setZ(double z) {
    super.setZ(z);
    return this;
  }

  /**
   * Check if self enable
   *
   * @return True if self enable is set, false otherwise
   */
  public boolean isSelfEnable() {
    return selfEnable.getValue();
  }

  /**
   * Set selfEnable
   *
   * @param selfEnable The new selfEnable
   */
  public void setSelfEnable(boolean selfEnable) {
    this.selfEnable = () -> selfEnable;
  }

  /**
   * Set selfEnable
   *
   * @param selfEnable The new selfEnable
   */
  public void setSelfEnable(Source<Boolean> selfEnable) {
    this.selfEnable = selfEnable;
  }

  @Override
  public String toString() {
    return "MenuObject{" + "name='" + name + '\'' + '}';
  }

  /** A builder for a MenuObject */
  protected class MenuObjectBuilder {
    /** MenuObject's offset */
    private Vector offset = null;
    /** MenuObject status */
    private boolean enable = true;
    /** MenuObject enableSet */
    private boolean enableSet = false;
    /** MenuObject self enable */
    private Source<Boolean> selfEnable = null;
    /** MenuObject z-index */
    private float z = 0;
    /** MenuObject name */
    private String name = null;
    /** Observers to MenuObject */
    private List<Observer> observerList = new ArrayList<>();
    /** Event observers to MenuObject */
    private List<EventObserver> eventObserverList = new ArrayList<>();

    /** Create a new MenuObjectBuilder */
    protected MenuObjectBuilder() {}

    /**
     * Add offset to target
     *
     * @param offset The offset to add
     * @return This builder
     */
    public MenuObjectBuilder addOffset(Vector offset) {
      this.offset = offset;
      return this;
    }

    public MenuObjectBuilder addOffset(float x, float y) {
      this.offset = new Vector(x, y);
      return this;
    }

    public MenuObjectBuilder setEnable(boolean enable) {
      this.enable = enable;
      this.enableSet = true;
      return this;
    }

    public MenuObjectBuilder setSelfEnable(Source<Boolean> enable) {
      this.selfEnable = enable;
      return this;
    }

    public MenuObjectBuilder registerObserver(Observer observer) {
      this.observerList.add(observer);
      return this;
    }

    public MenuObjectBuilder registerObserver(EventObserver observer) {
      this.eventObserverList.add(observer);
      return this;
    }

    public void setName(String name) {
      this.name = name;
    }

    public MenuObjectBuilder setZ(float z) {
      this.z = z;
      return this;
    }

    public MenuObject close() {
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

    protected class MenuObjectBuilderException extends IllegalStateException {
      public MenuObjectBuilderException(String s) {
        super(s);
      }
    }
  }

  protected class HorizontalAlignment {
    public MenuObject relativeTo;
    public HorizontalEdge sourceEdge;
    public HorizontalEdge targetEdge;
    public float offset;

    public HorizontalAlignment(
        HorizontalEdge sourceEdge, MenuObject relativeTo, HorizontalEdge targetEdge, float offset) {
      this.relativeTo = relativeTo;
      this.sourceEdge = sourceEdge;
      this.targetEdge = targetEdge;
      this.offset = offset;
    }

    public boolean sameEdge(HorizontalAlignment other) {
      if (other != null && other.sourceEdge == sourceEdge) return true;
      return false;
    }

    public float getTargetEdgePosition() {
      return this.relativeTo.getEdgePosition(this.targetEdge) + offset;
    }
  }

  protected class VerticalAlignment {
    public MenuObject relativeTo;
    public VerticalEdge sourceEdge;
    public VerticalEdge targetEdge;
    public float offset;

    public VerticalAlignment(
        VerticalEdge sourceEdge, MenuObject relativeTo, VerticalEdge targetEdge, float offset) {
      this.sourceEdge = sourceEdge;
      this.relativeTo = relativeTo;
      this.targetEdge = targetEdge;
      this.offset = offset;
    }

    public boolean sameEdge(VerticalAlignment other) {
      if (other != null && other.sourceEdge == sourceEdge) return true;
      return false;
    }

    public float getTargetEdgePosition() {
      return this.relativeTo.getEdgePosition(this.targetEdge) + offset;
    }
  }
}
