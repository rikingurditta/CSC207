package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.GlobalPreferences;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;

public class TsuMenu extends GameObject implements Observer, Observable {
    private static final float TITLE_SIZE = 500;
    private Bitmap title;
    private Button titleButton;
    private boolean started;
    private Paint titlePaint;

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

    }

    public boolean hasStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
