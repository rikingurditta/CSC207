package com.group0565.engine.android.assets;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.group0565.engine.assets.TileSheet;

import java.io.IOException;

public class AndroidTileSheet extends TileSheet {
    public static final String TILE_FOLDER = "tilesets/";
    private static final String TAG = "AndroidTileSheet";
    private Bitmap bitmap = null;
    private Bitmap[][] submap;
    private int width = -1, height = -1;

    private AssetManager assetManager;

    public AndroidTileSheet(
            String name, String path, int tileWidth, int tileHeight, AssetManager assetManager) {
        super(name, path, tileWidth, tileHeight);
        this.assetManager = assetManager;
    }

    @Override
    public void init() {
        try {
            bitmap = BitmapFactory.decodeStream(assetManager.open(TILE_FOLDER + this.getPath()));
            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();
            this.submap = new Bitmap[width / getTileWidth()][height / getTileHeight()];
        } catch (IOException e) {
            Log.e(
                    TAG, "Tilesheet " + this.getName() + " at path " + this.getPath() + " is not found.", e);
        }
    }

    private Bitmap createSubMap(int x, int y) {
        return Bitmap.createBitmap(
                bitmap, x * getTileWidth(), y * getTileHeight(), getTileWidth(), getTileHeight());
    }

    public Bitmap getTile(int x, int y) {
        if (submap[x][y] == null) submap[x][y] = createSubMap(x, y);
        return submap[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
