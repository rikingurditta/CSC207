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
import com.group0565.engine.assets.TileSheet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AndroidAssetManager extends GameAssetManager {
    private static final String TAG = "AndroidAssetManager";
    private static final String TILESHEET_NAME = "TileSheets";
    private static final String AUDIO_NAME = "Audio";
    private static final String JSON_NAME = "Json";
    private static final String LANGUAGE_NAME = "LanguagePack";

    private AssetManager assetManager;

    public AndroidAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
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
                }
                readSet(reader, type);
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.init();
    }

    private void readSet(JsonReader reader, AssetType type) throws IOException {
        reader.beginObject();
        while (reader.peek() == JsonToken.NAME) {
            String setName = reader.nextName();
            reader.beginObject();
            while (reader.peek() == JsonToken.NAME) {
                String sheetName = reader.nextName();
                Asset asset = readAsset(sheetName, reader, type);
                this.registerAsset(setName, asset, type);
            }
            reader.endObject();
        }
        reader.endObject();
    }

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
            default:
                return null;
        }
    }

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
}
