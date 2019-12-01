package com.group0565.tsu.enums;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.interfaces.Bitmap;

public enum NumberTiles {
    N0(0, 0), N1(1, 0), N2(2, 0), N3(3, 0),
    N4(4, 0), N5(5, 0), N6(6, 0), N7(7, 0),
    N8(8, 0), N9(9, 0);

    private static final String SET = "Tsu";
    private static final String NUMBER_SHEET = "Numbers";
    private int tileX, tileY;
    private Bitmap bitmap = null;
    private static boolean initialized = false;

    NumberTiles(int tileX, int tileY){
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public static void init(GameAssetManager manager){
        if (!initialized){
            TileSheet sheet = manager.getTileSheet(SET, NUMBER_SHEET);
            for (NumberTiles numberTile : NumberTiles.values())
                numberTile.bitmap = sheet.getTile(numberTile.tileX, numberTile.tileY);
            initialized = true;
        }
    }

    public static NumberTiles char2num(char c){
        switch (c){
            case '0': return N0;
            case '1': return N1;
            case '2': return N2;
            case '3': return N3;
            case '4': return N4;
            case '5': return N5;
            case '6': return N6;
            case '7': return N7;
            case '8': return N8;
            case '9': return N9;
            default : return null;
        }
    }

    /**
     * Getter for bitmap
     *
     * @return bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }
}
