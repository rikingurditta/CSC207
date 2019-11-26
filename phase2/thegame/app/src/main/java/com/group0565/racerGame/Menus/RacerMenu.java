package com.group0565.racerGame.Menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.Paint;
import com.group0565.math.Vector;
import com.group0565.racerGame.Obstacles.ObstacleManager;
import com.group0565.racerGame.Racer;
import com.group0565.theme.Themes;

public class RacerMenu extends GameMenu implements Observable {
    //Event Constants
    public static final String TO_GAME = "Go To Game";
    public static final String TO_STATS = "Go To Stats";

    public RacerMenu() {
        super(null);
    }

    @Override
    public void init() {
        super.init();

        this.build()
                .add("Left Button", new Button(
                        new Vector(100, 1750),
                        new Vector(150, 150),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0)))

                // Middle Button
                .add(" Middle Button", new Button(
                        new Vector(475, 1750),
                        new Vector(150, 150),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0),
                        getEngine()
                                .getGameAssetManager()
                                .getTileSheet("RacerButton", "RacerButton")
                                .getTile(0, 0)))

                // Right Button
                .add("Right Button", new Button(
                new Vector(850, 1750),
                new Vector(150, 150),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0)))

                // Racer
                .add("Racer", new Racer(new Vector(475, 1600), 2))

                // Obstacle Manager
                .add("Obstacle Manager", new ObstacleManager(this));

//    leftButton =
//                new Button(
//                        new Vector(100, 1750),
//                        new Vector(150, 150),
//                        getEngine()
//                                .getGameAssetManager()
//                                .getTileSheet("RacerButton", "RacerButton")
//                                .getTile(0, 0),
//                        getEngine()
//                                .getGameAssetManager()
//                                .getTileSheet("RacerButton", "RacerButton")
//                                .getTile(0, 0));
//        middleButton =
//                new Button(
//                        new Vector(475, 1750),
//                        new Vector(150, 150),
//                        getEngine()
//                                .getGameAssetManager()
//                                .getTileSheet("RacerButton", "RacerButton")
//                                .getTile(0, 0),
//                        getEngine()
//                                .getGameAssetManager()
//                                .getTileSheet("RacerButton", "RacerButton")
//                                .getTile(0, 0));
//        rightButton =
//                new Button(
//                        new Vector(850, 1750),
//                        new Vector(150, 150),
//                        getEngine()
//                                .getGameAssetManager()
//                                .getTileSheet("RacerButton", "RacerButton")
//                                .getTile(0, 0),
//                        getEngine()
//                                .getGameAssetManager()
//                                .getTileSheet("RacerButton", "RacerButton")
//                                .getTile(0, 0));
//        this.adopt(leftButton);
//        this.adopt(middleButton);
//        this.adopt(rightButton);
//
//        racer = new Racer(new Vector(475, 1600), 2);
//        this.adopt(racer);
//        leftButton.registerObserver(this);
//        middleButton.registerObserver(this);
//        rightButton.registerObserver(this);
//        obsManager = new ObstacleManager(this);
//        this.adopt(obsManager);
    }

    /**
     * Renders this object on the screen
     *
     * @param canvas The Canvas on which to draw
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint time = Paint.createInstance();
        if (getGlobalPreferences().getTheme() == Themes.LIGHT) {
            // Set background to white
            canvas.drawRGB(255, 255, 255);
            // Set text colour to black
            time.setARGB(255, 0, 0, 0);
        } else {
            // Set background to black
            canvas.drawRGB(0, 0, 0);
            // Set text colour to white
            time.setARGB(255, 255, 255, 255);
        }
        time.setTextSize(128);
        // Set the colour of the lines
        Paint colour = Paint.createInstance();
        colour.setARGB(255, 255, 0, 0);
        // Draw the red lines that separate the lanes
        canvas.drawRect(canvas.getWidth() / 3 - 15, 0, canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawRect(
                2 * canvas.getWidth() / 3 - 15, 0, 2 * canvas.getWidth() / 3 + 15, 2500, colour);
        canvas.drawText(Long.toString(totalTime), 600, 200, time);
    }
}