package com.thegame.main.navigationCommands;

import com.thegame.main.MainMVP;

/**
 * An implementation of NavigationCommand to move to Game 1
 */
public class Game1Command implements NavigationCommand {
    /**
     * A reference for the view that will switch games
     */
    private MainMVP.MainView mainView;

    /**
     * Initiates a new Game1Command with the view
     *
     * @param mainView The view that will move games
     */
    public Game1Command(MainMVP.MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * Execute the command and move to Game 1
     */
    @Override
    public void execute() {
        mainView.goToGame1();
    }
}
