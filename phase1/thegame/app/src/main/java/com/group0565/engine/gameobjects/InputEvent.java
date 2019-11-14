package com.group0565.engine.gameobjects;

import com.group0565.math.Vector;

import java.util.Objects;
import java.util.UUID;

public class InputEvent {
  private final UUID uuid = UUID.randomUUID();
  private Vector pos;
  private boolean active = true;

  public InputEvent() {
    this(new Vector());
  }

  public InputEvent(Vector pos) {
    this.pos = pos;
  }

  public void deactivate() {
    this.active = false;
  }

  public boolean isActive() {
    return this.active;
  }

  public UUID getID() {
    return uuid;
  }

  public Vector getPos() {
    return pos;
  }

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
