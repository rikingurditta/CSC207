package com.group0565.engine.gameobjects;

import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Drawable;
import com.group0565.engine.interfaces.GameEngine;
import com.group0565.engine.interfaces.LifecycleListener;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.WeakHashMap;

/** The parent entity of all game objects */
public class GameObject implements LifecycleListener, Drawable {
  /**
   * A lookup take for UUID to GameObjects. Only weak references are kept to both UUID and
   * GameObject to allow the Garbage Collector to clean up unused GameObjects without needing to
   * explicitly remove them from this table. As a side effect, having a strong reference to the UUID
   * does not guarantee the GameObject it corresponds to is not garbage collected.
   */
  private static final WeakHashMap<UUID, WeakReference<GameObject>> reference = new WeakHashMap<>();

  /** A common Random Number Generator for all game objects. */
  private static final Random RNG = new Random();

  /**
   * The UUID for this GameObject. Serves as a unique identifier.
   *
   * <p>Do NOT use a uuid as the only reference to the GameObject, as the object is only weakly
   * referenced by the uuid, the GameObject may be garbage collected even with strong references to
   * it's uuid.
   */
  private final UUID uuid;
  /** Set of captured events */
  private final HashSet<InputEvent> capturedEvents = new HashSet<>();
  /** Set of captured events */
  private final HashSet<InputEvent> capturingEvents = new HashSet<>();
  /** Vector to repersent the position of this object */
  private Vector relativePosition = new Vector();
  /**
   * Variable to cache the parent's absolute position to avoid recomputing the entire relative tree
   * each time absolute position is queried. This is updated when the absolute position of this
   * object is accessed (get or set) and invalidateCache is set true.
   */
  private Vector parentAbsolutePosition = new Vector();
  /**
   * Whether or not the cached data is invalid. If true, the parentAbsolutePosition variable will be
   * updated next time the absolute position of this object is queried.
   */
  private boolean invalidateCache = true;
  /**
   * Double to determine the relative rendering order of this object with its siblings. Parent is
   * always rendered before its children.
   */
  private double z;
  /**
   * A collection of child objects of this game object.
   *
   * <p>The parent is responsible for updating and rendering it's children.
   *
   * <p>A child's position is relative to it's parent's location.
   *
   * <p>The TreeMap with the ZComparator sorts the children by their z value in ascending order to
   * ensure rendering order.
   *
   * <p>A TreeMap is used as GameObjects are mutable, hence the immutable and hashable UUID of the
   * object is used as a key to the TreeMap.
   */
  private TreeMap<UUID, GameObject> children = new TreeMap<>(new ZComparator());
  /**
   * A reference back to the parent of this object. This variable is null if this Object is a top
   * level GameObject.
   */
  private GameObject parent = null;

  /** Whether or not this GameObject is enabled */
  private boolean enable = true;

  /** Whether or not this GameObject has been initialized */
  private boolean initialized = false;

  /** The GameEngine of this GameObject */
  private GameEngine engine;
  /** The global preferences */
  private GlobalPreferences globalPreferences;

  public GameObject() {
    this(0);
  }

  public GameObject(double z) {
    this(new Vector(), z);
  }

  /**
   * Creates a new GameObject with z-level defaulting to 0.
   *
   * <p>For more information on other parameters see the javadoc of the constructer with signature
   * (GameObject parent, Vector position, boolean relative, Vector charsize, double z).
   *
   * @param position The position (relative or absolute) of this object.
   */
  public GameObject(Vector position) {
    this(position, 0);
  }

  /**
   * Creates a new GameObject located at position, either relative to its parent if relative is
   * true, otherwise as an absolute position, character size charsize, and z-level z.
   *
   * <p>If parent is not null, this object is automatically added as a child to parent.
   *
   * <p>The z level determines the the rendering order of this object relative to its siblings.
   *
   * @param position The position (relative or absolute) of this object.
   * @param z The z-level of the object.
   */
  public GameObject(Vector position, double z) {
    this.uuid = UUID.randomUUID();
    this.z = z;
    this.setAbsolutePosition(position);
    this.setGlobalPreferences(new GlobalPreferences());
    GameObject.reference.put(this.uuid, new WeakReference<>(this));
  }

  /**
   * Dereferences this uuid to the GameObject with this uuid. Since uuids only weakly reference the
   * GameObjects, this method may return null if the GameObject has been garbage collected.
   *
   * @param uuid the uuid to look up.
   * @return The GameObject associated with this uuid, or null if none is found either due to no
   *     GameObject has this uuid or the GameObject has been garbage collected already.
   */
  public static GameObject getObj(UUID uuid) {
    WeakReference<GameObject> obj = GameObject.reference.get(uuid);
    return (obj == null ? null : obj.get());
  }

  /**
   * Return the Random Number Generator (RNG) of GameObjects
   *
   * @return The RNG of GameObjects.
   */
  public static Random getRNG() {
    return RNG;
  }

