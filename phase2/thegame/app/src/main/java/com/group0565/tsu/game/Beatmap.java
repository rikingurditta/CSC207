package com.group0565.tsu.game;

import android.util.Log;

import com.group0565.engine.assets.AudioAsset;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.JsonFile;
import com.group0565.engine.interfaces.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A set of information related to a playable song.
 *
 * Contains information such as Name, Audio, and Background
 */
public class Beatmap {
    //Tag used for error reporting
    private static final String TAG = "AndroidBeatmap";
    //Constants For Parsing Beatmap Json
    private static final String JsonName = "Name";
    private static final String JsonAudioSetName = "AudioSet";
    private static final String JsonAudioFileName = "AudioFile";
    private static final String JsonTitleName = "Title";
    private static final String JsonArtistName = "Artist";
    private static final String JsonCreatorName = "Creator";
    private static final String JsonDifficultyName = "Difficulty";
    private static final String JsonLeadInName = "LeadIn";
    private static final String JsonHitObjectsName = "HitObjects";
    private static final String JsonNoteWidthName = "NoteWidth";
    private static final String JsonBackgroundName = "Background";
    private static final String JsonBackgroundSetName = "Set";
    private static final String JsonBackgroundSheetName = "Name";
    private static final String JsonBackgroundTileXName = "tileX";
    private static final String JsonBackgroundTileYName = "tileY";

    //Beatmap Information
    private String name;
    //Audio information
    private String audioSet;
    private String audioFile;
    private AudioAsset audio;
    private String title;
    private String artist;
    private String creator;
    private double difficulty;
    //Length of time before the start of the song
    private long leadin;
    //The width of a note in how much of the total width
    private float noteWidth;
    private Bitmap background;
    private List<HitObject> hitObjects;

    //HitObject in Json
    private JSONArray hitObjectsJson;

    /**
     * Creates a Beatmap
     * @param set The set the Json file is
     * @param name The name of the Json file
     * @param manager The GameAssetManager to load the json from
     */
    public Beatmap(String set, String name, GameAssetManager manager) {
        super();
        JsonFile jsonFile = manager.getJsonFile(set, name);
        JSONObject jsonObject = jsonFile.getJsonObject();
        try {
            //Load Beatmap from the Json file
            this.setName(jsonObject.getString(JsonName));
            this.setAudioSet(jsonObject.getString(JsonAudioSetName));
            this.setAudioFile(jsonObject.getString(JsonAudioFileName));
            this.setAudio(manager.getAudioAsset(getAudioSet(), getAudioFile()));
            this.setTitle(jsonObject.getString(JsonTitleName));
            this.setArtist(jsonObject.getString(JsonArtistName));
            this.setCreator(jsonObject.getString(JsonCreatorName));
            this.setDifficulty(jsonObject.getDouble(JsonDifficultyName));
            this.setLeadin(jsonObject.getLong(JsonLeadInName));
            this.setNoteWidth((float) jsonObject.getDouble(JsonNoteWidthName));
            JSONObject background = jsonObject.getJSONObject(JsonBackgroundName);
            String bgSet = background.getString(JsonBackgroundSetName);
            String sheet = background.getString(JsonBackgroundSheetName);
            int tileX = background.getInt(JsonBackgroundTileXName);
            int tileY = background.getInt(JsonBackgroundTileYName);
            Bitmap backgroundBitmap = manager.getTileSheet(bgSet, sheet).getTile(tileX, tileY);
            this.setBackground(backgroundBitmap);
            hitObjectsJson = jsonObject.getJSONArray(JsonHitObjectsName);
        } catch (JSONException e) {
            Log.e(TAG, "Parsing AndroidBeatmap Failed", e);
        }
    }

    /***
     * Loads HitObjects from Json. Allows lazy loading.
     */
    public void initBeatmap() {
        try {
            List<HitObject> hitObjects = new ArrayList<>(hitObjectsJson.length());
            for (int i = 0; i < hitObjectsJson.length(); i++) {
                hitObjects.add(new HitObject(hitObjectsJson.getJSONObject(i)));
            }
            this.setHitObjects(hitObjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Getter for name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for audioSet
     *
     * @return audioSet
     */
    public String getAudioSet() {
        return audioSet;
    }

    /**
     * Getter for audioFile
     *
     * @return audioFile
     */
    public String getAudioFile() {
        return audioFile;
    }

    /**
     * Getter for audio
     *
     * @return audio
     */
    public AudioAsset getAudio() {
        return audio;
    }

    /**
     * Getter for title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for artist
     *
     * @return artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Getter for creator
     *
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Getter for difficulty
     *
     * @return difficulty
     */
    public double getDifficulty() {
        return difficulty;
    }

    /**
     * Getter for leadin
     *
     * @return leadin
     */
    public long getLeadin() {
        return leadin;
    }

    /**
     * Getter for hitObjects
     *
     * @return hitObjects
     */
    public List<HitObject> getHitObjects() {
        return hitObjects;
    }

    /**
     * Setter for name
     *
     * @param name The new value for name
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for audioSet
     *
     * @param audioSet The new value for audioSet
     */
    protected void setAudioSet(String audioSet) {
        this.audioSet = audioSet;
    }

    /**
     * Setter for audioFile
     *
     * @param audioFile The new value for audioFile
     */
    protected void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    /**
     * Setter for audio
     *
     * @param audio The new value for audio
     */
    protected void setAudio(AudioAsset audio) {
        this.audio = audio;
    }

    /**
     * Setter for title
     *
     * @param title The new value for title
     */
    protected void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter for artist
     *
     * @param artist The new value for artist
     */
    protected void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Setter for creator
     *
     * @param creator The new value for creator
     */
    protected void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Setter for difficulty
     *
     * @param difficulty The new value for difficulty
     */
    protected void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Setter for leadin
     *
     * @param leadin The new value for leadin
     */
    protected void setLeadin(long leadin) {
        this.leadin = leadin;
    }

    /**
     * Setter for hitObjects
     *
     * @param hitObjects The new value for hitObjects
     */
    protected void setHitObjects(List<HitObject> hitObjects) {
        this.hitObjects = hitObjects;
    }

    /**
     * Getter for noteWidth
     *
     * @return noteWidth
     */
    public float getNoteWidth() {
        return noteWidth;
    }

    /**
     * Setter for noteWidth
     *
     * @param noteWidth The new value for noteWidth
     */
    protected void setNoteWidth(float noteWidth) {
        this.noteWidth = noteWidth;
    }

    /**
     * Getter for background
     *
     * @return background
     */
    public Bitmap getBackground() {
        return background;
    }

    /**
     * Setter for background
     *
     * @param background The new value for background
     */
    protected void setBackground(Bitmap background) {
        this.background = background;
    }
}
