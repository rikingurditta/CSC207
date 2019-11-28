package com.group0565.racer.menus;

import com.group0565.engine.gameobjects.GameMenu;
import com.group0565.engine.interfaces.Canvas;
import com.group0565.engine.interfaces.Observable;

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

    }

    /**
     * Renders this object on the screen
     *
     * @param canvas The Canvas on which to draw
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}