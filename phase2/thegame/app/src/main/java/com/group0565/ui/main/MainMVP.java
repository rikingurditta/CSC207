package com.group0565.ui.main;

import com.group0565.basepatterns.mvp.BaseMVP;
import com.group0565.ui.main.enums.ActivityNames;
import com.group0565.ui.main.enums.GameID;
import com.group0565.ui.main.enums.MenuOptionID;

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

    /**
     * Launch designated activity
     *
     * @param targetActivity The target activity
     */
    void goToActivity(ActivityNames targetActivity);
  }
}
