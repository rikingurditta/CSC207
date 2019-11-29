package com.group0565.menuUI.main.menuCommands;

import com.group0565.menuUI.main.MainMVP;
import com.group0565.menuUI.main.enums.ActivityNames;

/** An implementation of NavigationCommand to move to Settings screen */
public class SettingsMenuCommand implements MenuCommand {
  /** A reference for the view that will switch activities */
  private MainMVP.MainView mainView;

  /**
   * Initiates a new SettingsMenuCommand with the view
   *
   * @param mainView The view that will move games
   */
  public SettingsMenuCommand(MainMVP.MainView mainView) {
    this.mainView = mainView;
  }

  /** Execute the command and move to Settings screen */
  @Override
  public void execute() {
    mainView.goToActivity(ActivityNames.SETTINGS);
  }
}
