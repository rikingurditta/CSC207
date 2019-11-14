package com.group0565.menuUI.main.enums;

import com.example.thegame.R;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation of Game ID using the xml ids */
public enum GameID {
  GAME1(R.id.CardGame1),
  GAME2(R.id.CardGame2),
  GAME3(R.id.CardGame3);

  private static Map map = new HashMap<>();

  static {
    for (com.group0565.menuUI.main.enums.GameID GameID : GameID.values()) {
      map.put(GameID.value, GameID);
    }
  }

  private int value;

  GameID(int value) {
    this.value = value;
  }

  public static GameID valueOf(int GameID) {
    return (com.group0565.menuUI.main.enums.GameID) map.get(GameID);
  }

  public int getValue() {
    return value;
  }
}