  /**
   * Update the object and its children by one time unit
   *
   * @param ms Milliseconds since last update
   */
  public void updateAll(long ms) {
    if (!enable) return;
    synchronized (capturingEvents) {
      for (InputEvent event : capturingEvents) {
        this.capturedEvents.add(event);
        this.onEventCapture(event);
      }
      this.capturingEvents.clear();
    }
    Iterator<InputEvent> eventIterator = this.capturedEvents.iterator();
    while (eventIterator.hasNext()) {
      InputEvent e = eventIterator.next();
      if (!e.isActive()) {
        this.onEventDisable(e);
        eventIterator.remove();
      }
    }
    this.update(ms);
    for (GameObject child : this.getChildren().values()) child.updateAll(ms);
  }

  /**
   * Update the object by ms milliseconds
   *
   * @param ms Milliseconds Since Last Update
   */
  public void update(long ms) {}

  /**
   * Draw this object and Renders its children onto canvas
   *
   * @param canvas The Canvas on which to draw and render
   */
  public void renderAll(Canvas canvas) {
    if (!enable) return;
    this.draw(canvas);
    for (GameObject child : this.getChildren().values()) child.renderAll(canvas);
  }

  /**
   * Draws ONLY this object to canvas. Its children are NOT drawn.
   *
   * @param canvas The Canvas on which to draw
   */
  @Override
  public void draw(Canvas canvas) {}

  /**
   * Adopts obj as a child of this object. Removes obj from it's previous parent if it exists.
   *
   * @param obj The GameObject to adopt.
   */
  public void adopt(GameObject obj) {
    Vector absolutePosition = obj.getAbsolutePosition();
    if (obj.parent != null) obj.parent.getChildren().remove(obj.uuid);
    obj.parent = this;
    obj.engine = this.engine;
    if (!this.getChildren().containsKey(obj.uuid)) this.getChildren().put(obj.uuid, obj);
    obj.invalidateCache();
    obj.setGlobalPreferences(this.globalPreferences);
    obj.setAbsolutePosition(absolutePosition);
  }

  /** Called before init */
  public void preInit() {}

  /** Initializer for this object. Call super.init() to initialize children. */
  public void init() {}

  /** Called after init */
  public void postInit() {
    for (GameObject child : this.getChildren().values()) {
      child.fullInit();
    }
  }

  /** Initializer for this object. Call super.init() to initialize children. */
  public void fullInit() {
    preInit();
    init();
    postInit();
  }

  /** Called on stop for this object. Call super.stop() to stop children. */
  public void stop() {
    for (GameObject child : this.getChildren().values()) child.stop();
  }

  /** Pauses this object. Call super.pause() to pause children. */
  public void pause() {
    for (GameObject child : this.getChildren().values()) child.pause();
  }

  /** Wakes this object. Call super.init() to resume children. */
  public void resume() {
    for (GameObject child : this.getChildren().values()) child.resume();
  }

  /**
   * Callback when an event has been captured. This is ran on the same thread as update. Use this to
   * process events when the are captured, instead of using captureEvent
   *
   * @param event The event that has been captured.
   */
  protected void onEventCapture(InputEvent event) {}

  /**
   * Callback when an event has ended. For example when a touched point is released.
   *
   * @param event The event that has ended.
   */
  protected void onEventDisable(InputEvent event) {}

  /**
   * Capture and store the event for further reference.
   *
   * @param event The event to be captured.
   */
  protected void captureEvent(InputEvent event) {
    synchronized (capturingEvents) {
      this.capturingEvents.add(event);
    }
  }

  /**
   * Processes the InputEvent. Return true if this object or it's children has handled the event
   * and it should not be passed to any other object any more.
   *
   * @param event The input to be processed
   * @return Whether or not the event has been captured.
   */
  public boolean processInput(InputEvent event) {
    if (!enable) return false;
    for (GameObject child : this.children.descendingMap().values())
      if (child.processInput(event)) return true;
    return false;
  }

  /**
   * Gets the absolute position of this object.
   *
   * @return A new vector representing the absolute position of this object.
   */
  public Vector getAbsolutePosition() {
    validateCache();
    return this.parentAbsolutePosition.add(this.relativePosition);
  }

  /**
   * Sets the absolute position of this vector. This is stored as a relative position to its parent.
   *
   * @param absolutePosition The absolute position of this vector.
   * @return This Object to allow chaining
   */
  public GameObject setAbsolutePosition(Vector absolutePosition) {
    validateCache();
    setRelativePosition(absolutePosition.subtract(parentAbsolutePosition));
    return this;
  }

  /**
   * Gets the relative position of this object.
   *
   * @return A new vector representing the relative position of this object.
   */
  public Vector getRelativePosition() {
    return this.relativePosition;
  }

  /**
   * Sets the relative position of this vector to its parent. If parent is null, this becomes the
   * absolute position.
   *
   * @param relativePosition The relative position of this vector.
   * @return This Object to allow chaining
   */
  public GameObject setRelativePosition(Vector relativePosition) {
    this.relativePosition = relativePosition;
    invalidateCache();
    return this;
  }

