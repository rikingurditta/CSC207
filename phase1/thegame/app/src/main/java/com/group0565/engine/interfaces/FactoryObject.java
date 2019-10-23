package com.group0565.engine.interfaces;


import com.group0565.math.Vector;

/**
 * An Object Supported by Factories.
 * <p>
 * FactoryObjects must have a constructor taking parameters
 * GameObject parent, Vector position, boolean relative
 */
public interface FactoryObject {
    /**
     * Initialize or Re-Initialize a Object
     *
     * @param position The location of the Object
     * @param relative Whether or not position is relative to this Object's parent.
     */
    public void init(Vector position, boolean relative);

    /**
     * Used to determine if this Object is currently being used.
     *
     * @return Whether or not this Object is active.
     */
    public boolean isActive();

    /**
     * Getter for the length of time this Object has been inactive for.
     *
     * @return The length of time in ms since the last time this Bubble was active.
     */
    public long getInactivity();

    /**
     * Deactivates this piece of Object
     */
    public void deactivate();
}
