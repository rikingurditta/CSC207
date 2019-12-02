package com.group0565.tsu.game;

import org.json.JSONException;
import org.json.JSONObject;

/** Contains Information on a single object to be hit */
public class HitObject {
  // Json Constants
  private static final String JsonTime = "Time";
  private static final String JsonEndTime = "EndTime";
  private static final String JsonPosition = "Position";

  // Data fields (Stored
  private long msStart;
  private double position;
  private long msEnd;
  private long hitTime = -1 << 31;
  private long releaseTime = -1 << 31;

  /** Default constructor - DO NOT USE! REQUIRED FOR FIREBASE DB */
  public HitObject() {
    // Default constructor required for calls to DataSnapshot.getStatVal(HitObject.class)
  }

  /**
   * Creates a HitObject from JSONObject
   *
   * @param jsonObject The JSONObject to read from
   * @throws JSONException
   */
  public HitObject(JSONObject jsonObject) throws JSONException {
    this.msStart = jsonObject.getLong(JsonTime);
    this.msEnd = jsonObject.has(JsonEndTime) ? jsonObject.getLong(JsonEndTime) : msStart;
    this.position = jsonObject.getDouble(JsonPosition);
  }

  /**
   * Creates a HitObject with the parameters
   *
   * @param msStart The start time in milliseconds
   * @param position The position on the screen
   * @param msEnd The end time in milliseconds
   */
  public HitObject(long msStart, double position, long msEnd) {
    this.msStart = msStart;
    this.position = position;
    this.msEnd = msEnd;
  }

  /**
   * Creates a HitObject with the parameters. End time is defaulted to start time
   *
   * @param msStart The start time in milliseconds
   * @param position The position on the screen
   */
  public HitObject(long msStart, double position) {
    this.msStart = msStart;
    this.position = position;
    this.msEnd = msStart;
  }

  /**
   * Getter for msStart
   *
   * @return msStart
   */
  public long getMsStart() {
    return msStart;
  }

  /**
   * Getter for position
   *
   * @return position
   */
  public double getPosition() {
    return position;
  }

  /**
   * Getter for msEnd
   *
   * @return msEnd
   */
  public long getMsEnd() {
    return msEnd;
  }

  /**
   * Getter for hitTime
   *
   * @return hitTime
   */
  public long getHitTime() {
    return hitTime;
  }

  /**
   * Getter for releaseTime
   *
   * @return releaseTime
   */
  public long getReleaseTime() {
    return releaseTime;
  }

  /**
   * Setter for hitTime
   *
   * @param hitTime The new value for hitTime
   */
  public void setHitTime(long hitTime) {
    this.hitTime = hitTime;
  }

  /**
   * Setter for releaseTime
   *
   * @param releaseTime The new value for releaseTime
   */
  public void setReleaseTime(long releaseTime) {
    this.releaseTime = releaseTime;
  }
}
