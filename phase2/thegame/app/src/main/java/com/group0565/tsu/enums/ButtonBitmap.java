package com.group0565.tsu.enums;

import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.interfaces.Bitmap;

/**
 * Enum storing bitmaps for Buttons. Makes accessing button bitmaps much simpler
 */
public enum ButtonBitmap {
    SettingsButton(1, 0), LanguageButton(4, 0), StatsButton(7, 0),
    ExitButton(0, 0), LightButton(2, 0), DarkButton(3, 0),
    AddButton(6, 0), SubButton(5, 0), AutoOnButton(12, 0),
    AutoOffButton(13, 0), BackButton(8, 0),
    TimeButton(15, 0), TimeUpButton(16, 0), TimeDownButton(17, 0),
    ScoreButton(18, 0), ScoreUpButton(19, 0), ScoreDownButton(20, 0),
    CheatIcon(21, 0), ResumeButton(10, 0), HomeButton(11, 0),
    PauseButton(9, 0), PrevButton(22, 0), PlayButton(23, 0), NextButton(24, 0),
    ReplayButton(25, 0);

    //The set and tilesheet of Buttons
    private static final String SET = "Tsu";
    private static final String BUTTON_SHEET = "Buttons";

    /**
     * The x, y coordinates of the tile on the tilesheet
     */
    private int tileX, tileY;
    /**
     * The bitmap for this Button
     */
    private Bitmap bitmap = null;
    /**
     * Whether or not we have already initialized
     */
    private static boolean initialized = false;

    /**
     * Initializes coordinates
     * @param tileX The x coordinate of the tile
     * @param tileY The y coordinate of the tile
     */
    ButtonBitmap(int tileX, int tileY){
        this.tileX = tileX;
        this.tileY = tileY;
    }

    /**
     * Initialize bitmaps for buttons
     * @param manager The GameAssetManager with which to load bitmaps from.
     */
    public static void init(GameAssetManager manager){
        if (!initialized){
            //Load Tilesheet
            TileSheet sheet = manager.getTileSheet(SET, BUTTON_SHEET);
            //Load and set bitmaps
            for (ButtonBitmap buttonBitmap : ButtonBitmap.values())
                buttonBitmap.bitmap = sheet.getTile(buttonBitmap.tileX, buttonBitmap.tileY);
            initialized = true;
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
