package com.group0565.tsu.menus;

import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.LanguageText;
import com.group0565.engine.render.TextRenderer;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.tsu.enums.ButtonBitmap;


public class TsuMenu extends GameMenu implements Observable {
    //Event Constants
    public static final String TO_GAME = "Go To Game";
    public static final String TO_STATS = "Go To Stats";

    //GUI Constants
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

    private SettingsMenu settingsMenu;

    public TsuMenu() {
        super(null);
    }

    @Override
    public void init() {
        super.init();
        //Ensure ButtonBitmap is initialized
        ButtonBitmap.init(getEngine().getGameAssetManager());

        //Initialization of PaintCans
        bgPaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        titlePaintCan.init(getGlobalPreferences(), getEngine().getGameAssetManager());

        //Building the Menu. Disabling formatter as too many spaces are added normally.
        // @formatter:off
        this.build()
            //Title Button
            .add(TitleButtonName,  new Button(TITLE_SIZE, getEngine().getGameAssetManager(), SET, TitleButtonBMName).build()
                .registerObserver(this::observeTitle)
                .close())
            .setCenteredRelativePosition("this")

            //Title String
            .add(TitleStringName, new TextRenderer(new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, TitleStringTokenName), titlePaintCan).build()
                .addOffset(TITLE_SIZE.elementMultiply(new Vector(0, 0.20f)))
                .close())
            .setCenteredRelativePosition(TitleButtonName)

            //Settings Menu
            .add(SettingsMenuName, (settingsMenu = new SettingsMenu(SETTINGS_SIZE)).build()
                .setEnable(false)
                .setZ(1)
                .close())
            .setCenteredRelativePosition("this")

            //Settings Button
            .add(SettingsButtonName, new Button(BUTTON_SIZE, ButtonBitmap.SettingsButton.getBitmap()).build()
                .addOffset(SCREEN_MARGIN.multiply(-1))
                .registerObserver(this::observeSettingsButton)
                .close())
            .setRelativePosition("this", HorizontalAlignment.RightAlign, VerticalAlignment.BottomAlign)

            //Language Button
            .add(LanguageButtonName, new Button(BUTTON_SIZE, ButtonBitmap.LanguageButton.getBitmap()).build()
                .addOffset(SCREEN_MARGIN.elementMultiply(new Vector(-1, 1)))
                .registerObserver(this::observeLanguageButton)
                .close())
            .setRelativePosition("this", HorizontalAlignment.RightAlign, VerticalAlignment.TopAlign)

            //Stats Button
            .add(StatsButtonName, new Button(BUTTON_SIZE, ButtonBitmap.StatsButton.getBitmap()).build()
                .addOffset(SCREEN_MARGIN.elementMultiply(new Vector(1, -1)))
                .registerObserver(this::observeStatsButton)
                .close())
            .setRelativePosition("this", HorizontalAlignment.LeftAlign, VerticalAlignment.BottomAlign)
        .close();
        // @formatter:on
    }

    /**
     * Event observing method for TitleButton
     */
    private void observeTitle(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            getEngine().getAchievementManager().unlockAchievement(SET, "FirstGame");
            notifyObservers(new ObservationEvent(TO_GAME));
        }
    }

    /**
     * Event observing method for SettingsButton
     */
    private void observeSettingsButton(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            settingsMenu.setEnable(true);
        }
    }

    /**
     * Event observing method for LanguageButton
     */
    private void observeLanguageButton(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            if (getGlobalPreferences().getLanguage().equals("en")) {
                getGlobalPreferences().setLanguage("fr");
            } else if (getGlobalPreferences().getLanguage().equals("fr")) {
                getGlobalPreferences().setLanguage("en");
            }
        }
    }

    /**
     * Event observing method for StatsButton
     */
    private void observeStatsButton(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            notifyObservers(new ObservationEvent(TO_STATS));
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
}
