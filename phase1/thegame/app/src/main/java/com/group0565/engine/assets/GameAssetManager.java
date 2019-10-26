package com.group0565.engine.assets;

import com.group0565.engine.interfaces.LifecycleListener;

import java.io.Closeable;
import java.util.HashMap;

public abstract class GameAssetManager implements LifecycleListener, Closeable {

    private HashMap<String, HashMap<String, TileSheet>> tileSheetSets = new HashMap<>();
    private HashMap<String, HashMap<String, AudioAsset>> audioSets = new HashMap<>();

    public void init(){
        for (HashMap<String, TileSheet> tileSet : this.tileSheetSets.values())
            for (TileSheet tileSheet : tileSet.values())
                tileSheet.init();
        for (HashMap<String, AudioAsset> audioSet : this.audioSets.values())
            for (AudioAsset audioAsset : audioSet.values())
                audioAsset.init();
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
        HashMap<String, AudioAsset> audiSet = audioSets.get(set);
        if (audiSet != null)
            audiSet.put(audioAsset.getName(), audioAsset);
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

    @Override
    public void close(){
        for (HashMap<String, TileSheet> tileSet : this.tileSheetSets.values())
            for (TileSheet tileSheet : tileSet.values())
                tileSheet.close();
        for (HashMap<String, AudioAsset> audioSet : this.audioSets.values())
            for (AudioAsset audioAsset : audioSet.values())
                audioAsset.close();
    }
}
