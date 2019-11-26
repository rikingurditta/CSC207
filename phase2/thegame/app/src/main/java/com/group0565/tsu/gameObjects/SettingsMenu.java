package com.group0565.tsu.gameObjects;

import com.group0565.engine.enums.HorizontalAlignment;
import com.group0565.engine.enums.VerticalAlignment;
import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.gameobjects.InputEvent;
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

public class SettingsMenu extends GameMenu implements Observer, Observable {
    //GUI Constants
    private static final Vector BUTTON_SIZE = new Vector(75);
    private static final Vector MARGINS = new Vector(75);
    private static final Vector BUFFERS = new Vector(50);
    private static final Vector BORDER_OFFSET = new Vector(10);
    private static final Vector BORDER_RADIUS = new Vector(50);

    //Asset Constants
    private static final String SET = "Tsu";
    private static final String ThemeFolder = "SettingsMenu.";
    private static final String TextPaintName = ThemeFolder + "Text";
    private static final String CenterPaintName = ThemeFolder + "Center";
    private static final String RimPaintName = ThemeFolder + "Rim";

    //Exit Button Constants
    private static final String ExitButtonName = "ExitButton";
    //Light Button Constants
    private static final String LightButtonName = "LightButton";
    //Dark Button Constants
    private static final String DarkButtonName = "DarkButton";
    //Volume Label Constants
    private static final String VolumeLabelName = "VolumeLabel";
    private static final String VolumeStringName = "Volume";
    //Volume Add Button Constants
    private static final String VolumeAddButtonName = "VolumeAddButton";
    //Volume Sub Button Constants
    private static final String VolumeSubButtonName = "VolumeSubButton";
    //Volume Value Constants
    private static final String VolumeValueName = "VolumeValue";
    //Difficulty Label Constants
    private static final String DifficultyLabelName = "DifficultyLabel";
    private static final String DifficultyStringName = "Difficulty";
    //Difficulty Add Button Constants
    private static final String DifficultyAddButtonName = "DifficultyAddButton";
    //Difficulty Sub Button Constants
    private static final String DifficultySubButtonName = "DifficultySubButton";
    //Difficulty Value Constants
    private static final String DifficultyValueName = "DifficultyValue";
    //Cheats Label Constants
    private static final String CheatsLabelName = "CheatsLabel";
    private static final String CheatsStringName = "Cheats";
    //Auto Label Constants
    private static final String AutoLabelName = "AutoLabel";
    private static final String AutoStringName = "AutoPlay";
    //AutoOn Button Constants
    private static final String AutoOnButtonName = "AutoOnButton";
    //AutoOff Button Constants
    private static final String AutoOffButtonName = "AutoOffButton";

    private ThemedPaintCan rimPaint = new ThemedPaintCan(SET, RimPaintName);
    private ThemedPaintCan centerPaint = new ThemedPaintCan(SET, CenterPaintName);
    private ThemedPaintCan textPaint = new ThemedPaintCan(SET, TextPaintName);

    private Preferences preferences;

    public SettingsMenu(Vector size) {
        super(size);
    }

    @Override
    public void init() {
        super.init();
        if (!(getGlobalPreferences() instanceof Preferences))
            throw new RuntimeException("Preference for Tsu Must be com.group0565.tsu.gameObjects.Preferences");
        preferences = (Preferences) getGlobalPreferences();

        this.textPaint.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        this.centerPaint.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        this.rimPaint.init(getGlobalPreferences(), getEngine().getGameAssetManager());

        //Building the Menu. Disabling formatter as too many spaces are added normally.
        // @formatter:off
        this.build()
            //Exit Button
            .add(ExitButtonName, new Button(BUTTON_SIZE, ButtonBitmap.ExitButton.getBitmap()).build()
                .registerObserver(this::observeExit)
            .close())
            .setRelativePosition("this", HorizontalAlignment.LeftAlign, VerticalAlignment.TopAlign)

            //Light Button
            .add(LightButtonName, new Button(BUTTON_SIZE, ButtonBitmap.LightButton.getBitmap()).build()
                .addOffset(MARGINS)
                .setSelfEnable(() -> getGlobalPreferences().getTheme() == Themes.LIGHT)
                .registerObserver(this::observeTheme)
            .close())
            .setRelativePosition("this", HorizontalAlignment.LeftAlign, VerticalAlignment.TopAlign)

            //Dark Button
            .add(DarkButtonName, new Button(BUTTON_SIZE, ButtonBitmap.DarkButton.getBitmap()).build()
                .setSelfEnable(() -> getGlobalPreferences().getTheme() == Themes.DARK)
                .registerObserver(this::observeTheme)
                .close())
            .setRelativePosition(LightButtonName, HorizontalAlignment.LeftAlign, VerticalAlignment.TopAlign)

            //Volume Label
            .add(VolumeLabelName, new TextRenderer(new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, VolumeStringName), textPaint).build()
                .addOffset(0, BUFFERS.getY())
                .close())
            .setRelativePosition(LightButtonName, HorizontalAlignment.LeftAlign, VerticalAlignment.BottomOf)

