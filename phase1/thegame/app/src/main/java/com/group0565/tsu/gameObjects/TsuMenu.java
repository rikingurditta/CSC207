package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;


public class TsuMenu extends GameObject implements Observer, Observable {
    private static final float TITLE_SIZE = 500;
    private static final float SETTINGS_SIZE = 900;
    private static final float BUTTON_SIZE = 75;
    private static final float MARGIN = 75;
    private Bitmap title;
    private Button titleButton;
    private boolean started = false;
    private Paint titlePaint;
    private SettingsMenu settingsMenu;
    private Button settingsButton;
    private Button languageButton;
    private Button statsButton;
    private double difficulty;
    private boolean stats = false;

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
        this.settingsMenu.setZ(1);
        this.settingsMenu.setEnable(false);
        this.settingsMenu.registerObserver(this);
        this.adopt(settingsMenu);

        Bitmap settingsButtonBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(1, 0);
        this.settingsButton = new Button(new Vector(cx - BUTTON_SIZE - MARGIN, cy - BUTTON_SIZE - MARGIN),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), settingsButtonBitmap, settingsButtonBitmap);
        settingsButton.registerObserver(this);
        adopt(settingsButton);

        Bitmap languageButtonBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(4, 0);
        this.languageButton = new Button(new Vector(cx - BUTTON_SIZE - MARGIN, MARGIN),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), languageButtonBitmap, languageButtonBitmap);
        languageButton.registerObserver(this);
        adopt(languageButton);

        Bitmap statsBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(7, 0);
        this.statsButton = new Button(new Vector(MARGIN, cy - BUTTON_SIZE - MARGIN),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), statsBitmap, statsBitmap);
        statsButton.registerObserver(this);
        adopt(statsButton);
        super.init();
    }

    @Override
    public void observe(Observable observable) {
        if (observable == titleButton) {
            if (titleButton.isPressed()) {
                started = true;
                this.notifyObservers();
            }
        } else if (observable == settingsButton) {
            if (settingsButton.isPressed()) {
                settingsMenu.setEnable(true);
            }
        } else if (observable == languageButton) {
            if (languageButton.isPressed()) {
                if (getGlobalPreferences().language.equals("en")) {
                    getGlobalPreferences().language = "fr";
                } else if (getGlobalPreferences().language.equals("fr")) {
                    getGlobalPreferences().language = "en";
                }
            }
        } else if (observable == statsButton) {
            if (statsButton.isPressed()) {
                stats = true;
                this.notifyObservers();
            }
        } else if (observable == settingsMenu) {
            if (!settingsMenu.isEnable()) {
                this.difficulty = settingsMenu.getDifficulty();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().theme == Themes.LIGHT)
            canvas.drawRGB(255, 255, 255);
        else if (getGlobalPreferences().theme == Themes.DARK)
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

    public boolean isStats() {
        return stats;
    }

    public void setStats(boolean stats) {
        this.stats = stats;
    }

    public double getDifficulty() {
        return difficulty;
    }
}