  /**
   * Sets the position either relative or absolute
   *
   * @param position The position
   * @param relative Whether position is relative or absolute
   * @return This Object to allow chaining
   */
  public GameObject setPosition(Vector position, boolean relative) {
    if (relative) this.setRelativePosition(position);
    else this.setAbsolutePosition(position);
    invalidateCache();
    return this;
  }

  /**
   * Helper method to check if the parentAbsolutePosition variable needs updating, and update it if
   * necessary.
   */
  private void validateCache() {
    if (!invalidateCache) return;
    if (this.parent != null) this.parentAbsolutePosition = this.parent.getAbsolutePosition();
    else this.parentAbsolutePosition = new Vector();
  }

  /** Invalidates the cache of this object. */
  public void invalidateCache() {
    this.invalidateCache(new HashSet<>());
  }

  /**
   * Invalidates the cache of this object.
   *
   * @param visited GameObjects that have been visited.
   */
  protected void invalidateCache(Set<UUID> visited) {
    this.invalidateCache = true;
    for (GameObject child : this.getChildren().values())
      if (!visited.contains(child.uuid)) {
        visited.add(child.uuid);
        child.invalidateCache(visited);
      }
  }

  /**
   * Converts the vector in absolute position into relative position
   *
   * @param absolutePosition The absolute position
   * @return The vector in relative position.
   */
  public Vector toRelativePosition(Vector absolutePosition) {
    return absolutePosition.subtract(parentAbsolutePosition);
  }

  /**
   * Getter for this GameObject's Z value
   *
   * @return The current z value of this GameObject
   */
  public double getZ() {
    return z;
  }

  /**
   * Setter for this GameObject's Z value
   *
   * @param z The new Z value
   */
  public void setZ(double z) {
    this.z = z;
  }

  /**
   * Getter for the TreeMap of this GameObject's children
   *
   * @return children of this GameObject
   */
  public Map<UUID, GameObject> getChildren() {
    return children;
  }

  /**
   * Getter for the parent of this GameObject
   *
   * @return A new GameObject
   */
  public GameObject getParent() {
    return parent;
  }

  /**
   * Changes the parent of this object. If parent is null, this becomes a top level object.
   *
   * @param parent The new parent of this object. Can be null.
   */
  public GameObject setParent(GameObject parent) {
    if (parent != null) parent.adopt(this);
    this.invalidateCache();
    return this;
  }

  /**
   * Getter for the GameEngine of this object
   *
   * @return The GameEngine
   */
  public GameEngine getEngine() {
    return engine;
  }

  /**
   * Setter for the GameEngine of this GameObject and its children.
   *
   * @param engine The new GameEngine
   */
  public void setEngine(GameEngine engine) {
    this.engine = engine;
    for (GameObject child : this.children.values()) child.setEngine(engine);
  }

  /**
   * Getter for uuid
   *
   * @return The uuid of this GameObject
   */
  public UUID getUUID() {
    return uuid;
  }

  /**
   * Returns the set of captured events.
   *
   * @return The stored set of captured events.
   */
  protected HashSet<InputEvent> getCapturedEvents() {
    return capturedEvents;
  }

  /**
   * Return whether or not his object is enabled.
   *
   * @return Whether or not his object is enabled.
   */
  public boolean isEnable() {
    return enable;
  }

  /**
   * Sets whether or not this object is enabled.
   *
   * @param enable whther or not this object is enabled
   */
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  /**
   * Getter for the GlobalPreferences
   *
   * @return The global preferences
   */
  public GlobalPreferences getGlobalPreferences() {
    return globalPreferences;
  }

  /**
   * Setter for global preferences
   *
   * @param globalPreferences The new Global Preferences
   * @return This object to allow chaining
   */
  public GameObject setGlobalPreferences(GlobalPreferences globalPreferences) {
    this.globalPreferences = globalPreferences;
    for (GameObject child : this.children.values()) {
      child.setGlobalPreferences(globalPreferences);
    }
    return this;
  }

  /**
   * Getter for the initialization status.
   *
   * @return True if init has been called at least once
   */
  public boolean isInitialized() {
    return initialized;
  }

  /**
   * Setter for the initialization status.
   *
   * @return This object to allow chaining
   */
  public GameObject setInitialized(boolean initialized) {
    this.initialized = initialized;
    return this;
  }

  /** Listener for Engine size change */
  public void sizeChanged(Observable observable, ObservationEvent event) {
    for (GameObject child : this.children.values()) child.sizeChanged(observable, event);
  }

  /** RuntimeException to be raised when the parent of a GameObject is Invalid. */
  protected class IllegalParentException extends RuntimeException {
    /**
     * Constructs a new IllegalParentException exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link
     * #initCause}.
     */
    public IllegalParentException() {
      super();
    }

    /**
     * Constructs a new IllegalParentException exception with the specified detail message. The
     * cause is not initialized, and may subsequently be initialized by a call to {@link
     * #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *     {@link #getMessage()} method.
     */
    public IllegalParentException(String message) {
      super(message);
    }
  }
}
