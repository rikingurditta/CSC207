package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.assets.ThemeAsset;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.interfaces.Typeface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class AndroidThemeAsset extends ThemeAsset {
    public static final String THEME_FOLDER = "theme/";
    private static final String TAG = "AndroidThemeAsset";
    private static final String PaintSuffix = "Paint";
    private AssetManager assetManager;
    private HashMap<String, AndroidPaint> paints = new HashMap<>();

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

    private void readStack(String stack, JsonReader reader) throws IOException {
        if (reader.peek() != JsonToken.BEGIN_OBJECT)
            return;
        reader.beginObject();
        while (reader.peek() == JsonToken.NAME){
            String name = reader.nextName();
            if (name.endsWith(PaintSuffix))
                readPaint(stack, name.substring(0, name.length() - PaintSuffix.length()), reader);
            else
                readStack((stack.equals("") ? "" : stack + ".") + name, reader);
        }
        reader.endObject();
    }

    private void readPaint(String stack, String name, JsonReader reader) throws IOException {
        AndroidPaint paint = new AndroidPaint();
        reader.beginObject();
        while (reader.peek() == JsonToken.NAME){
            String tokenName = reader.nextName();
            if (tokenName.equals("ARGB")){
                reader.beginArray();
                paint.setARGB(reader.nextInt(), reader.nextInt(), reader.nextInt(), reader.nextInt());
                reader.endArray();
            } else if (tokenName.equals("TextSize")){
                paint.setTextSize((float) reader.nextDouble());
            } else if (tokenName.equals("Color")){
                paint.setColor(reader.nextInt());
            } else if (tokenName.equals("StrokeWidth")){
                paint.setStrokeWidth((float) reader.nextDouble());
            } else if (tokenName.equals("Font")){
                reader.beginObject();
                String family = null;
                String style = null;
                while (reader.peek() == JsonToken.NAME){
                    String fontToken = reader.nextName();
                    if (fontToken.equals("Family"))
                        family = reader.nextString();
                    else if(fontToken.equals("Style"))
                        style = reader.nextString();
                    if (style == null)
                        throw new IllegalThemeAssetException("Type of Font must exist.");
                    paint.setTypeface(Typeface.create(family, Typeface.Style.valueOf(style)));
                }
                reader.endObject();
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
