package com.group0565.menuUI.main.enums;

import com.example.thegame.R;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation of Menu Item ID using the xml ids */
public enum MenuOptionID {
  SETTINGS(R.id.action_settings),
  STATISTICS(R.id.action_statistics),
  SIGN_OUT(R.id.sign_out),
  ACHIEVEMENTS(R.id.action_achievements);

  private static Map map = new HashMap<>();

  static {
    for (MenuOptionID OptionID : MenuOptionID.values()) {
      map.put(OptionID.value, OptionID);
    }
  }

  private int value;

  MenuOptionID(int value) {
    this.value = value;
  }

  public static MenuOptionID valueOf(int MenuOptionID) {
    return (MenuOptionID) map.get(MenuOptionID);
  }

  public int getValue() {
    return value;
  }
}
