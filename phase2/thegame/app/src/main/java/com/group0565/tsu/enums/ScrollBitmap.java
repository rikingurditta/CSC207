package com.group0565.tsu.enums;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.interfaces.Bitmap;

public enum ScrollBitmap {
    SCROLL_UP(0, 0), SCROLL_UP_DSB(1, 0),
    SCROLL_DOWN(0, 1), SCROLL_DOWN_DSB(1, 1),;

    private static final String SET = "Tsu";
    private static final String BUTTON_SHEET = "Scroll";
    private int tileX, tileY;
    private Bitmap bitmap = null;
    private static boolean initialized = false;

    ScrollBitmap(int tileX, int tileY){
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public static void init(GameAssetManager manager){
        if (!initialized){
            TileSheet sheet = manager.getTileSheet(SET, BUTTON_SHEET);
            for (ScrollBitmap scrollBitmap : ScrollBitmap.values())
                scrollBitmap.bitmap = sheet.getTile(scrollBitmap.tileX, scrollBitmap.tileY);
            initialized = true;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