            //Volume Sub Button
            .add(VolumeSubButtonName, new Button(BUTTON_SIZE, ButtonBitmap.SubButton.getBitmap()).build()
                .addOffset(BUFFERS.getX(), 0)
                .registerObserver(((observable, event) -> this.observeVolume(observable, event, false)))
            .close())
            .setRelativePosition(VolumeLabelName, HorizontalAlignment.RightOf, VerticalAlignment.Center)

            //Volume Value
            .add(VolumeValueName, new TextRenderer(() -> String.valueOf(getVolume()), textPaint).build()
                .addOffset(BUFFERS.getX() * 0.5f, 0)
                .close())
            .setRelativePosition(VolumeSubButtonName, HorizontalAlignment.RightOf, VerticalAlignment.Center)

            //Volume Add Button
            .add(VolumeAddButtonName, new Button(BUTTON_SIZE, ButtonBitmap.AddButton.getBitmap()).build()
                .addOffset(BUFFERS.getX() * 0.5f, 0)
                .registerObserver((observable, event) -> this.observeVolume(observable, event, true))
            .close())
            .setRelativePosition(VolumeValueName, HorizontalAlignment.RightOf, VerticalAlignment.Center)
                
            //Difficulty Label
            .add(DifficultyLabelName, new TextRenderer(new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, DifficultyStringName), textPaint).build()
                .addOffset(0, BUFFERS.getY() * 1.25f)
                .close())
            .setRelativePosition(VolumeLabelName, HorizontalAlignment.LeftAlign, VerticalAlignment.BottomOf)

            //Difficulty Sub Button
            .add(DifficultySubButtonName, new Button(BUTTON_SIZE, ButtonBitmap.SubButton.getBitmap()).build()
                .addOffset(BUFFERS.getX(), 0)
                .registerObserver((observable, event) -> this.observeDifficulty(observable, event, false))
            .close())
            .setRelativePosition(DifficultyLabelName, HorizontalAlignment.RightOf, VerticalAlignment.Center)

            //Difficulty Value
            .add(DifficultyValueName, new TextRenderer(() -> String.valueOf(getDifficulty()), textPaint).build()
                .addOffset(BUFFERS.getX() * 0.5f, 0)
                .close())
            .setRelativePosition(DifficultySubButtonName, HorizontalAlignment.RightOf, VerticalAlignment.Center)

            //Difficulty Add Button
            .add(DifficultyAddButtonName, new Button(BUTTON_SIZE, ButtonBitmap.AddButton.getBitmap()).build()
                .addOffset(BUFFERS.getX() * 0.5f, 0)
                .registerObserver((observable, event) -> this.observeDifficulty(observable, event, true))
            .close())
            .setRelativePosition(DifficultyValueName, HorizontalAlignment.RightOf, VerticalAlignment.Center)

            //Cheats Label
            .add(CheatsLabelName, new TextRenderer(new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, CheatsStringName), textPaint).build()
                .addOffset(0, BUFFERS.getY() * 1.25f)
                .close())
            .setRelativePosition(DifficultyLabelName, HorizontalAlignment.LeftAlign, VerticalAlignment.BottomOf)
                                
            //Auto Label
            .add(AutoLabelName, new TextRenderer(new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), SET, AutoStringName), textPaint).build()
                .addOffset(0, BUFFERS.getY() * 1.25f)
                .close())
            .setRelativePosition(CheatsLabelName, HorizontalAlignment.LeftAlign, VerticalAlignment.BottomOf)

            //AutoOn Button
            .add(AutoOnButtonName, new Button(BUTTON_SIZE, ButtonBitmap.AutoOnButton.getBitmap()).build()
                .addOffset(BUFFERS.getX(), 0)
                .setSelfEnable(() -> preferences.getAuto())
                .registerObserver(this::observeAuto)
            .close())
            .setRelativePosition(AutoLabelName, HorizontalAlignment.RightOf, VerticalAlignment.Center)

            //AutoOn Button
            .add(AutoOffButtonName, new Button(BUTTON_SIZE, ButtonBitmap.AutoOffButton.getBitmap()).build()
                .setSelfEnable(() -> !preferences.getAuto())
                .registerObserver(this::observeAuto)
            .close())
            .setRelativePosition(AutoOnButtonName, HorizontalAlignment.Center, VerticalAlignment.Center)
        .close();
        // @formatter:on
    }

    @Override
    public boolean processInput(InputEvent event) {
        if (!isEnable())
            return false;
        if (!super.processInput(event)) {
            if (Vector.inBounds(getAbsolutePosition(), getSize(), event.getPos())) {
                captureEvent(event);
                return true;
            }
        } else
            return true;
        return false;
    }

    /**
     * Event observing method for Exit
     */
    private void observeExit(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            this.setEnable(false);
        }
    }

    /**
     * Event observing method for Theme Change
     */
    private void observeTheme(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            if (getGlobalPreferences().getTheme() == Themes.LIGHT) getGlobalPreferences().setTheme(Themes.DARK);
            else if (getGlobalPreferences().getTheme() == Themes.DARK) getGlobalPreferences().setTheme(Themes.LIGHT);
        }
    }

    /**
     * Event observing method for Volume Change
     */
    private void observeVolume(Observable observable, ObservationEvent event, boolean increase){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            int volume = getVolume();
            volume += (increase ? 1 : -1);
            //Camp volume to 0-10
            volume = Math.max(0, Math.min(10, volume));
            setVolume(volume);
        }
    }

    /**
     * Event observing method for Difficulty Change
     */
    private void observeDifficulty(Observable observable, ObservationEvent event, boolean increase){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            int difficulty = getDifficulty();
            difficulty += (increase ? 1 : -1);
            //Camp difficulty to 0-10
            difficulty = Math.max(0, Math.min(10, difficulty));
            setDifficulty(difficulty);
        }
    }

    /**
     * Event observing method for AutoPlay Change
     */
    private void observeAuto(Observable observable, ObservationEvent event){
        if (event.getMsg().equals(Button.EVENT_DOWN)){
            preferences.setAuto(!preferences.getAuto());
        }
    }

    @Override
    public void draw(Canvas canvas, Vector pos, Vector size) {
        super.draw(canvas, pos, size);
        canvas.drawRoundRect(pos, size, BORDER_RADIUS, rimPaint);
        canvas.drawRoundRect(pos.add(BORDER_OFFSET), size.subtract(BORDER_OFFSET.multiply(2)), BORDER_RADIUS, centerPaint);
    }

    /**
     * Getter for the current Volume. As preferences store them as 0.0 - 1.0,
     * but 0 - 10 volume is needed.
     * @return The volume in 0-10
     */
    private int getVolume(){
        return (int) Math.round(preferences.getVolume()*10);
    }

    /**
     * Setter for the current Volume. As preferences store them as 0.0 - 1.0,
     * but 0 - 10 volume is needed.
     * @param volume The volume in 0-10
     */
    private void setVolume(int volume){
        preferences.setVolume(volume/10d);
    }

    /**
     * Getter for the current Difficulty. As preferences store them as 0.0 - 1.0,
     * but 0 - 10 difficulty is needed.
     * @return The difficulty in 0-10
     */
    private int getDifficulty(){
        return Math.round(preferences.getDifficulty()*10);
    }

    /**
     * Setter for the current Difficulty. As preferences store them as 0.0 - 1.0,
     * but 0 - 10 difficulty is needed.
     * @param difficulty The difficulty in 0-10
     */
    private void setDifficulty(int difficulty){
        preferences.setDifficulty(difficulty/10f);
    }
}
