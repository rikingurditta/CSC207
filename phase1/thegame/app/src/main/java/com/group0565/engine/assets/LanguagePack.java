package com.group0565.engine.assets;

import java.util.HashMap;

public class LanguagePack extends Asset {
    private String defaultString;
    private HashMap<String, String> tokens = new HashMap<>();

    public LanguagePack(String name, String path, String defaultString) {
        super(name, path);
        this.defaultString = defaultString;
    }

    public String registerToken(String name, String token) {
        return tokens.put(name, token);
    }

    public String getToken(String name) {
        return tokens.getOrDefault(name, defaultString);
    }

    public String getDefaultString() {
        return defaultString;
    }

    public void setDefaultString(String defaultString) {
        this.defaultString = defaultString;
    }
}
