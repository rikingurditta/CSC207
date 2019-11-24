package com.group0565.tsu.gameObjects;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.TextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;


public class TsuMenu extends GameMenu implements Observer, Observable {
    private static final Vector TITLE_SIZE = new Vector(500);
    private static final Vector SETTINGS_SIZE = new Vector(900);
    private static final Vector BUTTON_SIZE = new Vector(75);
    private static final float MARGIN = 75;

    private static final String SET = "Tsu";

    //Title Button Constants
    private static final String TitleButtonName = "TitleButton";
    private static final String TitleButtonBMName = "Title";
    //Title String Constants
    private static final String TitleStringName = "TitleString";
    private static final String TitleStringTokenName = "StartButton";
    private static final String TitleStringPaintName = "GameMenu.Title";
    //Settings Menu Constants
    private static final String SettingsMenuName = "SettingsMenu";
    //


    private ThemedPaintCan titlePaintCan = new ThemedPaintCan(SET, TitleStringPaintName);

    private Button titleButton;
    private boolean started = false;
    private Paint titlePaint;
    private SettingsMenu settingsMenu;
    private Button settingsButton;
    private Button languageButton;
    private Button statsButton;
    private double difficulty;
    private boolean stats = false;
    private boolean auto = false;
    private TsuGame tsuGame;

    public TsuMenu(TsuGame tsuGame) {
        super(null);
        this.tsuGame = tsuGame;
    }

    @Override
    public void init() {
        super.init();
        titlePaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        this.build()
                .add(TitleButtonName, new Button(TITLE_SIZE, getEngine(), SET, TitleButtonBMName))
                .setCenteredRelativePosition("this")
                .add(TitleStringName, new TextRenderer(new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, TitleStringTokenName), titlePaintCan).build()
                    .addOffset(TITLE_SIZE.elementMultiply(new Vector(0, 0.25f)))
                    .close())
                .setCenteredRelativePosition(TitleButtonName)
                .add(SettingsMenuName, new SettingsMenu(new Vector(), SETTINGS_SIZE).build()
                    .setEnable(false)
                    .setZ(1)
                    .close())
                .setCenteredRelativePosition("this")
                .close();
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
                settingsMenu.setDifficulty((int) this.difficulty);
                settingsMenu.setAuto(this.auto);
                settingsMenu.setEnable(true);
            }
        } else if (observable == languageButton) {
            if (languageButton.isPressed()) {
                if (getGlobalPreferences().getLanguage().equals("en")) {
                    getGlobalPreferences().setLanguage("fr");
                } else if (getGlobalPreferences().getLanguage().equals("fr")) {
                    getGlobalPreferences().setLanguage("en");
                }
                tsuGame.setPreferences(getGlobalPreferences());
            }
        } else if (observable == statsButton) {
            if (statsButton.isPressed()) {
                stats = true;
                this.notifyObservers();
            }
        } else if (observable == settingsMenu) {
            if (!settingsMenu.isEnable()) {
                this.difficulty = settingsMenu.getDifficulty();
                this.auto = settingsMenu.getAuto();
                tsuGame.setPreferences(getGlobalPreferences());
                tsuGame.setPreferences(tsuGame.getDifficultyPrefName(), this.difficulty);
                tsuGame.setPreferences(tsuGame.getAutoPrefName(), this.auto);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().getTheme() == Themes.LIGHT)
            canvas.drawRGB(255, 255, 255);
        else if (getGlobalPreferences().getTheme() == Themes.DARK)
            canvas.drawRGB(0, 0, 0);
//        String text = getEngine().getGameAssetManager().getLanguagePack("Tsu", getGlobalPreferences().getLanguage()).getToken("StartButton");
//        Rect rect = new Rect();
//        this.titlePaint.getTextBounds(text, 0, text.length(), rect);
//        float tx = (canvas.getWidth() - rect.width()) / 2;
//        float ty = titleButton.getAbsolutePosition().getY() + 3 * TITLE_SIZE / 4;
//        canvas.drawText(text, tx, ty, this.titlePaint);
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

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public boolean getAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }
}
