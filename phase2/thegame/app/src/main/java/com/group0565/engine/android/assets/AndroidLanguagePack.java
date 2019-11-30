package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.group0565.engine.assets.LanguagePack;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/** Android-specific implementation of LanguagePack */
public class AndroidLanguagePack extends LanguagePack {
  /** The language packs folder */
  public static final String LANGUAGE_FOLDER = "languagePacks/";
  /** Class tag constant */
  private static final String TAG = "AndroidLanguagePack";
  /** A reference to an AssetManager */
  private AssetManager assetManager;

  /**
   * Create a new AndroidLanguagePAck
   *
   * @param name The language pack name
   * @param path Language pack path
   * @param defaultString The default string for the text
   * @param assetManager The owner asset manager
   */
  public AndroidLanguagePack(
      String name, String path, String defaultString, AssetManager assetManager) {
    super(name, path, defaultString);
    this.assetManager = assetManager;
  }

  @Override
  public void init() {
    super.init();
    try {
      InputStream stream = assetManager.open(LANGUAGE_FOLDER + getPath());
      JsonReader reader = new JsonReader(new InputStreamReader(stream));
      reader.beginObject();
      while (reader.peek() == JsonToken.NAME) {
        this.registerToken(reader.nextName(), reader.nextString());
      }
      reader.endObject();
    } catch (IOException e) {
      Log.e(TAG, "Reading Language Pack " + getName() + " Failed.", e);
    }
  }
}
