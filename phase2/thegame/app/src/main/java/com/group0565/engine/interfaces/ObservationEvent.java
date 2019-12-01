package com.group0565.engine.interfaces;

/**
 * A class representing an Observation Event carrying payload
 *
 * @param <T> The payload type
 */
public class ObservationEvent<T> {
  /** A string message carried in the event */
  private String msg;
  /** An object carried by the event */
  private T payload = null;

  /**
   * Create a new ObservationEvent
   *
   * @param msg A string message
   */
  public ObservationEvent(String msg) {
    this.msg = msg;
  }

  /**
   * Create a new ObservationEvent
   *
   * @param msg A string message
   * @param payload The carried data
   */
  public ObservationEvent(String msg, T payload) {
    this.msg = msg;
    this.payload = payload;
  }

  /**
   * Get the message carried by the event
   *
   * @return The message
   */
  public String getMsg() {
    return msg;
  }
    /**
     * Get the payload object carried by the event
     *
     * @return The payload
     */
    public T getPayload() {
        return payload;
    }

    public boolean isEvent(String event) {
        return getMsg().equals(event);
    }
}
