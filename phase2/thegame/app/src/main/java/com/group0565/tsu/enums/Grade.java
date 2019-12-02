package com.group0565.tsu.enums;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.render.ThemedPaintCan;

/**
 * Enum representing a Grade. Also have Bitmaps and other information attached.
 */
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

  /**
   * The minimum score to achive this grade
   */
  private int minScore;
  /**
   * The string representation of the grade
   */
  private String string;
  /**
   * The numerical value of the Grade
   */
  private int value;
  /**
   * The paintcan that can be used to the strings
   */
  private ThemedPaintCan paintCan;

  /**
   * Creates a new Grade
   * @param minScore The minimum score to achive that grade
   * @param string The string representation of the grade
   * @param value The numberical value for the Grade.
   */
  Grade(int minScore, String string, int value) {
    this.minScore = minScore;
    this.string = string;
    this.value = value;
    this.paintCan = new ThemedPaintCan(SET, ThemeFolder + string);
  }

  /**
   * Initializes the paintCans
   * @param preferences The preferences to auto switch paint with
   * @param manager The GameAssetManager from which to load Paints from
   */
  public static void init(GlobalPreferences preferences, GameAssetManager manager){
    for (Grade g : Grade.values()){
      g.paintCan.init(preferences, manager);
    }
  }

  /**
   * Given a grade's value, return the Grade object
   * @param num The grade value
   * @return The Grade object
   */
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

  /**
   * Calculates the Grade given the total score
   * @param score The score to evaluate
   * @return The Grade achived by score
   */
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

  /**
   * Getter for string
   *
   * @return string
   */
  public String getString() {
    return string;
  }

  /**
   * Getter for value
   *
   * @return value
   */
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
