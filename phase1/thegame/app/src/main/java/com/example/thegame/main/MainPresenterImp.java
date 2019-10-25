package com.example.thegame.main;

import com.example.thegame.main.navigationCommands.Game1Command;
import com.example.thegame.main.navigationCommands.Game2Command;
import com.example.thegame.main.navigationCommands.Game3Command;
import com.example.thegame.main.navigationCommands.NavigationDirector;
import com.group0565.errorHandlers.ExceptionErrorHandler;
import com.group0565.errorHandlers.IErrorHandler;
import com.group0565.users.IUsersInteractor;
import com.group0565.users.UsersInteractorFirebaseImpl;

import com.example.thegame.main.MainMVP.MainPresenter;
import com.example.thegame.main.MainMVP.MainView;

/** Implementation of the MainPresenter */
public class MainPresenterImp implements MainPresenter {

  /** Reference to the attached view */
  private MainView mainView;

  /** Reference to the Navigation Director for game selection */
  private NavigationDirector navDir;

  /** Reference to the UserService */
  private IUsersInteractor mUserInteractor;

  /** Reference to the ErrorHandler */
  private IErrorHandler<Exception> mErrorHandler;

  /**
   * Instantiate a new MainPresenterImp, fill navigational director, and listen for changes in User
   * Auth status
   *
   * @param mainView
   */
  MainPresenterImp(MainView mainView) {
    this.mainView = mainView;

    navDir = new NavigationDirector();
    navDir.register(GAMEID.GAME1, new Game1Command(mainView));
    navDir.register(GAMEID.GAME2, new Game2Command(mainView));
    navDir.register(GAMEID.GAME3, new Game3Command(mainView));

    this.mUserInteractor = UsersInteractorFirebaseImpl.getInstance();
    this.mErrorHandler = ExceptionErrorHandler.getInstance();

    mUserInteractor
        .getUserObservable()
        .observe(
            mainView.getLifeCycleOwner(),
            iUser -> {
              if (iUser.isConnected()) {
                mainView.showNormalScreen();
              } else {
                mainView.showNoUserScreen();
              }
            });
  }

  /**
   * Choose which game to go to
   *
   * @param id The ID of the target game
   */
  @Override
  public void selectGame(GAMEID id) {
    try {
      navDir.execute(id);
    } catch (UnsupportedOperationException ex) {
      mErrorHandler.Alert(this.mainView, ex, "Operation not implemented");
    }
  }

  /** Destroy all references in this object */
  @Override
  public void onDestroy() {
    this.mainView = null;
  }
}
