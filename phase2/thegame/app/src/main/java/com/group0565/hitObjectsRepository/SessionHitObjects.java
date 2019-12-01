package com.group0565.hitObjectsRepository;

import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.tsu.enums.Scores;
import com.group0565.tsu.game.ArchiveInputEvent;
import com.group0565.tsu.game.HitObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** An object representation of Session Hits */
public class SessionHitObjects {
  /**
   * Lambda Map to set scores easier
   */
  private HashMap<Scores, Setter<Integer>> scoresSetters = new HashMap<>();
    /** A list of the user's session hits */
    private List<HitObject> hitObjects;
  private Set<ArchiveInputEvent> archive;
  private String beatmapName;
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
    this.beatmapName = "";
    this.datetime = "";
    this.cheats = false;
    this.maxCombo = 0;
    this.S300 = 0;
    this.S150 = 0;
    this.S50 = 0;
    this.S0 = 0;
    this.archive = new HashSet<>();
    scoresSetters.put(Scores.S300, this::setS300);
    scoresSetters.put(Scores.S150, this::setS150);
    scoresSetters.put(Scores.S50, this::setS50);
    scoresSetters.put(Scores.S0, this::setS0);
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

  /**
   * Setter for hitObjects
   *
   * @param hitObjects The new value for hitObjects
   */
  public void setHitObjects(List<HitObject> hitObjects) {
    this.hitObjects = hitObjects;
  }

  /**
   * Getter for archive
   *
   * @return archive
   */
  public Set<ArchiveInputEvent> getArchive() {
    return archive;
  }

  /**
   * Setter for archive
   *
   * @param archive The new value for archive
   */
  public void setArchive(Set<ArchiveInputEvent> archive) {
    this.archive = archive;
  }

  /**
   * Get the hitObjects for the session
   *
   * @return The hitObjects list
   */
  public int getScore() {
    return score;
  }

  /**
   * Setter for score
   *
   * @param score The new value for score
   */
  public void setScore(int score) {
    this.score = score;
  }

  /**
   * Getter for grade
   *
   * @return grade
   */
  public int getGrade() {
    return grade;
  }

  /**
   * Setter for grade
   *
   * @param grade The new value for grade
   */
  public void setGrade(int grade) {
    this.grade = grade;
  }

  /**
   * Getter for difficulty
   *
   * @return difficulty
   */
  public double getDifficulty() {
    return difficulty;
  }

  /**
   * Setter for difficulty
   *
   * @param difficulty The new value for difficulty
   */
  public void setDifficulty(double difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * Getter for datetime
   *
   * @return datetime
   */
  public String getDatetime() {
    return datetime;
  }

  /**
   * Setter for datetime
   *
   * @param datetime The new value for datetime
   */
  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }

  /**
   * Getter for beatmapName
   *
   * @return beatmapName
   */
  public String getBeatmapName() {
    return beatmapName;
  }

  /**
   * Setter for beatmapName
   *
   * @param beatmapName The new value for beatmapName
   */
  public void setBeatmapName(String beatmapName) {
    this.beatmapName = beatmapName;
  }

  /**
   * Getter for maxCombo
   *
   * @return maxCombo
   */
  public int getMaxCombo() {
    return maxCombo;
  }

  /**
   * Setter for maxCombo
   *
   * @param maxCombo The new value for maxCombo
   */
  public void setMaxCombo(int maxCombo) {
    this.maxCombo = maxCombo;
  }

  /**
   * Getter for S300
   *
   * @return S300
   */
  public int getS300() {
    return S300;
  }

  /**
   * Setter for S300
   *
   * @param S300 The new value for S300
   */
  public void setS300(int S300) {
    this.S300 = S300;
  }

  /**
   * Getter for S150
   *
   * @return S150
   */
  public int getS150() {
    return S150;
  }

  /**
   * Setter for S150
   *
   * @param S150 The new value for S150
   */
  public void setS150(int S150) {
    this.S150 = S150;
  }

  /**
   * Getter for S50
   *
   * @return S50
   */
  public int getS50() {
    return S50;
  }

  /**
   * Setter for S50
   *
   * @param S50 The new value for S50
   */
  public void setS50(int S50) {
    this.S50 = S50;
  }

  /**
   * Getter for S0
   *
   * @return S0
   */
  public int getS0() {
    return S0;
  }

  /**
   * Setter for S0
   *
   * @param S0 The new value for S0
   */
  public void setS0(int S0) {
    this.S0 = S0;
  }

  /**
   * Getter for cheats
   *
   * @return cheats
   */
  public boolean hasCheats() {
    return cheats;
  }

  /**
   * Setter for cheats
   *
   * @param cheats The new value for cheats
   */
  public void setCheats(boolean cheats) {
    this.cheats = cheats;
  }
}
