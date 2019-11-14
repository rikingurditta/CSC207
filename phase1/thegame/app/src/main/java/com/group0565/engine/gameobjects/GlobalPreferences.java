package com.group0565.engine.gameobjects;

import com.group0565.theme.Themes;

public class GlobalPreferences {
  public Themes theme = Themes.LIGHT;
  public String language = "en";
  public double volume = 1.0;

  public GlobalPreferences() {}

  public GlobalPreferences(Themes theme, String language, double volume) {
    this.theme = theme;
    this.language = language;
    this.volume = volume;
  }
}
