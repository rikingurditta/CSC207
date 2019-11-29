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

  /** A map from value to enum name */
  private static Map map = new HashMap<>();

  static {
    /* Fill map with defined values */
    for (MenuOptionID OptionID : MenuOptionID.values()) {
      map.put(OptionID.value, OptionID);
    }
  }

  /** The value of the enum */
  private int value;

  /**
   * Create a new instance of the enum with the given value
   *
   * @param value The given value
   */
  MenuOptionID(int value) {
    this.value = value;
  }

  /**
   * Get the underlying value by the key
   *
   * @param menuOptionID The key
   * @return The value of the enum
   */
  public static MenuOptionID valueOf(int menuOptionID) {
    return (MenuOptionID) map.get(menuOptionID);
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
