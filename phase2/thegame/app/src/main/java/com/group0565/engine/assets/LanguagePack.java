package com.group0565.engine.assets;

import java.util.HashMap;

/** A language pack asset for text language support */
public class LanguagePack extends Asset {
  /** The text in the default language */
  private String defaultString;
  /** A map of text key to text translation */
  private HashMap<String, String> tokens = new HashMap<>();

  /**
   * Create a new language pack
   *
   * @param name Asset name
   * @param path Asset location
   * @param defaultString Text in default language
   */
  public LanguagePack(String name, String path, String defaultString) {
    super(name, path);
    this.defaultString = defaultString;
  }

  /**
   * Add a new translation to the language pack
   *
   * @param name Text key
   * @param token Text for this language
   * @return The token
   */
  public String registerToken(String name, String token) {
    return tokens.put(name, token);
  }

  /**
   * Get the translation
   *
   * @param name The text key
   * @return The text translation
   */
  public String getToken(String name) {
    return tokens.getOrDefault(name, defaultString);
  }

  /**
   * Get the default text
   *
   * @return Default text
   */
  public String getDefaultString() {
    return defaultString;
  }

  /**
   * Set the default text
   *
   * @param defaultString New default text
   */
  public void setDefaultString(String defaultString) {
    this.defaultString = defaultString;
  }
}
