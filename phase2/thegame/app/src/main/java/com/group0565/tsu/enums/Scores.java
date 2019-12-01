package com.group0565.tsu.enums;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.assets.GameAssetManager;
import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Paint;

public enum Scores {
    SU(0, 255, 0, 0), S300(300, 29, 255, 0), S150(150, 208, 255, 0), S50(50, 255, 170, 0), S0(0, 128, 128, 128);
    private Paint paint;
    private int score;

    private static final String SET = "Tsu";
    private static final String SCORES_SHEET = "Scores";
    private int tileX, tileY;
    private Bitmap bitmap = null;
    private static boolean initialized = false;

    Scores(int score, int r, int g, int b, int tileX, int tileY) {
        this.paint = new AndroidPaint();
        paint.setARGB(255, r, g, b);
        this.score = score;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public static void init(GameAssetManager manager){
        if (!initialized){
            TileSheet sheet = manager.getTileSheet(SET, SCORES_SHEET);
            for (Scores score : Scores.values())
                score.bitmap = sheet.getTile(score.tileX, score.tileY);
            initialized = true;
        }
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getScore() {
        return score;
    }
}