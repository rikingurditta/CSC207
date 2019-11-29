package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Paint;
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

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint font = Paint.createInstance();
        font.setARGB(255, 255, 255, 255);
        font.setTextSize(96);

        canvas.drawRGB(0, 0, 0);
        canvas.drawText("Score: " + engine.getTotalTime(), 100, 200, font);

    }
}
