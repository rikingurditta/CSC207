package com.group0565.tsu.gameObjects;

import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Observer;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.TextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.theme.Themes;
import com.group0565.tsu.enums.ButtonBitmap;


public class TsuMenu extends GameMenu implements Observable {
    private static final Vector TITLE_SIZE = new Vector(500);
    private static final Vector SETTINGS_SIZE = new Vector(900);
    private static final Vector BUTTON_SIZE = new Vector(75);
    private static final Vector SCREEN_MARGIN = new Vector(75);

    private static final String SET = "Tsu";
    //Background Constants
    private static final String BGPaintName = "GameMenu.Background";
    //Title Button Constants
    private static final String TitleButtonName = "TitleButton";
    private static final String TitleButtonBMName = "Title";
    //Title String Constants
    private static final String TitleStringName = "TitleString";
    private static final String TitleStringTokenName = "StartButton";
    private static final String TitleStringPaintName = "GameMenu.Title";
    //Settings Menu Constants
    private static final String SettingsMenuName = "SettingsMenu";
    //Settings Button Constants
    private static final String SettingsButtonName = "SettingsButton";
    //Language Button Constants
    private static final String LanguageButtonName = "LanguageButton";
    //Stats Button Constants
    private static final String StatsButtonName = "StatsButton";

    private ThemedPaintCan bgPaintCan = new ThemedPaintCan(SET, BGPaintName);
    private ThemedPaintCan titlePaintCan = new ThemedPaintCan(SET, TitleStringPaintName);

    private Button settingsButton;
    private Button languageButton;
    private Button statsButton;


    private boolean started = false;
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
        ButtonBitmap.init(getEngine().getGameAssetManager());

        bgPaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        titlePaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        // @formatter:off
        this.build()
            .add(TitleButtonName,  new Button(TITLE_SIZE, getEngine().getGameAssetManager(), SET, TitleButtonBMName).build()
                .registerObserver(this::observeTitle)
                .close())
            .setCenteredRelativePosition("this")

            .add(TitleStringName, new TextRenderer(new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, TitleStringTokenName), titlePaintCan).build()
                .addOffset(TITLE_SIZE.elementMultiply(new Vector(0, 0.25f)))
                .close())
            .setCenteredRelativePosition(TitleButtonName)

            .add(SettingsMenuName, new SettingsMenu(new Vector(), SETTINGS_SIZE).build()
                .setEnable(false)
                .setZ(1)
                .registerObserver(this::observeSettingsMenu)
                .close())
            .setCenteredRelativePosition("this")

            .add(SettingsButtonName, new Button(BUTTON_SIZE, ButtonBitmap.SettingsButton.getBitmap()).build()
                .addOffset(SCREEN_MARGIN.multiply(-1))
                .registerObserver(this::observeSettingsButton)
                .close())
            .setRelativePosition("this", HorizontalAlignment.RightAlign, VerticalAlignment.BottomAlign)

            .add(LanguageButtonName, (languageButton = new Button(BUTTON_SIZE, ButtonBitmap.LanguageButton.getBitmap())).build()
                .addOffset(SCREEN_MARGIN.elementMultiply(new Vector(-1, 1)))
                .registerObserver(this::observeLanguageButton)
                .close())
            .setRelativePosition("this", HorizontalAlignment.RightAlign, VerticalAlignment.TopAlign)

            .add(StatsButtonName, (statsButton = new Button(BUTTON_SIZE, ButtonBitmap.StatsButton.getBitmap())).build()
                .addOffset(SCREEN_MARGIN.elementMultiply(new Vector(1, -1)))
                .registerObserver(this::observeStatsButton)
                .close())
            .setRelativePosition("this", HorizontalAlignment.LeftAlign, VerticalAlignment.BottomAlign)
        .close();
        // @formatter:on
    }

    private void observeTitle(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){

        }
    }

    private void observeSettingsMenu(Observable observable, ObservationEvent event){

    }

    private void observeSettingsButton(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){

        }
    }

    private void observeLanguageButton(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            if (getGlobalPreferences().getLanguage().equals("en")) {
                getGlobalPreferences().setLanguage("fr");
            } else if (getGlobalPreferences().getLanguage().equals("fr")) {
                getGlobalPreferences().setLanguage("en");
            }
            tsuGame.setPreferences(getGlobalPreferences());
        }
    }

    private void observeStatsButton(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){

        }
    }

//    @Override
//    public void observe(Observable observable) {
//        if (observable == titleButton) {
//            if (titleButton.isPressed()) {
//                started = true;
//                this.notifyObservers();
//            }
//        } else if (observable == settingsButton) {
//            if (settingsButton.isPressed()) {
//                settingsMenu.setDifficulty((int) this.difficulty);
//                settingsMenu.setAuto(this.auto);
//                settingsMenu.setEnable(true);
//            }
//        } else if (observable == languageButton) {
//            if (languageButton.isPressed()) {
//                if (getGlobalPreferences().getLanguage().equals("en")) {
//                    getGlobalPreferences().setLanguage("fr");
//                } else if (getGlobalPreferences().getLanguage().equals("fr")) {
//                    getGlobalPreferences().setLanguage("en");
//                }
//                tsuGame.setPreferences(getGlobalPreferences());
//            }
//        } else if (observable == statsButton) {
//            if (statsButton.isPressed()) {
//                stats = true;
//                this.notifyObservers();
//            }
//        } else if (observable == settingsMenu) {
//            if (!settingsMenu.isEnable()) {
//                this.difficulty = settingsMenu.getDifficulty();
//                this.auto = settingsMenu.getAuto();
//                tsuGame.setPreferences(getGlobalPreferences());
//                tsuGame.setPreferences(tsuGame.getDifficultyPrefName(), this.difficulty);
//                tsuGame.setPreferences(tsuGame.getAutoPrefName(), this.auto);
//            }
//        }
//    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(bgPaintCan);
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
