package com.group0565.menuUI.main.navigationCommands;

import com.group0565.menuUI.main.MainMVP;

/**
 * An implementation of NavigationCommand to move to Game 1
 */
public class Game2Command implements NavigationCommand {
    /**
     * A reference for the view that will switch games
     */
    private MainMVP.MainView mainView;

    /**
     * Initiates a new Game2Command with the view
     *
     * @param mainView The view that will move games
     */
    public Game2Command(MainMVP.MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * Execute the command and move to Game 2
     */
    @Override
    public void execute() {
        mainView.goToGame2();
  }
}
