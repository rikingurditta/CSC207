package com.group0565.tsu.game;

import android.graphics.RectF;

import com.group0565.engine.assets.TileSheet;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.Align;

public class NumberRenderer extends GameObject {
    private int number;
    private float height;
    private TileSheet tileSheet;
    private Align align;
    private Vector margin;

    public NumberRenderer(float height, Align align, Vector margin) {
        this.height = height;
        this.align = align;
        this.margin = margin;
    }

    @Override
    public void init() {
        super.init();
        this.tileSheet = getEngine().getGameAssetManager().getTileSheet("Tsu", "Numbers");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        String str = String.valueOf(number);
        float left;
        if (align == Align.LEFT)
            left = this.margin.getX();
        else if (align == Align.CENTER)
            left = (canvas.getWidth() - height * str.length()) / 2 + this.margin.getX();
        else
            left = canvas.getWidth() - height * str.length() - this.margin.getX();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c))
                continue;
            int n = char2int(c);
            canvas.drawBitmap(tileSheet.getTile(n, 0), null,
                    new RectF(left, this.margin.getY(), left + height,
                            this.margin.getY() + height));
            left += height * 0.8;
        }
    }

    private int char2int(char c) {
        return c - '0';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
