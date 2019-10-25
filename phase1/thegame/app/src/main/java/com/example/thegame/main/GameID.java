package com.example.thegame.main;

import com.example.thegame.R;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation of Game ID using the xml ids */
public enum GameID {
  GAME1(R.id.CardGame1),
  GAME2(R.id.CardGame2),
  GAME3(R.id.CardGame3);

  private int value;
  private static Map map = new HashMap<>();

  GameID(int value) {
    this.value = value;
  }

  static {
    for (com.example.thegame.main.GameID GameID : GameID.values()) {
      map.put(GameID.value, GameID);
    }
  }

  public static GameID valueOf(int GameID) {
    return (com.example.thegame.main.GameID) map.get(GameID);
  }

  public int getValue() {
    return value;
  }
}
