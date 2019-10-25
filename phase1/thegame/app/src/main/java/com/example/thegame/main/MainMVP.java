package com.example.thegame.main;

import com.group0565.mvp.BaseMVP;

/** An interface for the Main module MVP */
public interface MainMVP {
  /** An interface for the Main presenter */
  interface MainPresenter extends BaseMVP.BasePresenter {
    /**
     * Choose which game to go to
     *
     * @param id The ID of the target game
     */
    void selectGame(GAMEID id);
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
  }
}
