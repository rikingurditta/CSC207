package com.group0565.tsu.enums;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.interfaces.Bitmap;

public enum ButtonBitmap {
    SettingsButton(1, 0), LanguageButton(4, 0), StatsButton(7, 0);

    private static final String SET = "Tsu";
    private static final String BUTTON_SHEET = "Buttons";
    private int tileX, tileY;
    private Bitmap bitmap = null;
    private static boolean initialized = false;

    ButtonBitmap(int tileX, int tileY){
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public static void init(GameAssetManager manager){
        if (!initialized){
            TileSheet sheet = manager.getTileSheet(SET, BUTTON_SHEET);
            for (ButtonBitmap buttonBitmap : ButtonBitmap.values())
                buttonBitmap.bitmap = sheet.getTile(buttonBitmap.tileX, buttonBitmap.tileY);
            initialized = true;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
