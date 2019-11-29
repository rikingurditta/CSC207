package com.group0565.engine.render;

import androidx.annotation.NonNull;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.theme.Themes;

import java.util.HashMap;
import java.util.Set;

public class ThemedPaintCan extends PaintCan implements Cloneable{
    private HashMap<Themes, Paint> registry = new HashMap<>();
    private String set;
    private String name;

    public ThemedPaintCan(String set, String name) {
        super((Paint) null);
        this.set = set;
        this.name = name;
    }

    public ThemedPaintCan(ThemedPaintCan paintCan){
        super(paintCan);
        this.set = paintCan.set;
        this.name = paintCan.name;
        this.registry = paintCan.registry;
    }

    public ThemedPaintCan init(GlobalPreferences preferences, GameAssetManager assetManager){
        reloadAssets(assetManager);
        preferences.registerObserver(this::observe);
        return this;
    }

    public void reloadAssets(GameAssetManager manager){
        if (manager == null)
            throw new IllegalStateException("ThemedPaintCan must be initialized before use.");
        registry.clear();
        Set<String> themeNames = manager.getThemeSetNames(set);
        for (String themeName : themeNames){
            registry.put(Themes.valueOf(themeName), manager.getThemeSet(set, themeName).getPaint(name));
        }
    }

    public void observe(Observable observable, ObservationEvent event) {
        if (event.isEvent(GlobalPreferences.THEME_CHANGE)){
            if (event.getPayload() instanceof Themes) {
                Paint paint = registry.get(event.getPayload());
                if (paint != null) this.setPaint(paint);
            }
        }
    }

    @NonNull
    @Override
    public ThemedPaintCan clone() {
        return new ThemedPaintCan(this);
    }
}
