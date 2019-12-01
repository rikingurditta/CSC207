package com.group0565.menuUI.main.navigationCommands;

import com.group0565.menuUI.main.MainMVP;
import com.group0565.menuUI.main.enums.ActivityNames;

/** An implementation of NavigationCommand to move to Racer */
public class RacerCommand implements NavigationCommand {
  /** A reference for the view that will switch games */
  private MainMVP.MainView mainView;

  /**
   * Initiates a new RacerCommand with the view
   *
   * @param mainView The view that will move games
   */
  public RacerCommand(MainMVP.MainView mainView) {
    this.mainView = mainView;
  }

  /** Execute the command and move to Game 3 */
  @Override
  public void execute() {
    mainView.goToActivity(ActivityNames.RACER);
  }
}
