package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;

import com.group0565.engine.assets.Asset;
import com.group0565.engine.assets.AssetType;
import com.group0565.engine.assets.AudioAsset;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.JsonFile;
import com.group0565.engine.assets.LanguagePack;
import com.group0565.engine.assets.ThemeAsset;
import com.group0565.engine.assets.TileSheet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** Android-specific implementation of GameAssetManager */
public class AndroidAssetManager extends GameAssetManager {
  /** Class tag */
  private static final String TAG = "AndroidAssetManager";
  /** TileSheet name constant */
  private static final String TILESHEET_NAME = "TileSheets";
  /** Audio name constant */
  private static final String AUDIO_NAME = "Audio";
  /** Json name constant */
  private static final String JSON_NAME = "Json";
  /** Language pack name constant */
  private static final String LANGUAGE_NAME = "LanguagePack";
  /** Theme name constant */
  private static final String THEME_NAME = "Theme";
  /** Achievements name constant */
  private static final String ACHIEVEMENTS_NAME = "Achievements";

  /** A reference to an AssetManager */
  private AssetManager assetManager;
  /** A reference to an achievement manager */
  private AndroidAchievementManager achievementManager;

  /**
   * Create a new Android Asset Manager
   *
   * @param assetManager An asset manager
   * @param achievementManager An achievement manager
   */
  public AndroidAssetManager(
      AssetManager assetManager, AndroidAchievementManager achievementManager) {
    this.assetManager = assetManager;
    this.achievementManager = achievementManager;
  }

  @Override
  public void init() {
    try {
      InputStream stream = assetManager.open("json/resources.json");
      JsonReader reader = new JsonReader(new InputStreamReader(stream));
      reader.beginObject();
      while (reader.peek() == JsonToken.NAME) {
        String name = reader.nextName();
        AssetType type = null;
        switch (name) {
          case TILESHEET_NAME:
            type = AssetType.TILESHEET;
            break;
          case AUDIO_NAME:
            type = AssetType.AUDIO;
            break;
          case JSON_NAME:
            type = AssetType.JSON;
            break;
          case LANGUAGE_NAME:
            type = AssetType.LANGUAGE;
            break;
          case THEME_NAME:
            type = AssetType.THEME;
            break;
          case ACHIEVEMENTS_NAME:
            readAchievements(reader);
            break;
        }
        if (type != null) readSet(reader, type);
      }
      reader.endObject();
    } catch (IOException e) {
      e.printStackTrace();
    }
    super.init();
  }

  /**
   * Read an asset set from a json
   *
   * @param reader The json reader
   * @param type The asset type
   * @throws IOException An error reading the file
   */
  private void readSet(JsonReader reader, AssetType type) throws IOException {
    reader.beginObject();
    while (reader.peek() == JsonToken.NAME) {
      String setName = reader.nextName();
      reader.beginObject();
      while (reader.peek() == JsonToken.NAME) {
        String sheetName = reader.nextName();
        Asset asset = readAsset(sheetName, reader, type);
        if (asset != null) this.registerAsset(setName, asset, type);
      }
      reader.endObject();
    }
    reader.endObject();
  }

  /**
   * Parse a specific asset from an asset set
   *
   * @param name The asset name
   * @param reader The json file reader
   * @param type The asset type
   * @return The Asset object
   * @throws IOException An error reading the file
   */
  private Asset readAsset(String name, JsonReader reader, AssetType type) throws IOException {
    switch (type) {
      case TILESHEET:
        return readTileSheet(name, reader);
      case AUDIO:
        return readAudio(name, reader);
      case JSON:
        return readJson(name, reader);
      case LANGUAGE:
        return readLanguagePack(name, reader);
      case THEME:
        return readTheme(name, reader);
      default:
        return null;
    }
  }

  /**
   * Parse a specific TileSheet from an asset set
   *
   * @param name The asset name
   * @param reader The json file reader
   * @return The TileSheet object
   * @throws IOException An error reading the file
   */
  private TileSheet readTileSheet(String name, JsonReader reader) throws IOException {
    reader.beginObject();
    String path = null;
    int tileWidth = -1;
    int tileHeight = -1;
    while (reader.peek() != JsonToken.END_OBJECT) {
      switch (reader.nextName()) {
        case "Path":
          path = reader.nextString();
          break;
        case "TileWidth":
          tileWidth = reader.nextInt();
          break;
        case "TileHeight":
          tileHeight = reader.nextInt();
          break;
      }
    }
    reader.endObject();
    return new AndroidTileSheet(name, path, tileWidth, tileHeight, assetManager);
  }

  /**
   * Parse a specific audio from an asset set
   *
   * @param name The asset name
   * @param reader The json file reader
   * @return The AudioAsset object
   * @throws IOException An error reading the file
   */
  private AudioAsset readAudio(String name, JsonReader reader) throws IOException {
    reader.beginObject();
    String path = null;
    float volume = -1;
    while (reader.peek() != JsonToken.END_OBJECT) {
      switch (reader.nextName()) {
        case "Path":
          path = reader.nextString();
          break;
        case "Volume":
          volume = (float) reader.nextDouble();
          break;
      }
    }
    reader.endObject();
    return new AndroidAudioAsset(name, path, volume, assetManager);
  }

  /**
   * Parse a json asset from an asset set
   *
   * @param name The asset name
   * @param reader The json file reader
   * @return The json object
   * @throws IOException An error reading the file
   */
  private JsonFile readJson(String name, JsonReader reader) throws IOException {
    reader.beginObject();
    String path = null;
    while (reader.peek() != JsonToken.END_OBJECT) {
      switch (reader.nextName()) {
        case "Path":
          path = reader.nextString();
          break;
      }
    }
    reader.endObject();
    return new AndroidJsonFile(name, path, assetManager);
  }

  /**
   * Parse a specific language pack from an asset set
   *
   * @param name The asset name
   * @param reader The json file reader
   * @return The LanguagePack object
   * @throws IOException An error reading the file
   */
  private LanguagePack readLanguagePack(String name, JsonReader reader) throws IOException {
    reader.beginObject();
    String path = null;
    String defaultString = "String Not Found";
    while (reader.peek() != JsonToken.END_OBJECT) {
      switch (reader.nextName()) {
        case "Path":
          path = reader.nextString();
          break;
        case "Default":
          defaultString = reader.nextString();
          break;
      }
    }
    reader.endObject();
    return new AndroidLanguagePack(name, path, defaultString, assetManager);
  }

  /**
   * Parse a specific theme from an asset set
   *
   * @param name The asset name
   * @param reader The json file reader
   * @return The ThemeAsset object
   * @throws IOException An error reading the file
   */
  private ThemeAsset readTheme(String name, JsonReader reader) throws IOException {
    reader.beginObject();
    String path = null;
    while (reader.peek() != JsonToken.END_OBJECT) {
      switch (reader.nextName()) {
        case "Path":
          path = reader.nextString();
          break;
      }
    }
    reader.endObject();
    return new AndroidThemeAsset(name, path, assetManager);
  }

  /**
   * Parse achievements from an asset set and add them to AchievementManager
   *
   * @param reader The json file reader
   * @throws IOException An error reading the file
   */
  private void readAchievements(JsonReader reader) throws IOException {
    reader.beginObject();
    while (reader.peek() == JsonToken.NAME) {
      String name = reader.nextName();
      String path = reader.nextString();
      achievementManager.loadAchievements(name, path);
    }
    reader.endObject();
  }
}
