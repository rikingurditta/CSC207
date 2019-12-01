package com.group0565.theme;

import com.example.thegame.R;

import java.util.HashMap;
import java.util.Map;

/** An Enum representation of Themes to map available themes to Android themes */
public enum Themes {
  DARK(R.style.AppThemeDark),
  LIGHT(R.style.AppThemeLight);

  /** A map from value to enum name */
  private static Map map = new HashMap<>();

  static {
    /* Fill map with defined values */
    for (Themes Theme : Themes.values()) {
      map.put(Theme.value, Theme);
    }
  }

  /** The value of the enum */
  private int value;

  /**
   * Create a new instance of the enum with the given value
   *
   * @param value The given value
   */
  Themes(int value) {
    this.value = value;
  }

  /**
   * Get the enum of the theme with the given id
   *
   * @return The enum with id Theme
   */
  public static Themes valueOf(int Theme) {
    return (Themes) map.get(Theme);
  }

  /**
   * Get the default theme
   *
   * @return The value of default theme
   */
  public static Themes getDefault() {
    return Themes.LIGHT;
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
