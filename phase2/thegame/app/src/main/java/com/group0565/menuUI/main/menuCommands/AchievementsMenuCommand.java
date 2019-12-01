package com.group0565.menuUI.main.menuCommands;

import com.group0565.menuUI.main.MainMVP;
import com.group0565.menuUI.main.enums.ActivityNames;

public class AchievementsMenuCommand implements MenuCommand {
  /** A reference for the view that will switch activities */
  private MainMVP.MainView mainView;

  /**
   * Initiates a new TsuCommand with the view
   *
   * @param mainView The view that will move games
   */
  public AchievementsMenuCommand(MainMVP.MainView mainView) {
    this.mainView = mainView;
  }

  /** Execute the command and move to Achievements screen */
  @Override
  public void execute() {
    mainView.goToActivity(ActivityNames.ACHIEVEMENTS);
  }
}
