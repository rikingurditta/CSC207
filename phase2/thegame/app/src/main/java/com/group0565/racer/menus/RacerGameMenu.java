package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.racer.objects.Lane;
import com.group0565.racer.core.RacerEngine;
import com.group0565.theme.Themes;

import static com.group0565.engine.enums.HorizontalEdge.*;
import static com.group0565.engine.enums.VerticalEdge.*;

public class RacerGameMenu extends GameMenu implements Observable {

    private RacerEngine engine;

    private Lane leftLane;

    private Lane middleLane;

    private Lane rightLane;

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
                new Vector(100, 100),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0)).build()
                        .registerObserver(this::observePauseButton)
                .close()
                )
        .close();

        leftLane.registerObserver(this::observeLane);
        middleLane.registerObserver(this::observeLane);
        rightLane.registerObserver(this::observeLane);
    }

    /**
     * Renders this object on the screen
     *
     * @param canvas The Canvas on which to draw
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getGlobalPreferences().getTheme() == Themes.LIGHT) {
            // Set background to white
            canvas.drawRGB(255, 255, 255);
            // Set text colour to black
            //time.setARGB(255, 0, 0, 0);
        } else {
            // Set background to black
            canvas.drawRGB(0, 0, 0);
            // Set text colour to white
            //time.setARGB(255, 255, 255, 255);
        }
        Paint time = Paint.createInstance();
        time.setARGB(255, 0, 0, 0);
        time.setTextSize(128);
        // Set the colour of the lines
        Paint colour = Paint.createInstance();
        colour.setARGB(255, 255, 0, 0);
        // Draw the red lines that separate the lanes
        canvas.drawRect(canvas.getWidth() / 3 - 15, 0, canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawRect(
                2 * canvas.getWidth() / 3 - 15, 0, 2 * canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawText(Long.toString(engine.getTotalTime()), 600, 200, time);
    }

    public void observeLeftLane(Observable observable, ObservationEvent event) {
        if (event.isEvent(Button.EVENT_DOWN)) {
            middleLane.setRacerLane(false);
            rightLane.setRacerLane(false);
            leftLane.setRacerLane(true);
            engine.moveRacer(1);
        }
    }

    public void observeMiddleLane(Observable observable, ObservationEvent event) {
        if (event.isEvent(Button.EVENT_DOWN)) {
            leftLane.setRacerLane(false);
            rightLane.setRacerLane(false);
            middleLane.setRacerLane(true);
            engine.moveRacer(2);
        }
    }

    public void observeRightLane(Observable observable, ObservationEvent event) {
        if (event.isEvent(Button.EVENT_DOWN)) {
            leftLane.setRacerLane(false);
            middleLane.setRacerLane(false);
            rightLane.setRacerLane(true);
            engine.moveRacer(3);
        }
    }

    public void observePauseButton(Observable observable, ObservationEvent event) {
        if (event.isEvent(Button.EVENT_DOWN)) {

        }
    }

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

    public void observeLane(Observable observable, ObservationEvent observationEvent) {
        if (observationEvent.getMsg().equals("Collision")) {
            engine.endGame();
        }
    }
}