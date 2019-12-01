package com.group0565.engine.gameobjects;

import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.theme.Themes;

/** An object representation of game preferences service */
public class GlobalPreferences implements Observable {
  /** Text for theme change event */
  public static final String THEME_CHANGE = "Theme Changed";
  /** Text for volume change event */
  public static final String VOLUME_CHANGE = "Volume Changed";
  /** Text for language change event */
  public static final String LANGUAGE_CHANGE = "Language Changed";
  /** The current theme */
  private Themes theme = Themes.LIGHT;
  /** The current language */
  private String language = "en";
  /** The current volume */
  private double volume = 1.0;

  /** Create a new GlobalPreferences object with default values */
  public GlobalPreferences() {}

  /**
   * Create a new GlobalPreferences object
   *
   * @param theme Current theme
   * @param language Current language
   * @param volume Current volume
   */
  public GlobalPreferences(Themes theme, String language, double volume) {
    this.setTheme(theme);
    this.setLanguage(language);
    this.setVolume(volume);
  }

  /**
   * Get the current theme
   *
   * @return The current theme
   */
  public Themes getTheme() {
    return theme;
  }

  /**
   * Set the current theme and notify observers
   *
   * @param theme The target theme
   */
  public void setTheme(Themes theme) {
    this.theme = theme;
    this.notifyObservers(new ObservationEvent<>(THEME_CHANGE, theme));
  }

  /**
   * Get the current language
   *
   * @return The current language
   */
  public String getLanguage() {
    return language;
  }

  /**
   * Set the current language and notify observers
   *
   * @param language The target theme
   */
  public void setLanguage(String language) {
    this.language = language;
    this.notifyObservers(new ObservationEvent<>(LANGUAGE_CHANGE, language));
  }

  /**
   * Get the current volume
   *
   * @return The current volume
   */
  public double getVolume() {
    return volume;
  }

  /**
   * Set the current volume and notify observers
   *
   * @param volume The target volume
   */
  public void setVolume(double volume) {
    this.volume = volume;
    this.notifyObservers(new ObservationEvent<>(VOLUME_CHANGE, volume));
  }

  @Override
  public void registerObserver(EventObserver eventObserver) {
    Observable.super.registerObserver(eventObserver);
    this.notifyObservers(new ObservationEvent<>(THEME_CHANGE, theme));
    this.notifyObservers(new ObservationEvent<>(LANGUAGE_CHANGE, language));
    this.notifyObservers(new ObservationEvent<>(VOLUME_CHANGE, volume));
  }
}
