package com.group0565.engine.gameobjects;

import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

import java.util.HashMap;

/** GameMenu representation of MenuObject */
public class GameMenu extends MenuObject implements Observer {
  /** The name of this class */
  public static final String THIS = "this";

  /** A map from component name to object */
  private HashMap<String, MenuObject> menuComponents;

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

  @Override
  public void observe(Observable observable) {
    super.observe(observable);
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

  /** A Builder for GameMenus */
  protected class MenuBuilder extends MenuObjectBuilder {
    /** A map of component names to objects */
    private HashMap<String, MenuObject> buildComponents;
    /** The current active object */
    private MenuObject activeObject = null;
    /** Default component alignment */
    private Alignment defaultAlign;

    /** Create a new MenuBuilder to begin building */
    protected MenuBuilder() {
      buildComponents = new HashMap<>();
      buildComponents.put(THIS, GameMenu.this);
      defaultAlign =
          new Alignment(GameMenu.this, HorizontalAlignment.Center, VerticalAlignment.Center);
    }

    /**
     * Add a child to the object being built
     *
     * @param name The new child name
     * @param object The child object
     * @return This builder
     */
    public MenuBuilder add(String name, MenuObject object) {
      if (name.equals(THIS)) throw new MenuBuilderException("Name \"" + THIS + "\" is reserved.");
      buildComponents.put(name, object);
      activeObject = object;
      activeObject.setName(name);
      activeObject.registerObserver(GameMenu.this);
      activeObject.setAlignment(defaultAlign);
      return this;
    }

    /**
     * Set the relative position of the built object
     *
     * @param relativeTo The object to reference location to
     * @param hAlign The horizontal alignment
     * @param vAlign The vertical alignment
     * @return This builder
     */
    public MenuBuilder setRelativePosition(
        String relativeTo, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
      if (activeObject == null)
        throw new MenuBuilderException(
            "Unable to set relative position when no previous object has been added.");
      MenuObject object = buildComponents.get(relativeTo);
      if (object == null)
        throw new MenuBuilderException("No MenuObject with name" + relativeTo + " is found");
      Alignment alignment = new Alignment(object, hAlign, vAlign);
      activeObject.setAlignment(alignment);
      return this;
    }

    /**
     * Set the relative position of the built object horizontally
     *
     * @param relativeTo The object to reference location to
     * @param hAlign The horizontal alignment
     * @return This builder
     */
    public MenuBuilder setRelativePosition(String relativeTo, HorizontalAlignment hAlign) {
      return setRelativePosition(relativeTo, hAlign, VerticalAlignment.Center);
    }

    /**
     * Set the relative position of the built object vertically
     *
     * @param relativeTo The object to reference location to
     * @param vAlign The vertical alignment
     * @return This builder
     */
    public MenuBuilder setRelativePosition(String relativeTo, VerticalAlignment vAlign) {
      return setRelativePosition(relativeTo, HorizontalAlignment.Center, vAlign);
    }

    /**
     * Set the relative position of the built object to center
     *
     * @param relativeTo The object to reference location to
     * @return This builder
     */
    public MenuBuilder setCenteredRelativePosition(String relativeTo) {
      return setRelativePosition(relativeTo, HorizontalAlignment.Center, VerticalAlignment.Center);
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
      refreshAll();
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
