package com.group0565.hitObjectsRepository;

import com.group0565.tsu.enums.Scores;
import com.group0565.tsu.game.HitObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionHitObjects {
  /**
   * Lambda Map to set scores easier
   */
  private HashMap<Scores, Setter<Integer>> scoresSetters = new HashMap<>();
  private List<HitObject> hitObjects;
  private int score;
  private int grade;
  private double difficulty;
  private String datetime;
  private boolean cheats;
  private int maxCombo;
  private int S300;
  private int S150;
  private int S50;
  private int S0;

  public SessionHitObjects(List<HitObject> hitObjects) {
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
    scoresSetters.put(Scores.S300, this::setS300);
    scoresSetters.put(Scores.S150, this::setS150);
    scoresSetters.put(Scores.S50, this::setS50);
    scoresSetters.put(Scores.S0, this::setS0);
  }

  public SessionHitObjects() {
    this(new ArrayList<>());
  }

  public List<HitObject> getHitObjects() {
    return hitObjects;
  }

  public void setHitObjects(List<HitObject> hitObjects) {
    this.hitObjects = hitObjects;
  }

  public void addToList(HitObject newHitObject) {
    this.hitObjects.add(newHitObject);
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public int getGrade() {
    return grade;
  }

  public void setGrade(int grade) {
    this.grade = grade;
  }

  public double getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(double difficulty) {
    this.difficulty = difficulty;
  }

  public String getDatetime() {
    return datetime;
  }

  public void setDatetime(String datetime) {
    this.datetime = datetime;
  }

  public int getMaxCombo() {
    return maxCombo;
  }

  public void setMaxCombo(int maxCombo) {
    this.maxCombo = maxCombo;
  }

  public int getS300() {
    return S300;
  }

  public void setS300(int s300) {
    S300 = s300;
  }

  public int getS150() {
    return S150;
  }

  public void setS150(int s150) {
    S150 = s150;
  }

  public int getS50() {
    return S50;
  }

  public void setS50(int s50) {
    S50 = s50;
  }

  public int getS0() {
    return S0;
  }

  public void setS0(int s0) {
    S0 = s0;
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

  public void setHitScore(Scores score, int count) {
    Setter setter = scoresSetters.get(score);
    if (setter != null)
      setter.set(count);
  }

  public void setHitScore(Map<Scores, Integer> scoreCount) {
    for (Scores score : scoreCount.keySet()) {
      Integer value = scoreCount.get(score);
      if (value != null)
        setHitScore(score, value);
    }
  }

  private interface Setter<T>{
    void set(T t);
  }
}
