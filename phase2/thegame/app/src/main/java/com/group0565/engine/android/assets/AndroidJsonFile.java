package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.util.Log;

import com.group0565.engine.assets.JsonFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/** Android-specific implementation of JsonFile */
public class AndroidJsonFile extends JsonFile {
  /** The json files folder */
  public static final String JSON_FOLDER = "json/";
  /** Class tag constant */
  private static final String TAG = "AndroidJsonFile";
  /** A reference to an AssetManager */
  private AssetManager assetManager;

  /**
   * Create an AndroidJsonFile
   *
   * @param name The file name
   * @param path The file path
   * @param assetManager The owner AssetManager
   */
  public AndroidJsonFile(String name, String path, AssetManager assetManager) {
    super(name, path);
    this.assetManager = assetManager;
  }

  @Override
  public void init() {
    super.init();
    try {
      InputStream stream = assetManager.open(JSON_FOLDER + getPath());
      Scanner scanner = new Scanner(stream).useDelimiter("\\A");
      String json = scanner.hasNext() ? scanner.next() : "";
      setJsonObject(new JSONObject(json));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (JSONException e) {
      Log.e(TAG, "JSON File" + getName() + "without Object as root is not supported.", e);
    }
  }
}
