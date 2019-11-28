package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.group0565.engine.Achievement;
import com.group0565.engine.gameobjects.AchievementManager;
import com.group0565.math.Vector;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AndroidAchievementManager extends AchievementManager {
    private static final String TAG = "AndroidAchievementManager";

    //Constants for Names of fields of Achievements Json File
    private static final String ACHIEVEMENTS_FOLDER = "achievements/";
    private static final String DISPLAY_NAME = "DisplayName";
    private static final String ICON_NAME = "Icon";
    private static final String SET_NAME = "Set";
    private static final String SHEET_NAME = "Sheet";
    private static final String TILEX_NAME = "Tile X";
    private static final String TILEY_NAME = "Tile Y";
    private static final String DESCRIPTION_NAME = "Description";
    private static final String HIDDEN_NAME = "Hidden";


    /**Default Size of dropdown**/
    private static final Vector SIZE = new Vector(750, 100);


    /**Android's AssetManager used to load Achievements**/
    private AssetManager manager;

    /**
     * Creates a new AndroidAchievementManager
     * @param manager Android's AssetManager used to load Achievements
     */
    public AndroidAchievementManager(AssetManager manager) {
        this.manager = manager;
    }

    /**
     * Load the set of achievements
     * @param set The name of the set
     * @param path The path to the file
     */
    void loadAchievements(String set, String path) {
        try {
            InputStream stream = manager.open(ACHIEVEMENTS_FOLDER + path);
            JsonReader reader = new JsonReader(new InputStreamReader(stream));
            reader.beginObject();
            while (reader.peek() == JsonToken.NAME) {
                String name = reader.nextName();
                String displayName = "";
                String description = "";
                boolean hidden = false;
                String Set = null;
                String Sheet = null;
                int tilex = 0;
                int tiley = 0;
                boolean flag = false;
                reader.beginObject();
                while (reader.peek() == JsonToken.NAME || flag){
                    if (flag && reader.peek() == JsonToken.END_OBJECT) {
                        flag = false;
                        reader.endObject();
                        continue;
                    }
                    String tokenName = reader.nextName();
                    switch (tokenName){
                        case DISPLAY_NAME:
                            displayName = reader.nextString();
                            break;
                        case ICON_NAME:
                            flag = true;
                            reader.beginObject();
                            break;
                        case SET_NAME:
                            Set = reader.nextString();
                            break;
                        case SHEET_NAME:
                            Sheet = reader.nextString();
                            break;
                        case TILEX_NAME:
                            tilex = reader.nextInt();
                            break;
                        case TILEY_NAME:
                            tiley = reader.nextInt();
                            break;
                        case DESCRIPTION_NAME:
                            description = reader.nextString();
                            break;
                        case HIDDEN_NAME:
                            hidden = reader.nextBoolean();
                            break;
                    }
                }
                reader.endObject();
                this.registerAchivement(set, new Achievement(SIZE, name, displayName, description, hidden, Set, Sheet, tilex, tiley));
            }
            reader.endObject();
        } catch (IOException e) {
            Log.e(TAG, "Reading Achievement Failed.", e);
        }
    }
}
