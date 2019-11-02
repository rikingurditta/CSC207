package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.group0565.engine.assets.LanguagePack;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AndroidLanguagePack extends LanguagePack {
    public static final String LANGUAGE_FOLDER = "languagePacks/";
    private static final String TAG = "AndroidLanguagePack";
    private AssetManager assetManager;

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
