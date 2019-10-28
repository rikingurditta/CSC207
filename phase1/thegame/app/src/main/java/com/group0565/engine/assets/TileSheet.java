package com.group0565.engine.assets;

import android.graphics.Bitmap;

import com.group0565.engine.interfaces.LifecycleListener;

public abstract class TileSheet extends Asset implements LifecycleListener {
    private final int tileWidth;
    private final int tileHeight;

    public TileSheet(String name, String path, int tileWidth, int tileHeight) {
        super(name, path);
        if (tileWidth <= 0 || tileHeight <= 0)
            throw new IllegalTileSetException("Dimensions of TileSet " + name + " is invalid");
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public abstract Bitmap getTile(int x, int y);

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}

class IllegalTileSetException extends IllegalAssetException{
    IllegalTileSetException(String message) {
        super(message);
    }
}