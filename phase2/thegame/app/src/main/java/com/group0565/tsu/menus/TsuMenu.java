package com.group0565.tsu.menus;

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

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

public class TsuMenu extends GameMenu implements Observable {
    //Event Constants
    public static final String TO_GAME = "Go To Game";

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
            .addCenteredAlignment(THIS)

            //Title String
            .add(TitleStringName, new TextRenderer(new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, TitleStringTokenName), titlePaintCan).build()
                .addOffset(TITLE_SIZE.elementMultiply(new Vector(0, 0.20f)))
                .close())
            .addCenteredAlignment(TitleButtonName)

            //Settings Menu
            .add(SettingsMenuName, (settingsMenu = new SettingsMenu(SETTINGS_SIZE)).build()
                .setEnable(false)
                .setZ(1)
                .close())
            .addCenteredAlignment("this")

            //Settings Button
            .add(SettingsButtonName, new Button(BUTTON_SIZE, ButtonBitmap.SettingsButton.getBitmap()).build()
                .addOffset(SCREEN_MARGIN.multiply(-1))
                .registerObserver(this::observeSettingsButton)
                .close())
            .addAlignment(Right, THIS, Right)
            .addAlignment(Bottom, THIS, Bottom)

            //Language Button
            .add(LanguageButtonName, new Button(BUTTON_SIZE, ButtonBitmap.LanguageButton.getBitmap()).build()
                .addOffset(SCREEN_MARGIN.elementMultiply(new Vector(-1, 1)))
                .registerObserver(this::observeLanguageButton)
                .close())
            .addAlignment(Right, THIS, Right)
            .addAlignment(Top, THIS, Top)
        .close();
        // @formatter:on
    }

    /**
     * Event observing method for TitleButton
     */
    private void observeTitle(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(bgPaintCan);
    }
}
