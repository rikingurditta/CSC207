package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;

public class StatsMenu extends GameObject implements Observable, Observer {
    private static final float BUTTON_SIZE = 75;
    private static final float MARGIN = 75;
    private Button back;
    private boolean exit;

    @Override
    public void init() {
        Bitmap backBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(8, 0);
        this.back = new Button(this.getAbsolutePosition().add(new Vector(MARGIN, MARGIN)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), backBitmap, backBitmap);
        back.registerObserver(this);
        adopt(back);
        super.init();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().theme == Themes.LIGHT)
            canvas.drawRGB(255, 255, 255);
        else if (getGlobalPreferences().theme == Themes.DARK)
            canvas.drawRGB(0, 0, 0);
    }

    @Override
    public void observe(Observable observable) {
        if (observable == back) {
            if (back.isPressed()) {
                exit = true;
                this.notifyObservers();
            }
        }
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
