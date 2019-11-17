package com.group0565.tsu.gameObjects;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameObject;
import com.group0565.engine.gameobjects.InputEvent;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;

public class SettingsMenu extends GameObject implements Observer, Observable {
    private static final float BUTTON_SIZE = 75;
    private static final float LEFT_MARGIN = 75;
    private static final float VOLUME_Y = 200;
    private static final float DIFFICULTY_Y = 300;
    private static final float CHEAT_Y = 400;
    private static final float AUTO_Y = 500;
    private Vector size;
    private Paint rim;
    private Paint center;
    private Paint textPaint;
    private Button exit;
    private Button light, dark;
    private Button volumeSubButton;
    private Button volumeAddButton;
    private Button difficultySubButton;
    private Button difficultyAddButton;
    private Button autoOn, autoOff;
    private int volume = 0;
    private int difficulty = 5;
    private boolean auto = false;


    public SettingsMenu(Vector position, Vector size) {
        super(position);
        this.size = size;
    }

    @Override
    public void init() {
        super.init();
        this.rim = new Paint();
        this.rim.setARGB(255, 255, 0, 255);
        this.center = new Paint();
        this.center.setARGB(255, 0, 0, 0);
        this.textPaint = new Paint();
        this.textPaint.setARGB(255, 255, 0, 0);
        this.textPaint.setTextSize(50);

        float cx = this.getAbsolutePosition().getX();
        float cy = this.getAbsolutePosition().getY();

        Bitmap exitBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(0, 0);
        this.exit = new Button(this.getAbsolutePosition(),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), exitBitmap, exitBitmap);
        exit.registerObserver(this);
        adopt(exit);

        Bitmap lightBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(2, 0);
        this.light = new Button(this.getAbsolutePosition().add(new Vector(75, 75)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), lightBitmap, lightBitmap);
        light.registerObserver(this);
        light.setEnable(getGlobalPreferences().theme == Themes.LIGHT);
        adopt(light);

        Bitmap darkBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(3, 0);
        this.dark = new Button(this.getAbsolutePosition().add(new Vector(75, 75)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), darkBitmap, darkBitmap);
        dark.registerObserver(this);
        dark.setEnable(getGlobalPreferences().theme == Themes.DARK);
        adopt(dark);


        Bitmap subButtonBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(5, 0);
        Bitmap addButtonBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(6, 0);

