package com.group0565.tsu.game;

import android.util.Log;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.JsonFile;
import com.group0565.engine.interfaces.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AndroidBeatmap extends Beatmap {
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

  public AndroidBeatmap(String set, String name, GameAssetManager manager) {
    super();
    JsonFile jsonFile = manager.getJsonFile(set, name);
    JSONObject jsonObject = jsonFile.getJsonObject();
    try {
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
      JSONArray array = jsonObject.getJSONArray(JsonHitObjectsName);
      List<HitObject> hitObjects = new ArrayList<>(array.length());
      for (int i = 0; i < array.length(); i++) {
        hitObjects.add(new HitObject(array.getJSONObject(i)));
      }
      this.setHitObjects(hitObjects);
    } catch (JSONException e) {
      Log.e(TAG, "Parsing AndroidBeatmap Failed", e);
    }
  }
}
