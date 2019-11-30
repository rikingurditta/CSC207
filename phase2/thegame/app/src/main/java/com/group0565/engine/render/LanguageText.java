package com.group0565.engine.render;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Source;

/** A language-aware implementation of text */
public class LanguageText implements Observer, Source<String> {
  /** A reference to the current GlobalPreferences */
  private GlobalPreferences preferences;
  /** A reference to the AssetManager */
  private GameAssetManager manager;
  /** The target assets set */
  private String set;
  /** The key of the asset in the assets set */
  private String token;
  /** The current displayed text */
  private String current;

  /**
   * Create a new instance of LanguageText
   *
   * @param string The target starting text
   */
  public LanguageText(String string) {
    this.current = string;
  }

  /**
   * Creates a new instance of LanguageText
   *
   * @param preferences The globalPreferences for the game
   * @param manager The asset manager
   * @param set The set of assets
   * @param token The json token of the asset
   */
  public LanguageText(
      GlobalPreferences preferences, GameAssetManager manager, String set, String token) {
    this.manager = manager;
    this.set = set;
    this.token = token;
    this.preferences = preferences;
    this.preferences.registerObserver(this);
  }

  @Override
  public String getValue() {
    return current;
  }

  @Override
  public void observe(Observable observable) {
    if (observable == preferences) {
      this.current = manager.getLanguagePack(set, preferences.getLanguage()).getToken(token);
    }
  }
}
