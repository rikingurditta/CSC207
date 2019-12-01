package com.group0565.menuUI.main.enums;

import com.example.thegame.R;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation of Game ID using the xml ids */
public enum GameID {
  GAME1(R.id.CardGame1),
  GAME2(R.id.CardGame2),
  GAME3(R.id.CardGame3);

  /** A map from value to enum name */
  private static Map map = new HashMap<>();

  static {
    /* Fill map with defined values */
    for (GameID gameID : GameID.values()) {
      map.put(gameID.value, gameID);
    }
  }

  /** The value of the enum */
  private int value;

  /**
   * Create a new instance of the enum with the given value
   *
   * @param value The given value
   */
  GameID(int value) {
    this.value = value;
  }

  /**
   * Get the underlying value by the key
   *
   * @param gameID The key
   * @return The value of the enum
   */
  public static GameID valueOf(int gameID) {
    return (GameID) map.get(gameID);
  }

  /**
   * Get the value of the instance
   *
   * @return The value
   */
  public int getValue() {
    return value;
  }
}
