package com.group0565.tsu.enums;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.render.ThemedPaintCan;

public enum Grade {

  SS(100_000_000, "SS", 5),
  S(50_000_000, "S", 4),
  A(1_000_000, "A", 3),
  B(500_000, "B", 2),
  C(100_000, "C", 1),
  F(0, "F", 0);

  //Asset Constants
  private static final String SET = "Tsu";
  private static final String ThemeFolder = "Grade.";

  private int minScore;
  private String string;
  private int value;
  private ThemedPaintCan paintCan;

  Grade(int minScore, String string, int value) {
    this.minScore = minScore;
    this.string = string;
    this.value = value;
    this.paintCan = new ThemedPaintCan(SET, ThemeFolder + string);
  }

  public static void init(GlobalPreferences preferences, GameAssetManager manager){
    for (Grade g : Grade.values()){
      g.paintCan.init(preferences, manager);
    }
  }

  public static Grade str2Grade(String str) {
    switch (str) {
      case "SS":
        return SS;
      case "S":
        return S;
      case "A":
        return A;
      case "B":
        return B;
      case "C":
        return C;
      default:
        return F;
    }
  }

  public static Grade num2Grade(int num) {
    switch (num) {
      case 5:
        return SS;
      case 4:
        return S;
      case 3:
        return A;
      case 2:
        return B;
      case 1:
        return C;
      default:
        return F;
    }
  }

  public static Grade score2Grade(int score) {
    if (score >= SS.minScore) {
      return SS;
    } else if (score >= S.minScore) {
      return S;
    } else if (score >= A.minScore) {
      return A;
    } else if (score >= B.minScore) {
      return B;
    } else if (score >= C.minScore) {
      return C;
    } else return F;
  }

  public int getMinScore() {
    return minScore;
  }

  public String getString() {
    return string;
  }

  public int getValue() {
    return value;
  }

  /**
   * Getter for paintCan
   *
   * @return paintCan
   */
  public ThemedPaintCan getPaintCan() {
    return paintCan;
  }
}
