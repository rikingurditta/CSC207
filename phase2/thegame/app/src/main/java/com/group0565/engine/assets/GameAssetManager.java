package com.group0565.engine.assets;

import com.group0565.engine.interfaces.LifecycleListener;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** A class representation of GameAssetManager - a manager for all the game assets */
public abstract class GameAssetManager implements LifecycleListener, Closeable {

  /** A map from name to tileSheet map */
  private HashMap<String, HashMap<String, TileSheet>> tileSheetSets = new HashMap<>();
  /** A map from name to audioSet map */
  private HashMap<String, HashMap<String, AudioAsset>> audioSets = new HashMap<>();
  /** A map from name to jsonSet map */
  private HashMap<String, HashMap<String, JsonFile>> jsonSets = new HashMap<>();
  /** A map from name to languagePack map */
  private HashMap<String, HashMap<String, LanguagePack>> languagePackSets = new HashMap<>();
  /** A map from name to theme map */
  private HashMap<String, HashMap<String, ThemeAsset>> themeSets = new HashMap<>();

  /** Call all init functions in the assets mapped to this manager */
  public void init() {
    initSet(tileSheetSets);
    initSet(audioSets);
    initSet(jsonSets);
    initSet(languagePackSets);
    initSet(themeSets);
  }

  /**
   * Init every single asset in the set of asset maps
   *
   * @param assetSet The set of asset maps
   */
  private void initSet(Map<String, ? extends Map<String, ? extends Asset>> assetSet) {
    assetSet.forEach((s, assetMap) -> assetMap.forEach((s1, asset) -> asset.init()));
  }

  /**
   * Register a new asset
   *
   * @param set Target set
   * @param asset New asset
   * @param type New asset's type
   */
  protected void registerAsset(String set, Asset asset, AssetType type) {
    if (type.cls.isInstance(asset))
      switch (type) {
        case TILESHEET:
          registerTileSheet(set, (TileSheet) asset);
          break;
        case AUDIO:
          registerAudioAsset(set, (AudioAsset) asset);
          break;
        case JSON:
          registerJsonFile(set, (JsonFile) asset);
          break;
        case LANGUAGE:
          registerLanguagePack(set, (LanguagePack) asset);
          break;
        case THEME:
          registerThemeSet(set, (ThemeAsset) asset);
          break;
      }
  }

  /**
   * Register a new tileSheet in the set
   *
   * @param set Target set
   * @param sheet New TileSheet
   */
  protected void registerTileSheet(String set, TileSheet sheet) {
    if (!tileSheetSets.containsKey(set)) tileSheetSets.put(set, new HashMap<>());
    HashMap<String, TileSheet> tileSet = tileSheetSets.get(set);
    if (tileSet != null) tileSet.put(sheet.getName(), sheet);
  }

  /**
   * Register a new audioAsset in the set
   *
   * @param set Target set
   * @param audioAsset New audioAsset
   */
  protected void registerAudioAsset(String set, AudioAsset audioAsset) {
    if (!audioSets.containsKey(set)) audioSets.put(set, new HashMap<>());
    HashMap<String, AudioAsset> audioSet = audioSets.get(set);
    if (audioSet != null) audioSet.put(audioAsset.getName(), audioAsset);
  }

  /**
   * Register a new jsonFile in the set
   *
   * @param set Target set
   * @param jsonFile New jsonFile
   */
  protected void registerJsonFile(String set, JsonFile jsonFile) {
    if (!jsonSets.containsKey(set)) jsonSets.put(set, new HashMap<>());
    HashMap<String, JsonFile> jsonSet = jsonSets.get(set);
    if (jsonSet != null) jsonSet.put(jsonFile.getName(), jsonFile);
  }

  /**
   * Register a new languagePack in the set
   *
   * @param set Target set
   * @param languagePack New languagePack
   */
  protected void registerLanguagePack(String set, LanguagePack languagePack) {
    if (!languagePackSets.containsKey(set)) languagePackSets.put(set, new HashMap<>());
    HashMap<String, LanguagePack> languagePackSet = languagePackSets.get(set);
    if (languagePackSet != null) languagePackSet.put(languagePack.getName(), languagePack);
  }

  /**
   * Register a new ThemeSet in the set
   *
   * @param set Target set
   * @param theme New Theme
   */
  protected void registerThemeSet(String set, ThemeAsset theme) {
    if (!themeSets.containsKey(set)) themeSets.put(set, new HashMap<>());
    HashMap<String, ThemeAsset> themeSet = themeSets.get(set);
    if (themeSet != null) themeSet.put(theme.getName(), theme);
  }

