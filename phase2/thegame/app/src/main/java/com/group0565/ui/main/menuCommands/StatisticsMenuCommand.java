package com.group0565.ui.main.menuCommands;

import com.group0565.ui.main.MainMVP;
import com.group0565.ui.main.enums.ActivityNames;

/** An implementation of NavigationCommand to move to Statistics screen */
public class StatisticsMenuCommand implements MenuCommand {
  /** A reference for the view that will switch activities */
  private MainMVP.MainView mainView;

  /**
   * Initiates a new StatisticsMenuCommand with the view
   *
   * @param mainView The view that will move games
   */
  public StatisticsMenuCommand(MainMVP.MainView mainView) {
    this.mainView = mainView;
  }

  /** Execute the command and move to Statistics screen */
  @Override
  public void execute() {
    mainView.goToActivity(ActivityNames.STATISTICS);
  }
}
