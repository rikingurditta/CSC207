package com.group0565.engine.gameobjects;

import com.group0565.math.Vector;

import java.util.Objects;
import java.util.UUID;

/** A class representation of an InputEvent */
public class InputEvent {
  /** A random UID for this object */
  private final UUID uuid = UUID.randomUUID();
  /** The event's position */
  private Vector pos;
  /** Is event active */
  private boolean active = true;

  /** Create a new input event */
  public InputEvent() {
    this(new Vector());
  }

  /**
   * Create a new input event at given position
   *
   * @param pos The event position vector
   */
  public InputEvent(Vector pos) {
    this.pos = pos;
  }

  /** Deactivate the event by setting active to false */
  public void deactivate() {
    this.active = false;
  }

  /**
   * Is the event currently active
   *
   * @return True if event is active and false otherwise
   */
  public boolean isActive() {
    return this.active;
  }

  /**
   * Get this object unique id
   *
   * @return The uuid of the object
   */
  public UUID getID() {
    return uuid;
  }

  /**
   * Get this object's current position
   *
   * @return The position vector
   */
  public Vector getPos() {
    return pos;
  }

  /**
   * Set this event's position
   *
   * @param pos The new position
   */
  public void setPos(Vector pos) {
    this.pos = pos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InputEvent that = (InputEvent) o;
    return Objects.equals(uuid, that.uuid);
  }

  @Override
  public int hashCode() {
    return uuid.hashCode();
  }
}
