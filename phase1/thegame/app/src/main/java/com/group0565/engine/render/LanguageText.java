package com.group0565.engine.render;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;

public class LanguageText implements Observer {
    private GlobalPreferences preferences;
    private GameAssetManager manager;
    private String set;
    private String token;
    private String current;

    public LanguageText(String string){
        this.current = string;
    }

    public LanguageText(GlobalPreferences preferences, GameAssetManager manager, String set, String token){
        this.manager = manager;
        this.set = set;
        this.token = token;
        this.preferences = preferences;
        this.preferences.registerObserver(this);
    }

    public String getString(){
        return current;
    }

    @Override
    public void observe(Observable observable) {
        if (observable == preferences){
            this.current = manager.getLanguagePack(set, preferences.getLanguage()).getToken(token);
        }
    }
}
