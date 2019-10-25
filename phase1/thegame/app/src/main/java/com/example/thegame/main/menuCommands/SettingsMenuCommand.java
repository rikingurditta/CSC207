package com.example.thegame.main.menuCommands;

import com.example.thegame.main.MainMVP;

public class SettingsMenuCommand implements MenuCommand {
  /** A reference for the view that will switch activities */
  private MainMVP.MainView mainView;

  /**
   * Initiates a new Game1Command with the view
   *
   * @param mainView The view that will move games
   */
  public SettingsMenuCommand(MainMVP.MainView mainView) {
    this.mainView = mainView;
  }

  /** Execute the command and move to Game 1 */
  @Override
  public void execute() {
    mainView.goToSettings();
  }
}
