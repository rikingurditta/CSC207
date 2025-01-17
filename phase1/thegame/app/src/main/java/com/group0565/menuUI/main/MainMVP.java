package com.group0565.menuUI.main;

import com.group0565.basePatterns.mvp.BaseMVP;
import com.group0565.menuUI.main.enums.GameID;
import com.group0565.menuUI.main.enums.MenuOptionID;

/** An interface for the Main module MVP */
public interface MainMVP {
  /** An interface for the Main presenter */
  interface MainPresenter extends BaseMVP.BasePresenter {
    /**
     * Choose which game to go to
     *
     * @param id The ID of the target game
     */
    void selectGame(GameID id);

    /** Sign out the current user */
    void signOut();

    /**
     * Checks whether the menu should appear
     *
     * @return True if menu should appear and false otherwise
     */
    boolean isMenuAvailable();

    /**
     * Choose which command to execute based on id of clicked menu item
     *
     * @param id The id of the clicked menu item
     * @return True if action was handled and false otherwise
     */
    boolean handleMenuClick(MenuOptionID id);
  }

  /** An interface for the Main view */
  interface MainView extends BaseMVP.BaseView {
    /** Show the "no user connected" screen */
    void showNoUserScreen();

    /** Show the normal Main screen */
    void showNormalScreen();

    /** Redirect to game 1 activity */
    void goToGame1();

    /** Redirect to game 1 activity */
    void goToGame2();

    /** Redirect to game 1 activity */
    void goToGame3();

    /** Redirect to settings activity */
    void goToSettings();

    /** Redirect to statistics activity */
    void goToStatistics();

    /** Redirect to achievements activity */
    void goToAchievements();
  }
}
