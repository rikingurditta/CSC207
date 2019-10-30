package com.thegame.main.enums;

import com.example.thegame.R;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation of Menu Item ID using the xml ids */
public enum MenuOptionID {
  SETTINGS(R.id.action_settings),
  SIGN_OUT(R.id.sign_out);

  private int value;
  private static Map map = new HashMap<>();

  MenuOptionID(int value) {
    this.value = value;
  }

  static {
    for (MenuOptionID OptionID : MenuOptionID.values()) {
      map.put(OptionID.value, OptionID);
    }
  }

  public static MenuOptionID valueOf(int MenuOptionID) {
    return (MenuOptionID) map.get(MenuOptionID);
  }

  public int getValue() {
    return value;
  }
}
