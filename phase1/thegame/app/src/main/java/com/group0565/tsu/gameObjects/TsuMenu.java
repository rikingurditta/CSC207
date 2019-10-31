package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class TsuMenu extends GameObject implements Observer, Observable {
    private static final float TITLE_SIZE = 500;
    private static final float SETTINGS_SIZE = 900;
    private Bitmap title;
    private Button titleButton;
    private boolean started;
    private Paint titlePaint;
    private SettingsMenu settingsMenu;

    @Override
    public void init() {
        this.title = getEngine().getGameAssetManager().getTileSheet("Tsu", "Title").getTile(0, 0);
        float cx = getEngine().getSize().getX();
        float cy = getEngine().getSize().getY();
        this.titleButton = new Button(new Vector((cx - TITLE_SIZE) / 2, (cy - TITLE_SIZE) / 2), new Vector(TITLE_SIZE, TITLE_SIZE),
                title, title);
        this.adopt(titleButton);
        titleButton.registerObserver(this);
        this.titlePaint = new Paint();
        this.titlePaint.setARGB(255, 255, 0, 0);
        this.titlePaint.setTextSize(50);

        this.settingsMenu = new SettingsMenu(new Vector((cx - SETTINGS_SIZE) / 2, (cy - SETTINGS_SIZE) / 2),
                new Vector(SETTINGS_SIZE, SETTINGS_SIZE));
        this.adopt(settingsMenu);
        super.init();
    }

    @Override
    public void observe(Observable observable) {
        if (observable == titleButton) {
            if (titleButton.isPressed()) {
                started = true;
                this.notifyObservers();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().theme == GlobalPreferences.Theme.LIGHT)
            canvas.drawRGB(255, 255, 255);
        else if (getGlobalPreferences().theme == GlobalPreferences.Theme.DARK)
            canvas.drawRGB(0, 0, 0);
        String text = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("StartButton");
        Rect rect = new Rect();
        this.titlePaint.getTextBounds(text, 0, text.length(), rect);
        float tx = (canvas.getWidth() - rect.width()) / 2;
        float ty = titleButton.getAbsolutePosition().getY() + 3 * TITLE_SIZE / 4;
        canvas.drawText(text, tx, ty, this.titlePaint);
    }

    public boolean hasStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
