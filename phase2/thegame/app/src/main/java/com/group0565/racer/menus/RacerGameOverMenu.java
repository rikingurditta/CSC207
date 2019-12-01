package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;
import com.group0565.engine.interfaces.ObservationEvent;
import com.group0565.engine.interfaces.Paint;
import com.group0565.engine.render.LanguageText;
import com.group0565.math.Vector;
import com.group0565.racer.core.RacerEngine;
import com.group0565.theme.Themes;

public class RacerGameOverMenu extends GameMenu {
    private Button restartButton;
    private Button exitButton;
    private RacerEngine engine;

    public RacerGameOverMenu(Vector size, RacerEngine engine) {
        super(size);
        this.engine = engine;
    }

    public void init() {
        restartButton = new Button(new Vector(100, 1750),
                new Vector(150, 150),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0));
        this.adopt(restartButton);

        exitButton = new Button(new Vector(300, 1750),
                new Vector(150, 150),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0),
                getEngine()
                        .getGameAssetManager()
                        .getTileSheet("RacerButton", "RacerButton")
                        .getTile(0, 0));
        this.adopt(exitButton);
    }

    public void observe(Observable observable, ObservationEvent observationEvent) {
        if (observable == restartButton && observationEvent.getMsg().equals(Button.EVENT_DOWN)) {
            setEnable(false);
        }
        else if (observable == exitButton && observationEvent.getMsg().equals(Button.EVENT_DOWN)) {

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint font = Paint.createInstance();
        font.setARGB(255, 255, 255, 255);
        font.setTextSize(96);
        canvas.drawRGB(0, 0, 0);

        LanguageText gameOver = new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), "Racer", "Game Over");
        LanguageText score = new LanguageText(getGlobalPreferences(), getEngine().getGameAssetManager(), "Racer", "Score");

        canvas.drawText(gameOver.getValue(), 50, 200, font);
        canvas.drawText(score.getValue() + engine.getTotalTime(), 50, 400, font);


    }
}
