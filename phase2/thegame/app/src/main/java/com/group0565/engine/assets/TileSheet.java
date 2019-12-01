package com.group0565.engine.assets;

import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.LifecycleListener;

public abstract class TileSheet extends Asset implements LifecycleListener {
    private int tileWidth;
    private int tileHeight;

    public TileSheet(String name, String path, int tileWidth, int tileHeight) {
        super(name, path);
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public abstract Bitmap getTile(int x, int y);

    /**
     * Getter for tileWidth
     *
     * @return tileWidth
     */
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * Setter for tileWidth
     *
     * @param tileWidth The new value for tileWidth
     */
    protected void setTileWidth(int tileWidth) {
        this.tileWidth = tileWidth;
    }

    /**
     * Getter for tileHeight
     *
     * @return tileHeight
     */
    public int getTileHeight() {
        return tileHeight;
    }

    /**
     * Setter for tileHeight
     *
     * @param tileHeight The new value for tileHeight
     */
    protected void setTileHeight(int tileHeight) {
        this.tileHeight = tileHeight;
    }

    protected class IllegalTileSetException extends IllegalAssetException{
        public IllegalTileSetException(String message) {
            super(message);
        }
    }
}