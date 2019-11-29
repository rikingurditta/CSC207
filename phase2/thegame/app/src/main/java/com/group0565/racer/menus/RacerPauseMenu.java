package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.Button;
import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.math.Vector;
import com.group0565.racer.core.RacerEngine;
import com.group0565.theme.Themes;

public class RacerPauseMenu extends GameMenu {
    private Button resumeButton;
    private Button exitButton;
    private RacerEngine engine;

    public RacerPauseMenu(Vector size, RacerEngine engine) {
        super(size);
        this.engine = engine;
    }

    public void init() {
        resumeButton = new Button(null);
        this.adopt(resumeButton);

        exitButton = new Button(null);
        this.adopt(exitButton);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(0, 0, 0);

    }
}
