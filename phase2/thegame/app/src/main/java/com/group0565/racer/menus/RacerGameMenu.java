package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.render.ThemedPaintCan;
import com.group0565.math.Vector;
import com.group0565.racer.objects.Lane;
import com.group0565.racer.core.RacerEngine;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

public class RacerGameMenu extends GameMenu implements Observable {

    /**
     * ThemedPaintCan for background
     */
    private static final ThemedPaintCan BACKGROUND_PAINT_CAN = new ThemedPaintCan("Racer", "Background.Background");

    /**
     * ThemedPaintCan for Score
     */
    private static final ThemedPaintCan SCORE_PAINT_CAN = new ThemedPaintCan("Racer", "Score.Score");

    /**
     * ThemedPaintCan for lane dividers
     */
    private static final ThemedPaintCan LANE_PAINT_CAN = new ThemedPaintCan("Racer", "Divider.Divider");

    /**
     * The size of the pause button.
     */
    private static final Vector PAUSE_BUTTON_SIZE = new Vector(100, 100);

    /**
     * The value of the left Lane used to move the Racer.
     */
    private static final int LEFT_LANE_VALUE = 1;

    /**
     * The value of the middle Lane used to move the Racer.
     */
    private static final int MIDDLE_LANE_VALUE = 2;

    /**
     * The value of the right Lane used to move the Racer.
     */
    private static final int RIGHT_LANE_VALUE = 3;

    /**
     * The observation message passed when a collision occurs.
     */
    private static final String COLLISION_MESSAGE = "Collision";

    /**
     * The position of the score value.
     */
    private static final Vector SCORE_POSITION = new Vector(50, 170);

    /**
     * The size of the dividers inbetween the Lanes.
     */
    private static final Vector DIVIDER = new Vector(30, 2500);

    /** An engine object */
    private RacerEngine engine;

    /** The left lane of the object */
    private Lane leftLane;

    /** The middle lane of the object */
    private Lane middleLane;

    /** The right lane of the object*/
    private Lane rightLane;

    /**
     * A RacerGameMenu object for display purposes
     * @param engine The engine object that controls this game menu
     */
    public RacerGameMenu(RacerEngine engine) {
        super(null);
        this.engine = engine;
    }

    @Override
    public void init() {
        super.init();

        this.build()
                .add("Left Lane", (leftLane = new Lane(new Vector(getSize().getX() / 3, 0), this::observeLeftLane)).build()
                        .close())
                .addAlignment(Left, THIS, Left)
                .addAlignment(Bottom, THIS, Bottom)
                .addAlignment(Top, THIS, Top)


                .add("Middle Lane", (middleLane = new Lane(new Vector(getSize().getX() / 3, 0), this::observeMiddleLane)).build()
                        .close())
                .addAlignment(HCenter, THIS, HCenter)
                .addAlignment(Bottom, THIS, Bottom)
                .addAlignment(Top, THIS, Top)

                .add("Right Lane", (rightLane = new Lane(new Vector(getSize().getX() / 3, 0), this::observeRightLane)).build()
                        .close())
                .addAlignment(Right, THIS, Right)
                .addAlignment(Bottom, THIS, Bottom)
                .addAlignment(Top, THIS, Top)

                .add("Pause Button", new Button(new Vector(900, 150),
                                PAUSE_BUTTON_SIZE,
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("Racer", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("Racer", "RacerButton")
                        .getTile(0, 0)).build()
                        .registerObserver(this::observePauseButton)
                .close()
                )
        .close();

        leftLane.registerObserver(this::observeCollision);
        middleLane.registerObserver(this::observeCollision);
        rightLane.registerObserver(this::observeCollision);

        middleLane.setRacerLane(true);

        BACKGROUND_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        SCORE_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());
        LANE_PAINT_CAN.init(getGlobalPreferences(), getEngine().getGameAssetManager());

    }

    /**
    Spawns an obstacle in a random lane
     */
    public void spawnObstacle() {
        double d = Math.random();

        if (d < 0.3) {
            leftLane.spawnObstacle();
        } else if (d < 0.7) {
            middleLane.spawnObstacle();
        } else {
            rightLane.spawnObstacle();
        }
    }

    /**
     * Renders this object on the screen
     *
     * @param canvas The Canvas on which to draw
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(BACKGROUND_PAINT_CAN);
        canvas.drawRect(new Vector(canvas.getWidth() / 3 - 15, 0), DIVIDER, LANE_PAINT_CAN);
        canvas.drawRect(new Vector(2 * canvas.getWidth() / 3 - 15, 0), DIVIDER, LANE_PAINT_CAN);
        SCORE_PAINT_CAN.setTextSize(96);
        canvas.drawText(Long.toString(engine.getTotalTime()), SCORE_POSITION, SCORE_PAINT_CAN);
    }

    /**
     * Observes left lane
     * @param observable the object being observed
     * @param event the event being observed
     */
    private void observeLeftLane(Observable observable, ObservationEvent event) {
        if (event.isEvent(Button.EVENT_DOWN)) {
            middleLane.setRacerLane(false);
            rightLane.setRacerLane(false);
            leftLane.setRacerLane(true);
            engine.moveRacer(LEFT_LANE_VALUE);
        }
    }

    /**
     * Observes mid lane
     * @param observable the object being observed
     * @param event the event being observed
     */
    private void observeMiddleLane(Observable observable, ObservationEvent event) {
        if (event.isEvent(Button.EVENT_DOWN)) {
            leftLane.setRacerLane(false);
            rightLane.setRacerLane(false);
            middleLane.setRacerLane(true);
            engine.moveRacer(MIDDLE_LANE_VALUE);
        }
    }

    /**
     * Observes right lane
     * @param observable the object being observed
     * @param event the event being observed
     */
    private void observeRightLane(Observable observable, ObservationEvent event) {
        if (event.isEvent(Button.EVENT_DOWN)) {
            leftLane.setRacerLane(false);
            middleLane.setRacerLane(false);
            rightLane.setRacerLane(true);
            engine.moveRacer(RIGHT_LANE_VALUE);
        }
    }

    /**
     * Observes pausing
     * @param observable the object being observed
     * @param event the event being observed
     */
    private void observePauseButton(Observable observable, ObservationEvent event) {
        if (event.isEvent(Button.EVENT_DOWN)) {
            engine.pauseGame();
        }
    }

    /**
     * Observes collisions
     * @param observable the object being observed
     * @param observationEvent the event that is being observed
     */

    private void observeCollision(Observable observable, ObservationEvent observationEvent) {
        if (observationEvent.getMsg().equals(COLLISION_MESSAGE)) {
            engine.endGame();
        }
    }
}