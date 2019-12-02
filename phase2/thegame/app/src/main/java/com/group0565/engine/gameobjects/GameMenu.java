package com.group0565.engine.gameobjects;

import com.group0565.engine.enums.HorizontalEdge;
import com.group0565.engine.enums.VerticalEdge;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.math.Vector;

import java.util.HashMap;

import static com.group0565.engine.enums.HorizontalEdge.HCenter;
import static com.group0565.engine.enums.VerticalEdge.VCenter;

/** An in-game menu */
public class GameMenu extends MenuObject {
  /** The reference name */
  public static final String THIS = "this";
  /** A map from child name to child object */
  private HashMap<String, MenuObject> menuComponents;

  /** Create a new GameMenu */
  public GameMenu() {
    super();
  }

  /**
   * Create a new GameMenu
   *
   * @param size The menu size
   */
  public GameMenu(Vector size) {
    super(size);
  }

  @Override
  public void preInit() {
    super.preInit();
    if (this.getSize() == null) {
      this.setSize(getEngine().getSize());
    }
  }

  /**
   * Observe for a change in preferences
   *
   * @param observable The observed object
   * @param observationEvent The observed event
   */
  protected void observePreferences(Observable observable, ObservationEvent observationEvent) {
    super.observePreferences(observable, observationEvent);
    this.updateAllPosition();
  }

  @Override
  public void postInit() {
    super.postInit();
    updateAllPosition();
  }

  /**
   * Begin build process
   *
   * @return A new MenuBuilder
   */
  public MenuBuilder build() {
    return new MenuBuilder();
  }

  /**
   * Get a child component by name
   *
   * @param name The child name
   * @return The child
   */
  protected MenuObject getComponent(String name) {
    return menuComponents.get(name);
  }

  /** Update the position of all children */
  public void updateAllPosition() {
    super.updatePosition();
    if (this.menuComponents != null)
      for (MenuObject obj : this.menuComponents.values()) obj.updatePosition();
  }

  /** A builder for a GameMenu */
  protected class MenuBuilder extends MenuObjectBuilder {
    /** A map of components to build */
    private HashMap<String, MenuObject> buildComponents;
    /** The active MenuObject */
    private MenuObject activeObject = null;

    /** Create a new builder to initiate build process */
    protected MenuBuilder() {
      buildComponents = new HashMap<>();
      buildComponents.put(THIS, GameMenu.this);
    }

    /**
     * Add a new MenuObject component as a child
     *
     * @param name The component name
     * @param object The component value
     * @return This builder
     */
    public MenuBuilder add(String name, MenuObject object) {
      if (name.equals(THIS)) throw new MenuBuilderException("Name \"" + THIS + "\" is reserved.");
      buildComponents.put(name, object);
      activeObject = object;
      activeObject.setName(name);
      return this;
    }

    /**
     * Add an alignment to the object
     *
     * @param sourceEdge The source edge
     * @param relativeTo The object relative to
     * @param targetEdge The target edge
     * @return This builder
     */
    public MenuBuilder addAlignment(
        HorizontalEdge sourceEdge, String relativeTo, HorizontalEdge targetEdge) {
      return addAlignment(sourceEdge, relativeTo, targetEdge, 0);
    }

    /**
     * Add an alignment to the object
     *
     * @param sourceEdge The source edge
     * @param relativeTo The object relative to
     * @param targetEdge The target edge
     * @return This builder
     */
    public MenuBuilder addAlignment(
        VerticalEdge sourceEdge, String relativeTo, VerticalEdge targetEdge) {
      return addAlignment(sourceEdge, relativeTo, targetEdge, 0);
    }

    /**
     * Add an alignment to the object
     *
     * @param sourceEdge The source edge
     * @param relativeTo The object relative to
     * @param targetEdge The target edge
     * @param offset The alignment offset
     * @return This builder
     */
    public MenuBuilder addAlignment(
        HorizontalEdge sourceEdge, String relativeTo, HorizontalEdge targetEdge, float offset) {
      if (activeObject == null)
        throw new MenuBuilderException(
            "Unable to set relative position when no previous object has been added.");
      MenuObject object = buildComponents.get(relativeTo);
      if (object == null)
        throw new MenuBuilderException("No MenuObject with name" + relativeTo + " is found");
      activeObject.addAlignment(new HorizontalAlignment(sourceEdge, object, targetEdge, offset));
      return this;
    }

    /**
     * Add an alignment to the object
     *
     * @param sourceEdge The source edge
     * @param relativeTo The object relative to
     * @param targetEdge The target edge
     * @param offset The alignment offset
     * @return This builder
     */
    public MenuBuilder addAlignment(
        VerticalEdge sourceEdge, String relativeTo, VerticalEdge targetEdge, float offset) {
      if (activeObject == null)
        throw new MenuBuilderException(
            "Unable to set relative position when no previous object has been added.");
      MenuObject object = buildComponents.get(relativeTo);
      if (object == null)
        throw new MenuBuilderException("No MenuObject with name" + relativeTo + " is found");
      activeObject.addAlignment(new VerticalAlignment(sourceEdge, object, targetEdge, offset));
      return this;
    }

    /**
     * Align to center of object
     *
     * @param relativeTo The reference object
     * @return This builder
     */
    public MenuBuilder addCenteredAlignment(String relativeTo) {
      addAlignment(HCenter, relativeTo, HCenter);
      addAlignment(VCenter, relativeTo, VCenter);
      return this;
    }

    /**
     * Align to center of object
     *
     * @param relativeTo The reference object
     * @param offset Alignment offset
     * @return This builder
     */
    public MenuBuilder addCenteredAlignment(String relativeTo, float offset) {
      addAlignment(HCenter, relativeTo, HCenter, offset);
      addAlignment(VCenter, relativeTo, VCenter, offset);
      return this;
    }

    /**
     * Finish building process
     *
     * @return The built object
     */
    public GameMenu close() {
      super.close();
      buildComponents.remove(THIS);
      menuComponents = buildComponents;
      for (MenuObject menuComponent : menuComponents.values()) {
        adopt(menuComponent);
      }
      return GameMenu.this;
    }

    /** An exception for errors in building process */
    protected class MenuBuilderException extends MenuObjectBuilderException {
      /**
       * Create a new exception
       *
       * @param s The exception message
       */
      public MenuBuilderException(String s) {
        super(s);
      }
    }
  }
}
