package com.group0565.racer.menus;

import com.group0565.engine.android.AndroidPaint;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Bitmap;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;

public class RacerPauseMenu extends GameObject implements Observer, Observable {
    private static final float BUTTON_SIZE = 150;
    private Vector size;
    private Paint rim;
    private Paint center;
    private Button resumeButton;
    private Button exitButton;
    private boolean resume;
    private boolean exit;


    public RacerPauseMenu(Vector position, Vector size) {
        super(position);
        this.size = size;
    }

    @Override
    public void init() {
        super.init();
        this.rim = new AndroidPaint();
        this.rim.setARGB(255, 255, 0, 255);
        this.center = new AndroidPaint();
        this.center.setARGB(255, 0, 0, 0);

        float cx = getAbsolutePosition().getX();
        float cy = getAbsolutePosition().getY();
        float w = size.getX();
        float h = size.getY();

        Bitmap resumeBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(10, 0);
        this.resumeButton = new Button(new Vector(cx + (w - 2 * BUTTON_SIZE) / 3, cy + (h - BUTTON_SIZE) / 2),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), resumeBitmap, resumeBitmap);
        resumeButton.registerObserver(this);
        adopt(resumeButton);

        Bitmap exitBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(11, 0);
        this.exitButton = new Button(new Vector(cx + 2 * (w - 2 * BUTTON_SIZE) / 3 + BUTTON_SIZE, cy + (h - BUTTON_SIZE) / 2),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), exitBitmap, exitBitmap);
        exitButton.registerObserver(this);
        adopt(exitButton);
    }

    @Override
    public boolean processInput(InputEvent event) {
        if (!isEnable())
            return false;
        if (!super.processInput(event)) {
            float x = getAbsolutePosition().getX();
            float y = getAbsolutePosition().getY();
            float px = event.getPos().getX();
            float py = event.getPos().getY();
            float w = size.getX();
            float h = size.getY();
            if (x <= px && px <= x + w && y <= py && py <= y + h) {
                captureEvent(event);
                return true;
            }
        } else
            return true;
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float x = getAbsolutePosition().getX();
        float y = getAbsolutePosition().getY();
        float w = size.getX();
        float h = size.getY();
        canvas.drawRoundRect(x, y, x + w, y + h, 50, 50, rim);
        if (getGlobalPreferences().getTheme() == Themes.LIGHT)
            center.setARGB(255, 255, 255, 255);
        else if (getGlobalPreferences().getTheme() == Themes.DARK)
            center.setARGB(255, 0, 0, 0);
        canvas.drawRoundRect(x + 10, y + 10, x + w - 10, y + h - 10, 50, 50, center);

    }

    @Override
    public void observe(Observable observable) {
        if (observable == resumeButton) {
            if (resumeButton.isPressed()) {
                this.resume = true;
                this.notifyObservers();
            }
        }
        if (observable == exitButton) {
            if (exitButton.isPressed()) {
                this.exit = true;
                this.notifyObservers();
            }
        }
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
