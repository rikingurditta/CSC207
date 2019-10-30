package com.menu.main;

/** An injector for the MainPresenter */
class MainPresenterInjector {
  /**
   * Injects the caller with an implementation of MainPresenter
   *
   * @param view The view to be associated with the MainPresenter
   * @return An instance of a MainPresenter
   */
  static MainMVP.MainPresenter inject(MainMVP.MainView view) {
    return new MainPresenterImp(view);
  }
}
