package com.group0565.hitObjectsRepository;

import com.group0565.tsu.game.HitObject;

import java.util.ArrayList;
import java.util.List;

/** An object representation of Session Hits */
public class SessionHitObjects {

  /** A list of the user's session hits */
  private List<HitObject> hitObjects;
  /** The session score */
  private int score;
  /** The session grade */
  private int grade;
  /** Session difficulty */
  private double difficulty;
  /** A string representation of the time the session started */
  private String datetime;
  /** A boolean for whether cheats were used in the session */
  private boolean cheats;
  /** The max session combo */
  private int maxCombo;
  /** Amount of 300 point hits */
  private int S300;
  /** Amount of 150 point hits */
  private int S150;
  /** Amount of 50 point hits */
  private int S50;
  /** Amount of 0 point hits */
  private int S0;

  /**
   * Create a new object with the given HitObjects
   *
   * @param hitObjects The HitObjects of the session
   */
  private SessionHitObjects(List<HitObject> hitObjects) {
    this.hitObjects = hitObjects;
    this.score = 0;
    this.grade = 0;
    this.difficulty = 0;
    this.datetime = "";
    this.cheats = false;
    this.maxCombo = 0;
    this.S300 = 0;
    this.S150 = 0;
    this.S50 = 0;
    this.S0 = 0;
  }

  /** Create a new object with an empty list */
  public SessionHitObjects() {
    this(new ArrayList<>());
  }

  /**
   * Get the hitObjects for the session
   *
   * @return The hitObjects list
   */
  public List<HitObject> getHitObjects() {
    return hitObjects;
  }

  public void setHitObjects(List<HitObject> hitObjects) {
    this.hitObjects = hitObjects;
  }

  public void addToList(HitObject newHitObject) {
    this.hitObjects.add(newHitObject);
  }

  /**
   * Get the hitObjects for the session
   *
   * @return The hitObjects list
   */
  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Get the hitObjects for the session
   *
   * @return The hitObjects list
   */
  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  /**
   * Get the hitObjects for the session
   *
   * @return The hitObjects list
   */
  public double getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(double difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * Get the hitObjects for the session
   *
   * @return The hitObjects list
   */
  public String getDatetime() {
    return datetime;
  }

  /**
   * Set the datetime
   *
   * @param datetime The target value
   */
  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }

  /**
   * Get the max combo for the session
   *
   * @return The max combo length
   */
  public int getMaxCombo() {
    return maxCombo;
  }

  /**
   * Set max combo
   *
   * @param maxCombo The target value
   */
  public void setMaxCombo(int maxCombo) {
    this.maxCombo = maxCombo;
  }

  /**
   * Get the number of 300 score hits
   *
   * @return The number of 300 score hits
   */
  public int getS300() {
    return S300;
  }

  /**
   * Set number of 300 hits
   *
   * @param s300 The target value
   */
  public void setS300(int s300) {
    S300 = s300;
  }

  /**
   * Get the number of 150 score hits
   *
   * @return The number of 150 score hits
   */
  public int getS150() {
    return S150;
  }

  /**
   * Set the number of 150 hits
   *
   * @param s150 The target value
   */
  public void setS150(int s150) {
    S150 = s150;
  }

  /**
   * Get the number of 50 score hits
   *
   * @return The number of 50 score hits
   */
  public int getS50() {
    return S50;
  }

  /**
   * Set the number of 50 score hits
   *
   * @param s50 The target value
   */
  public void setS50(int s50) {
    S50 = s50;
  }

  /**
   * Get the number of 0 score hits
   *
   * @return The number of 0 score hits
   */
  public int getS0() {
    return S0;
  }

  /**
   * Set the number of 0 score hits
   *
   * @param s0 The target value
   */
  public void setS0(int s0) {
    S0 = s0;
  }

  /**
   * Get the cheats bool for this session
   *
   * @return True if cheats were used, false otherwise
   */
  public boolean hasCheats() {
    return cheats;
  }

  /**
   * Set the cheats bool
   *
   * @param cheats The target value
   */
  public void setCheats(boolean cheats) {
    this.cheats = cheats;
  }
}
