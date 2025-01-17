package com.group0565.menuUI.main;

import com.group0565.menuUI.main.menuCommands.AchievementsMenuCommand;
import com.group0565.preferences.PreferencesInjector;
import com.group0565.menuUI.main.enums.GameID;
import com.group0565.menuUI.main.enums.MenuOptionID;
import com.group0565.menuUI.main.menuCommands.MenuDirector;
import com.group0565.menuUI.main.menuCommands.SettingsMenuCommand;
import com.group0565.menuUI.main.menuCommands.SignOutMenuCommand;
import com.group0565.menuUI.main.menuCommands.StatisticsMenuCommand;
import com.group0565.menuUI.main.navigationCommands.Game1Command;
import com.group0565.menuUI.main.navigationCommands.Game2Command;
import com.group0565.menuUI.main.navigationCommands.Game3Command;
import com.group0565.menuUI.main.navigationCommands.NavigationDirector;
import com.group0565.errorHandlers.ExceptionErrorHandler;
import com.group0565.errorHandlers.IErrorHandler;
import com.group0565.users.IUser;
import com.group0565.users.IUsersInteractor;

import com.group0565.menuUI.main.MainMVP.MainPresenter;
import com.group0565.menuUI.main.MainMVP.MainView;
import com.group0565.theme.ThemeManager;

/** Implementation of the MainPresenter */
public class MainPresenterImp implements MainPresenter {

  /** Reference to the attached view */
  private MainView mainView;

  /** Reference to the Navigation Director for game selection */
  private NavigationDirector navDir;

  /** Reference to the MenuDirector for menu options */
  private MenuDirector menuDir;

  /** Reference to the UserService */
  private IUsersInteractor mUserInteractor;

  /** Reference to the ErrorHandler */
  private IErrorHandler<Exception> mErrorHandler;

  /** Reference to the connected user object */
  private IUser mUser;

  /**
   * Instantiate a new MainPresenterImp, fill navigational director, and listen for changes in User
   * Auth status
   *
   * @param mainView The calling MainView to attach to
   */
  MainPresenterImp(MainView mainView) {
    this.mainView = mainView;

    navDir = new NavigationDirector();
    navDir.register(GameID.GAME1, new Game1Command(mainView));
    navDir.register(GameID.GAME2, new Game2Command(mainView));
    navDir.register(GameID.GAME3, new Game3Command(mainView));

    menuDir = new MenuDirector();
    menuDir.register(MenuOptionID.SETTINGS, new SettingsMenuCommand(mainView));
    menuDir.register(MenuOptionID.STATISTICS, new StatisticsMenuCommand(mainView));
    menuDir.register(MenuOptionID.SIGN_OUT, new SignOutMenuCommand(this));
    menuDir.register(MenuOptionID.ACHIEVEMENTS, new AchievementsMenuCommand(mainView));

    this.mUserInteractor = IUsersInteractor.getInstance();
    this.mErrorHandler = ExceptionErrorHandler.getInstance();

    mUserInteractor
        .getUserObservable()
        .observe(
            mainView.getLifeCycleOwner(),
            iUser -> {
              mUser = iUser;
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
  public void selectGame(GameID id) {
    try {
      navDir.execute(id);
    } catch (UnsupportedOperationException ex) {
      mErrorHandler.Alert(this.mainView, ex, "Operation not implemented");
    }
  }

  /** Sign out the current user */
  @Override
  public void signOut() {
    try {
      mUser.signOut();
    } catch (NullPointerException ex) {
      mErrorHandler.Ignore(ex);
    }
  }

  /**
   * Checks whether the menu should appear
   *
   * @return True if menu should appear and false otherwise
   */
  @Override
  public boolean isMenuAvailable() {
    try {
      return mUser.isConnected();
    } catch (NullPointerException ex) {
      mErrorHandler.Ignore(ex);
      return false;
    }
  }

  /**
   * Choose which command to execute based on id of clicked menu item
   *
   * @param id The id of the clicked menu item
   * @return True if action was handled and false otherwise
   */
  @Override
  public boolean handleMenuClick(MenuOptionID id) {
    try {
      menuDir.execute(id);

      return true;
    } catch (UnsupportedOperationException ex) {
      mErrorHandler.Alert(this.mainView, ex, "Operation not implemented");

      return false;
    }
  }

  /** Destroy all references in this object */
  @Override
  public void onDestroy() {
    this.mainView = null;
  }

  /**
   * Gets the current display language
   *
   * @return The current display language
   */
  @Override
  public String getDisplayLanguage() {
    return PreferencesInjector.inject().getLanguage();
  }

  /**
   * Gets the current display theme id
   *
   * @return The current display theme
   */
  @Override
  public int getAppTheme() {
    int themeID = ThemeManager.getTheme(PreferencesInjector.inject().getTheme());
    return themeID;
  }
}
