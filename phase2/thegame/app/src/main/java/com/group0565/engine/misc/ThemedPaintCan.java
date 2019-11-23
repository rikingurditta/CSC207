package com.group0565.engine.misc;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.theme.Themes;

import java.util.HashMap;
import java.util.Set;

public class ThemedPaintCan extends PaintCan implements Observer {
    private HashMap<Themes, Paint> registry = new HashMap<>();
    private GameAssetManager manager = null;
    private String set;
    private String name;
    private GlobalPreferences preferences = null;

    public ThemedPaintCan(String set, String name) {
        super(null);
    }

    public void init(GlobalPreferences preferences, GameAssetManager assetManager){
        this.manager = assetManager;
        this.preferences = preferences;
        reloadAssets();
    }

    public void reloadAssets(){
        if (manager == null)
            throw new IllegalStateException("ThemedPaintCan must be initialized before use.");
        registry.clear();
        Set<String> themeNames = manager.getThemeSetNames(set);
        for (String themeName : themeNames){
            registry.put(Themes.valueOf(name), manager.getThemeSet(set, themeName).getPaint(name));
        }
        this.setPaint(registry.get(preferences.getTheme()));
    }


    @Override
    public void observe(Observable observable) {
        if (observable == preferences){
            this.setPaint(registry.get(preferences.getTheme()));
        }
    }
}
