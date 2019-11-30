package com.group0565.engine.render;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.theme.Themes;

import java.util.HashMap;
import java.util.Set;

/** A PaintCan that is Theme-aware */
public class ThemedPaintCan extends PaintCan {
  /** A map of theme to their paint */
  private HashMap<Themes, Paint> registry = new HashMap<>();
  /** The assets set */
  private String set;
  /** The name of the asset */
  private String name;

  /**
   * Create a new ThemedPaintCan by setting the assets
   *
   * @param set The assets set
   * @param name The asset key
   */
  public ThemedPaintCan(String set, String name) {
    super(null);
    this.set = set;
    this.name = name;
  }

  /**
   * Initialize a ThemedPaintCan
   *
   * @param preferences The target preferences
   * @param assetManager The target asset manager
   * @return This instance of of ThemedPaintCan with the assets loaded
   */
  public ThemedPaintCan init(GlobalPreferences preferences, GameAssetManager assetManager) {
    reloadAssets(assetManager);
    preferences.registerObserver(this::observe);
    return this;
  }

  /**
   * Reload the assets from the given AssetManager
   *
   * @param manager The target GameAssetManager
   */
  private void reloadAssets(GameAssetManager manager) {
    if (manager == null)
      throw new IllegalStateException("ThemedPaintCan must be initialized before use.");
    registry.clear();
    Set<String> themeNames = manager.getThemeSetNames(set);
    for (String themeName : themeNames) {
      registry.put(Themes.valueOf(themeName), manager.getThemeSet(set, themeName).getPaint(name));
    }
  }

  /**
   * Observe an event happening in Observable
   *
   * @param observable The observable object
   * @param event The observed event carrying the payload
   */
  public void observe(Observable observable, ObservationEvent event) {
    if (event.getMsg().equals(GlobalPreferences.THEME_CHANGE)) {
      if (event.getPayload() instanceof Themes) {
        Paint paint = registry.get(event.getPayload());
        if (paint != null) this.setPaint(paint);
      }
    }
  }
}
