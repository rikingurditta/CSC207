package com.group0565.tsu.core;

import android.content.res.Resources;

import com.example.thegame.R;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.EventObserver;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.preferences.IPreferenceInteractor;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.preferences.UserPreferenceFactory;
import com.group0565.theme.Themes;
import com.group0565.ui.TheGameApplication;

/** Class Responsible for reading and writing preferences to the repository; */
public class TsuPreferences extends GlobalPreferences implements Observable {
  // Event Constants
  public static final String DIFFICULTY_CHANGE = "Difficulty Changed";
  public static final String AUTO_CHANGE = "Auto Changed";

  // Constants for the names of the preferences
  private String ThemePrefName = "";
  private String VolumePrefName = "";
  private String LanguagePrefName = "";
  private String DifficultyPrefName = "";
  private String AutoPrefName = "";

  // Local Copies of the preferences
  private float difficulty;
  private boolean auto;
  /** Reload the preferences to match the most updated preferences */
  public TsuPreferences reload() {
    Resources resources = TheGameApplication.getInstance().getResources();
    ThemePrefName = resources.getString(R.string.theme_pref_id);
    LanguagePrefName = resources.getString(R.string.lan_pref_id);
    VolumePrefName = resources.getString(R.string.vol_pref_id);
    DifficultyPrefName = "tsu-difficulty";
    AutoPrefName = "tsu-auto";

    IPreferenceInteractor prefInter = PreferencesInjector.inject();

    // Load Theme, Language and volume
    super.setTheme(Themes.valueOf(prefInter.getTheme()));
    super.setLanguage(prefInter.getLanguage());
    super.setVolume(
        prefInter.getVolume() / 100D); // Volume is stores 0-100 in repo, but engine runs on 0-1

    // Load difficulty
    Object difficulty = prefInter.getPreference(DifficultyPrefName, 5);
    // If we loaded some garbage from the database, just set difficulty to the default 5
    if (!(difficulty instanceof Double) && !(difficulty instanceof Float)) difficulty = 0.5D;
    // If we got a double or a float, use these values
    if (difficulty instanceof Double) this.difficulty = ((float) (double) difficulty);
    else this.difficulty = ((float) difficulty);
    // Load Auto
    Object auto;
    // If we didn't get something usable, use default value of false
    if (!((auto = prefInter.getPreference(AutoPrefName, false)) instanceof Boolean)) auto = false;
    this.auto = (boolean) auto;

    return this;
  }

  /**
   * Setter for the theme
   *
   * @param theme The new theme
   */
  @Override
  public void setTheme(Themes theme) {
    super.setTheme(theme);
    setPreferences(ThemePrefName, theme.name());
  }

  /**
   * Setter for the language
   *
   * @param language The new language
   */
  @Override
  public void setLanguage(String language) {
    super.setLanguage(language);
    setPreferences(LanguagePrefName, language);
  }

  /**
   * Getter for the difficulty
   *
   * @return The difficulty
   */
  public float getDifficulty() {
    return difficulty;
  }

  /**
   * Setter for the difficulty
   *
   * @param difficulty The new difficulty
   */
  public void setDifficulty(float difficulty) {
    this.difficulty = difficulty;
    this.notifyObservers(new ObservationEvent<>(DIFFICULTY_CHANGE, difficulty));
    setPreferences(DifficultyPrefName, difficulty);
  }

  /**
   * Setter for the volume
   *
   * @param volume The new volume
   */
  @Override
  public void setVolume(double volume) {
    super.setVolume(volume);
    setPreferences(VolumePrefName, (int) (volume * 100));
  }

  /**
   * Getter for the auto state
   *
   * @return The auto state
   */
  public boolean getAuto() {
    return auto;
  }

  /**
   * Setter for the auto state
   *
   * @param auto The new auto state
   */
  public void setAuto(boolean auto) {
    this.auto = auto;
    this.notifyObservers(new ObservationEvent<>(AUTO_CHANGE, auto));
    setPreferences(AutoPrefName, auto);
  }

  @Override
  public void registerObserver(EventObserver eventObserver) {
    super.registerObserver(eventObserver);
    this.notifyObservers(new ObservationEvent<>(DIFFICULTY_CHANGE, difficulty));
    this.notifyObservers(new ObservationEvent<>(AUTO_CHANGE, auto));
  }

  /** Helper method to write changes to the repository */
  private void setPreferences(String name, Object value) {
    IPreferenceInteractor interactor = PreferencesInjector.inject();
    interactor.updatePreference(UserPreferenceFactory.getUserPreference(name, value));
  }
}
