package com.group0565.engine.assets;

import org.json.JSONObject;

/** An object representation of an asset JsonFile */
public class JsonFile extends Asset {
  /** The JSONObject in the file */
  private JSONObject jsonObject;

  /**
   * Create a new JSONFile
   *
   * @param name File name
   * @param path File path
   */
  public JsonFile(String name, String path) {
    super(name, path);
  }

  /**
   * Get the JSONObject in this file
   *
   * @return The underlying JSONObject
   */
  public JSONObject getJsonObject() {
    return jsonObject;
  }

  /**
   * Set the JSONObject for this file
   *
   * @param jsonObject Target JsonObject
   */
  protected void setJsonObject(JSONObject jsonObject) {
    this.jsonObject = jsonObject;
  }
}
