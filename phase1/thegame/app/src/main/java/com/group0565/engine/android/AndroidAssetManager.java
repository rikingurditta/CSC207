package com.group0565.engine.android;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.group0565.engine.assets.AudioAsset;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.interfaces.LifecycleListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;

public class AndroidAssetManager extends GameAssetManager{
    private static final String TAG = "AndroidAssetManager";
    private static final String TILESHEET_NAME = "TileSheets";
    private static final String AUDIO_NAME = "Audio";

    private AssetManager assetManager;

    public AndroidAssetManager(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    @Override
    public void init() {
        try {
            InputStream stream = assetManager.open("json/resources.json");
            JsonReader reader = new JsonReader(new InputStreamReader(stream));
            reader.beginObject();
            while (reader.peek() == JsonToken.NAME){
                String name = reader.nextName();
                if (name.equals(TILESHEET_NAME))
                    readTileSheetSet(reader);
                else if(name.equals(AUDIO_NAME))
                    readAudioSet(reader);
            }
            reader.endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.init();
    }

    private void readTileSheetSet(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.peek() == JsonToken.NAME){
            String name = reader.nextName();
            TileSheet sheet = readTileSheet(reader);
            this.registerTileSheet(name, sheet);
        }
        reader.endObject();
    }

    private TileSheet readTileSheet(JsonReader reader) throws IOException {
        reader.beginObject();
        String name = null;
        String path = null;
        int tileWidth = -1;
        int tileHeight = -1;
        while (reader.peek() != JsonToken.END_OBJECT){
            switch (reader.nextName()){
                case "Name":
                    name = reader.nextString();
                    break;
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

    private void readAudioSet(JsonReader reader) throws IOException{
        reader.beginObject();
        while (reader.peek() == JsonToken.NAME){
            String name = reader.nextName();
            AudioAsset audio = readAudio(reader);
            this.registerAudioAsset(name, audio);
        }
        reader.endObject();
    }

    private AudioAsset readAudio(JsonReader reader) throws IOException {
        reader.beginObject();
        String name = null;
        String path = null;
        int volume = -1;
        while (reader.peek() != JsonToken.END_OBJECT){
            switch (reader.nextName()){
                case "Name":
                    name = reader.nextString();
                    break;
                case "Path":
                    path = reader.nextString();
                    break;
                case "Volume":
                    volume = reader.nextInt();
                    break;
            }
        }
        reader.endObject();
        return new AndroidAudioAsset(name, path, volume, assetManager);
    }
}