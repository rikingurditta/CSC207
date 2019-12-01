package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.racer.core.RacerEngine;

/**
 * The Pause Menu that displays when the game is paused
 */
public class RacerPauseMenu extends GameMenu {

    /**
     * The vector position where the title 'PAUSE' is drawn
     */
    private static final Vector PAUSE_POSITION = new Vector(50, 200);

    /**
     * The vector position of the player's score as of pausing
     */
    private static final Vector SCORE_POSITION = new Vector(50, 400);

    /**
     * The resume text vector position
     */
    private static final Vector RESUME_POSITION = new Vector(450, 1500);

    /**
     * A button to resume the game and leave pause menu
     */
    private Button resumeButton;

    /**
     * The engine that controls the PauseMenu
     */
    private RacerEngine engine;

    /**
     * ThemedPaintCan for the colours and size of text on the pause menu
     */
    private static final ThemedPaintCan MESSAGE_PAINT_CAN = new ThemedPaintCan("Racer", "Message.Message");

    /**
     * ThemedPaintCan for the colours of the pause menu background
     */
    private static final ThemedPaintCan PAUSE_PAINT_CAN = new ThemedPaintCan("Racer", "Pause.Pause");

    /**
     * RacerPauseMenu constructor
     * @param size the size of the menu
     * @param engine the engine that this pause menu is held by
     */
    public RacerPauseMenu(Vector size, RacerEngine engine) {
        super(size);
        this.engine = engine;
    }

    /**
     * Initializes the buttons and ThemedPaintCans
     */
    public void init() {
        resumeButton = new Button(new Vector(510, 1600),
                new Vector(150, 150),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("Racer", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("Racer", "RacerButton")
                        .getTile(0, 0));
        resumeButton.registerObserver(this::observe);
        this.adopt(resumeButton);

        MESSAGE_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        PAUSE_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
    }

    /**
     * Unpauses the game and leaves the PauseMenu when the button is pressed
     * @param observable an observable object
     * @param observationEvent the observed event
     */
    public void observe(Observable observable, ObservationEvent observationEvent) {
        if (observable == resumeButton && observationEvent.isEvent(Button.EVENT_DOWN)) {
            engine.unpauseGame();
        }
    }

    /**
     * Renders the PauseMenu
     * @param canvas the canvas it's drawn on
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(PAUSE_PAINT_CAN);
        canvas.drawText("PAUSED", PAUSE_POSITION, MESSAGE_PAINT_CAN);
        canvas.drawText("Score: " + engine.getTotalTime(), SCORE_POSITION , MESSAGE_PAINT_CAN);
        canvas.drawText("Resume", RESUME_POSITION, MESSAGE_PAINT_CAN);
    }
}