        this.volumeAddButton = new Button(new Vector(cx + LEFT_MARGIN, cy + VOLUME_Y),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), addButtonBitmap, addButtonBitmap);
        volumeAddButton.registerObserver(this);
        adopt(volumeAddButton);

        this.volumeSubButton = new Button(new Vector(cx + LEFT_MARGIN, cy + VOLUME_Y),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), subButtonBitmap, subButtonBitmap);
        volumeSubButton.registerObserver(this);
        adopt(volumeSubButton);

        this.volume = (int) (getGlobalPreferences().volume * 10);


        this.difficultyAddButton = new Button(new Vector(cx + LEFT_MARGIN, cy + DIFFICULTY_Y),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), addButtonBitmap, addButtonBitmap);
        difficultyAddButton.registerObserver(this);
        adopt(difficultyAddButton);

        this.difficultySubButton = new Button(new Vector(cx + LEFT_MARGIN, cy + DIFFICULTY_Y),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), subButtonBitmap, subButtonBitmap);
        difficultySubButton.registerObserver(this);
        adopt(difficultySubButton);

        Bitmap autoOnBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(12, 0);
        this.autoOn = new Button(this.getAbsolutePosition().add(new Vector(75, 75)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), autoOnBitmap, autoOnBitmap);
        autoOn.registerObserver(this);
        autoOn.setEnable(auto);
        adopt(autoOn);

        Bitmap autoOffBitmap = getEngine().getGameAssetManager().getTileSheet("Tsu", "Buttons").getTile(13, 0);
        this.autoOff = new Button(this.getAbsolutePosition().add(new Vector(75, 75)),
                new Vector(BUTTON_SIZE, BUTTON_SIZE), autoOffBitmap, autoOffBitmap);
        autoOff.registerObserver(this);
        autoOff.setEnable(!auto);
        adopt(autoOff);
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
        if (getGlobalPreferences().theme == Themes.LIGHT)
            center.setARGB(255, 255, 255, 255);
        else if (getGlobalPreferences().theme == Themes.DARK)
            center.setARGB(255, 0, 0, 0);
        canvas.drawRoundRect(x + 10, y + 10, x + w - 10, y + h - 10, 50, 50, center);
        {
            String volume = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Volume");
            Rect volumeRect = new Rect();
            this.textPaint.getTextBounds(volume, 0, volume.length(), volumeRect);
            canvas.drawText(volume, getAbsolutePosition().getX() + LEFT_MARGIN, getAbsolutePosition().getY() + VOLUME_Y, textPaint);
            float vx1 = LEFT_MARGIN + volumeRect.width() + 20;
            this.volumeSubButton.setRelativePosition(new Vector(vx1, VOLUME_Y - 50));
            float vx2 = vx1 + BUTTON_SIZE + 20;

            String volnum = String.valueOf(this.volume);
            Rect volnumRect = new Rect();
            this.textPaint.getTextBounds(volnum, 0, volnum.length(), volnumRect);
            canvas.drawText(volnum, getAbsolutePosition().getX() + vx2, getAbsolutePosition().getY() + VOLUME_Y, textPaint);

            float vx3 = vx2 + volnumRect.width() + 20;
            this.volumeAddButton.setRelativePosition(new Vector(vx3, VOLUME_Y - 50));
        }
        {
            String difficulty = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Difficulty");
            Rect difficultyRect = new Rect();
            this.textPaint.getTextBounds(difficulty, 0, difficulty.length(), difficultyRect);
            canvas.drawText(difficulty, getAbsolutePosition().getX() + LEFT_MARGIN, getAbsolutePosition().getY() + DIFFICULTY_Y, textPaint);
            float vx1 = LEFT_MARGIN + difficultyRect.width() + 20;
            this.difficultySubButton.setRelativePosition(new Vector(vx1, DIFFICULTY_Y - 50));
            float vx2 = vx1 + BUTTON_SIZE + 20;

            String diffnum = String.valueOf(this.difficulty);
            Rect diffnumRect = new Rect();
            this.textPaint.getTextBounds(diffnum, 0, diffnum.length(), diffnumRect);
            canvas.drawText(diffnum, getAbsolutePosition().getX() + vx2, getAbsolutePosition().getY() + DIFFICULTY_Y, textPaint);

            float vx3 = vx2 + diffnumRect.width() + 20;
            this.difficultyAddButton.setRelativePosition(new Vector(vx3, DIFFICULTY_Y - 50));
        }
        {
            String cheats = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("Cheats");
            Rect cheatsRect = new Rect();
            this.textPaint.getTextBounds(cheats, 0, cheats.length(), cheatsRect);
            canvas.drawText(cheats, getAbsolutePosition().getX() + (size.getX() - cheatsRect.width()) / 2, getAbsolutePosition().getY() + CHEAT_Y, textPaint);
        }
        {
            String autoPlay = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().language).getToken("AutoPlay");
            Rect autoPlayRect = new Rect();
            this.textPaint.getTextBounds(autoPlay, 0, autoPlay.length(), autoPlayRect);
            canvas.drawText(autoPlay, getAbsolutePosition().getX() + LEFT_MARGIN, getAbsolutePosition().getY() + AUTO_Y, textPaint);
            float vx1 = LEFT_MARGIN + autoPlayRect.width() + 20;
            this.autoOn.setRelativePosition(new Vector(vx1, AUTO_Y - 50));
            this.autoOff.setRelativePosition(new Vector(vx1, AUTO_Y - 50));
        }
    }

    @Override
    public void observe(Observable observable) {
        if (observable == exit) {
              if (exit.isPressed()) {
                this.setEnable(false);
                this.notifyObservers();
              }
        } else if (observable == light) {
            if (light.isPressed()) {
                getGlobalPreferences().theme = Themes.DARK;
                light.setEnable(getGlobalPreferences().theme == Themes.LIGHT);
                dark.setEnable(getGlobalPreferences().theme == Themes.DARK);
            }
        } else if (observable == dark) {
            if (dark.isPressed()) {
                getGlobalPreferences().theme = Themes.LIGHT;
                light.setEnable(getGlobalPreferences().theme == Themes.LIGHT);
                dark.setEnable(getGlobalPreferences().theme == Themes.DARK);
            }
        } else if (observable == volumeAddButton) {
            if (volumeAddButton.isPressed()) {
                this.volume += 1;
                if (this.volume > 10)
                    this.volume = 10;
                getGlobalPreferences().volume = (this.volume / 10d);
            }
        } else if (observable == volumeSubButton) {
            if (volumeSubButton.isPressed()) {
                this.volume -= 1;
                if (this.volume < 0)
                    this.volume = 0;
                getGlobalPreferences().volume = (this.volume / 10d);
            }
        } else if (observable == difficultyAddButton) {
            if (difficultyAddButton.isPressed()) {
                this.difficulty += 1;
                if (this.difficulty > 10)
                    this.difficulty = 10;
            }
        } else if (observable == difficultySubButton) {
            if (difficultySubButton.isPressed()) {
                this.difficulty -= 1;
                if (this.difficulty < 1)
                    this.difficulty = 1;
            }
        } else if (observable == autoOn) {
            if (autoOn.isPressed()) {
                auto = false;
                autoOn.setEnable(auto);
                autoOff.setEnable(!auto);
            }
        } else if (observable == autoOff) {
            if (autoOff.isPressed()) {
                auto = true;
                autoOn.setEnable(auto);
                autoOff.setEnable(!auto);
            }
        }
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean getAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
        autoOn.setEnable(auto);
        autoOff.setEnable(!auto);
    }
}
