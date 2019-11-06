package com.group0565.tsu.enums;

import android.graphics.Bitmap;
import android.graphics.Paint;

public enum Scores {
    SU(0, 255, 0, 0), S300(300, 29, 255, 0), S150(150, 208, 255, 0), S50(50, 255, 170, 0), S0(0, 128, 128, 128);
    private Paint paint;
    private Bitmap bitmap;
    private int score;

    Scores(int score, int r, int g, int b) {
        this.paint = new Paint();
        paint.setARGB(255, r, g, b);
        this.score = score;
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