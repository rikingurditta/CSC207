package com.group0565.engine.assets;

/** An enum representing all possible Asset Types */
public enum AssetType {
  TILESHEET(TileSheet.class),
  AUDIO(AudioAsset.class),
  JSON(JsonFile.class),
  LANGUAGE(LanguagePack.class),
  THEME(ThemeAsset.class);

  /** The class value of the enum */
  public final Class cls;

  /**
   * Create a new AssetType
   *
   * @param cls The class of the type
   */
  AssetType(Class cls) {
    this.cls = cls;
  }
}
