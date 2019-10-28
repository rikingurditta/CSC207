package com.group0565.tsu.gameObjects;

import android.util.Log;

import com.group0565.engine.assets.AudioAsset;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.JsonFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Beatmap {
    private static final String TAG = "Beatmap";
    private JsonFile jsonFile;
    private JSONObject jsonObject;
    private String name;
    private String audioSet;
    private String audioFile;
    private AudioAsset audio;
    private String title;
    private String artist;
    private String mapper;
    private double difficulty;
    private long leadin;
    private List<HitObject> hitObjects;


    public Beatmap(String set, String name, GameAssetManager manager) {
        super();
        this.jsonFile = manager.getJsonFile(set, name);
        this.jsonObject = jsonFile.getJsonObject();
        try {
            this.name = jsonObject.getString("Name");
            this.audioSet = jsonObject.getString("AudioSet");
            this.audioFile = jsonObject.getString("AudioFile");
            this.audio = manager.getAudioAsset(audioSet, audioFile);
            this.title = jsonObject.getString("Title");
            this.artist = jsonObject.getString("Artist");
            this.mapper = jsonObject.getString("Mapper");
            this.difficulty = jsonObject.getDouble("Difficulty");
            this.leadin = jsonObject.getLong("LeadIn");
            JSONArray array = jsonObject.getJSONArray("HitObjects");
            hitObjects = new ArrayList<>(array.length());
            for (int i = 0; i < array.length(); i++) {
                hitObjects.add(new HitObject(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Parsing Beatmap Failed", e);
        }

    }

    public JsonFile getJsonFile() {
        return jsonFile;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public String getName() {
        return name;
    }

    public String getAudioSet() {
        return audioSet;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getMapper() {
        return mapper;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public long getLeadin() {
        return leadin;
    }

    public List<HitObject> getHitObjects() {
        return hitObjects;
    }

    public AudioAsset getAudio() {
        return audio;
    }
}
