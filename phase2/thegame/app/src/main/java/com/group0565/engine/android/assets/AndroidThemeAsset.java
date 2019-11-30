package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.assets.ThemeAsset;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.TypeFaceFactory;
import com.group0565.engine.interfaces.Typeface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/** Android-specific implementation of ThemeAsset */
public class AndroidThemeAsset extends ThemeAsset {
  /** The themes folder */
  public static final String THEME_FOLDER = "theme/";
  /** The paint suffix constant */
  private static final String PaintSuffix = "Paint";
  /** A reference to an AssetManager */
  private AssetManager assetManager;
  /** A map from name to AndroidPaint */
  private HashMap<String, AndroidPaint> paints = new HashMap<>();

  /**
   * Create an AndroidThemeAsset
   *
   * @param name The theme name
   * @param path The theme location
   * @param assetManager The owner asset manager
   */
  public AndroidThemeAsset(String name, String path, AssetManager assetManager) {
    super(name, path);
    this.assetManager = assetManager;
  }

  @Override
  public void init() {
    super.init();
    try {
      InputStream stream = assetManager.open(THEME_FOLDER + getPath());
      //            InputStream stream = assetManager.open("languagePacks/Tsu/en.json");
      String stack = "";
      JsonReader reader = new JsonReader(new InputStreamReader(stream));
      readStack(stack, reader);
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Parse a paint from a string stack
   *
   * @param stack The string stack
   * @param reader A json reader
   * @throws IOException An error reading a file
   */
  private void readStack(String stack, JsonReader reader) throws IOException {
    if (reader.peek() != JsonToken.BEGIN_OBJECT) return;
    reader.beginObject();
    while (reader.peek() == JsonToken.NAME) {
      String name = reader.nextName();
      if (name.endsWith(PaintSuffix))
        readPaint(stack, name.substring(0, name.length() - PaintSuffix.length()), reader);
      else readStack((stack.equals("") ? "" : stack + ".") + name, reader);
    }
    reader.endObject();
  }

  /**
   * Read Paint from string
   *
   * @param stack The source string
   * @param name The theme name
   * @param reader A json reader
   * @throws IOException An error reading a file
   */
  private void readPaint(String stack, String name, JsonReader reader) throws IOException {
    AndroidPaint paint = new AndroidPaint();
    reader.beginObject();
    while (reader.peek() == JsonToken.NAME) {
      String tokenName = reader.nextName();
      switch (tokenName) {
        case "ARGB":
          reader.beginArray();
          paint.setARGB(reader.nextInt(), reader.nextInt(), reader.nextInt(), reader.nextInt());
          reader.endArray();
          break;
        case "TextSize":
          paint.setTextSize((float) reader.nextDouble());
          break;
        case "Color":
          paint.setColor(reader.nextInt());
          break;
        case "StrokeWidth":
          paint.setStrokeWidth((float) reader.nextDouble());
          break;
        case "Font":
          reader.beginObject();
          String family = null;
          String style = null;
          while (reader.peek() == JsonToken.NAME) {
            String fontToken = reader.nextName();
            if (fontToken.equals("Family")) family = reader.nextString();
            else if (fontToken.equals("Style")) style = reader.nextString();
            if (style == null) throw new IllegalThemeAssetException("Type of Font must exist.");
            paint.setTypeface(TypeFaceFactory.create(family, Typeface.Style.valueOf(style)));
          }
          reader.endObject();
          break;
      }
    }
    reader.endObject();
    paints.put(stack + "." + name, paint);
  }

  @Override
  public AndroidPaint getPaint(String name) {
    return paints.getOrDefault(name, new AndroidPaint());
  }
}