  /**
   * Get the tileSheet from the set
   *
   * @param set Target set
   * @param name TileSheet name
   * @return The TileSheet with the given name in the target set
   */
  public TileSheet getTileSheet(String set, String name) {
    HashMap<String, TileSheet> tileSet = tileSheetSets.get(set);
    if (tileSet != null) return tileSet.get(name);
    return null;
  }

  /**
   * Get the AudioAsset from the set
   *
   * @param set Target set
   * @param name AudioAsset name
   * @return The AudioAsset with the given name in the target set
   */
  public AudioAsset getAudioAsset(String set, String name) {
    HashMap<String, AudioAsset> audioSet = this.audioSets.get(set);
    if (audioSet != null) return audioSet.get(name);
    return null;
  }

  /**
   * Get the JsonFile from the set
   *
   * @param set Target set
   * @param name JsonFile name
   * @return The JsonFile with the given name in the target set
   */
  public JsonFile getJsonFile(String set, String name) {
    HashMap<String, JsonFile> jsonSet = this.jsonSets.get(set);
    if (jsonSet != null) return jsonSet.get(name);
    return null;
  }

  /**
   * Get the LanguagePack from the set
   *
   * @param set Target set
   * @param name LanguagePack name
   * @return The LanguagePack with the given name in the target set
   */
  public LanguagePack getLanguagePack(String set, String name) {
    HashMap<String, LanguagePack> languagePackSet = this.languagePackSets.get(set);
    if (languagePackSet != null) return languagePackSet.get(name);
    return null;
  }

  /**
   * Get the ThemeAsset from the set
   *
   * @param set Target set
   * @param name ThemeAsset name
   * @return The ThemeAsset with the given name in the target set
   */
  public ThemeAsset getThemeSet(String set, String name) {
    HashMap<String, ThemeAsset> themeSet = this.themeSets.get(set);
    if (themeSet != null) return themeSet.get(name);
    return null;
  }

  /**
   * Get all TileSheet names in the set
   *
   * @param set Target set
   * @return The names of all the TileSheets in the set
   */
  public Set<String> getTileSheetNames(String set) {
    HashMap<String, TileSheet> tileSet = tileSheetSets.get(set);
    if (tileSet != null) return tileSet.keySet();
    return null;
  }

  /**
   * Get all TileSheet names in the set
   *
   * @param set Target set
   * @return The names of all the TileSheets in the set
   */
  public Set<String> getAudioAssetNames(String set) {
    HashMap<String, AudioAsset> audioSet = this.audioSets.get(set);
    if (audioSet != null) return audioSet.keySet();
    return null;
  }

  /**
   * Get all TileSheet names in the set
   *
   * @param set Target set
   * @return The names of all the TileSheets in the set
   */
  public Set<String> getJsonFileNames(String set) {
    HashMap<String, JsonFile> jsonSet = this.jsonSets.get(set);
    if (jsonSet != null) return jsonSet.keySet();
    return null;
  }

  /**
   * Get all TileSheet names in the set
   *
   * @param set Target set
   * @return The names of all the TileSheets in the set
   */
  public Set<String> getLanguagePackNames(String set) {
    HashMap<String, LanguagePack> languagePackSet = this.languagePackSets.get(set);
    if (languagePackSet != null) return languagePackSet.keySet();
    return null;
  }

  /**
   * Get all TileSheet names in the set
   *
   * @param set Target set
   * @return The names of all the TileSheets in the set
   */
  public Set<String> getThemeSetNames(String set) {
    HashMap<String, ThemeAsset> themeSet = this.themeSets.get(set);
    if (themeSet != null) return themeSet.keySet();
    return null;
  }

  @Override
  public void close() {
    closeSet(tileSheetSets);
    closeSet(audioSets);
    closeSet(jsonSets);
    closeSet(languagePackSets);
    closeSet(themeSets);
  }

  /**
   * Close every single asset in the set of asset maps
   *
   * @param assetSet The set of asset maps
   */
  private void closeSet(Map<String, ? extends Map<String, ? extends Asset>> assetSet) {
    assetSet.forEach((s, assetMap) -> assetMap.forEach((s1, asset) -> asset.close()));
  }
}
