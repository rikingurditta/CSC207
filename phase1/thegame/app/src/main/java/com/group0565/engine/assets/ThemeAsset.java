package com.group0565.engine.assets;


import com.group0565.engine.interfaces.Paint;

public abstract class ThemeAsset extends Asset{
    public ThemeAsset(String name, String path) {
        super(name, path);
    }

    public abstract Paint getPaint(String name);

    protected class IllegalThemeAssetException extends IllegalAssetException{
        public IllegalThemeAssetException(String message) {
            super(message);
        }
    }
}