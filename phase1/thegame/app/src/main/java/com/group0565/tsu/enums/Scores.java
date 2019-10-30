package com.group0565.tsu.enums;

import android.graphics.Bitmap;
import android.graphics.Paint;

public enum Scores {
    SU(255, 0, 0), S300(29, 255, 0), S150(208, 255, 0), S50(255, 170, 0), S0(128, 128, 128);
    private Paint paint;
    private Bitmap bitmap;

    Scores(int r, int g, int b) {
        this.paint = new Paint();
        paint.setARGB(255, r, g, b);
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
}