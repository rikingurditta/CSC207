package com.group0565.engine.assets;

import com.group0565.engine.interfaces.LifecycleListener;

import java.io.Closeable;
import java.util.HashMap;

public abstract class GameAssetManager implements LifecycleListener, Closeable {

    private HashMap<String, HashMap<String, TileSheet>> tileSheetSets = new HashMap<>();
    private HashMap<String, HashMap<String, AudioAsset>> audioSets = new HashMap<>();
    private HashMap<String, HashMap<String, JsonFile>> jsonSets = new HashMap<>();
    private HashMap<String, HashMap<String, LanguagePack>> languagePackSets = new HashMap<>();

    public void init(){
        for (HashMap<String, TileSheet> tileSet : this.tileSheetSets.values())
            for (TileSheet tileSheet : tileSet.values())
                tileSheet.init();
        for (HashMap<String, AudioAsset> audioSet : this.audioSets.values())
            for (AudioAsset audioAsset : audioSet.values())
                audioAsset.init();
        for (HashMap<String, JsonFile> jsonSet : this.jsonSets.values())
            for (JsonFile jsonFile : jsonSet.values())
                jsonFile.init();
        for (HashMap<String, LanguagePack> languagePackSet : this.languagePackSets.values())
            for (LanguagePack languagePack : languagePackSet.values())
                languagePack.init();
    }

    protected void registerAsset(String set, Asset asset, AssetType type) {
        if (type.cls.isInstance(asset))
            switch (type) {
                case TILESHEET:
                    registerTileSheet(set, (TileSheet) asset);
                    break;
                case AUDIO:
                    registerAudioAsset(set, (AudioAsset) asset);
                    break;
                case JSON:
                    registerJsonFile(set, (JsonFile) asset);
                    break;
                case LANGUAGE:
                    registerLanguagePack(set, (LanguagePack) asset);
                    break;
            }
    }

    protected void registerTileSheet(String set, TileSheet sheet){
        if (!tileSheetSets.containsKey(set))
            tileSheetSets.put(set, new HashMap<>());
        HashMap<String, TileSheet> tileSet = tileSheetSets.get(set);
        if (tileSet != null)
            tileSet.put(sheet.getName(), sheet);
    }

    protected void registerAudioAsset(String set, AudioAsset audioAsset){
        if (!audioSets.containsKey(set))
            audioSets.put(set, new HashMap<>());
        HashMap<String, AudioAsset> audioSet = audioSets.get(set);
        if (audioSet != null)
            audioSet.put(audioAsset.getName(), audioAsset);
    }

    protected void registerJsonFile(String set, JsonFile jsonFile) {
        if (!jsonSets.containsKey(set))
            jsonSets.put(set, new HashMap<>());
        HashMap<String, JsonFile> jsonSet = jsonSets.get(set);
        if (jsonSet != null)
            jsonSet.put(jsonFile.getName(), jsonFile);
    }

    protected void registerLanguagePack(String set, LanguagePack languagePack) {
        if (!languagePackSets.containsKey(set))
            languagePackSets.put(set, new HashMap<>());
        HashMap<String, LanguagePack> languagePackSet = languagePackSets.get(set);
        if (languagePackSet != null)
            languagePackSet.put(languagePack.getName(), languagePack);
    }

    public TileSheet getTileSheet(String set, String name){
        HashMap<String, TileSheet> tileSet = tileSheetSets.get(set);
        if (tileSet != null)
            return tileSet.get(name);
        return null;
    }

    public AudioAsset getAudioAsset(String set, String name){
        HashMap<String, AudioAsset> audioSet = this.audioSets.get(set);
        if (audioSet != null)
            return audioSet.get(name);
        return null;
    }

    public JsonFile getJsonFile(String set, String name) {
        HashMap<String, JsonFile> jsonSet = this.jsonSets.get(set);
        if (jsonSet != null)
            return jsonSet.get(name);
        return null;
    }

    public LanguagePack getLanguagePack(String set, String name) {
        HashMap<String, LanguagePack> languagePackSet = this.languagePackSets.get(set);
        if (languagePackSet != null)
            return languagePackSet.get(name);
        return null;
    }


    @Override
    public void close(){
        for (HashMap<String, TileSheet> tileSet : this.tileSheetSets.values())
            for (TileSheet tileSheet : tileSet.values())
                tileSheet.close();
        for (HashMap<String, AudioAsset> audioSet : this.audioSets.values())
            for (AudioAsset audioAsset : audioSet.values())
                audioAsset.close();
        for (HashMap<String, JsonFile> jsonSet : this.jsonSets.values())
            for (JsonFile jsonFile : jsonSet.values())
                jsonFile.close();
        for (HashMap<String, LanguagePack> languagePackSet : this.languagePackSets.values())
            for (LanguagePack languagePack : languagePackSet.values())
                languagePack.close();
    }
}