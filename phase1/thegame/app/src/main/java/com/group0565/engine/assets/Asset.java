package com.group0565.engine.assets;

import java.io.Closeable;

public class Asset implements Closeable {
    private final String name;
    private final String path;

    public Asset(String name, String path) {
        if (name == null)
            throw new IllegalAssetException("Name of TileSet is missing");
        this.name = name;
        if (path == null)
            throw new IllegalAssetException("Path of TileSet" + name + " is missing");
        this.path = path;
    }

    public void init(){}

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }


    @Override
    public void close(){

    }
}

class IllegalAssetException extends RuntimeException{
    IllegalAssetException(String message) {
        super(message);
    }
}