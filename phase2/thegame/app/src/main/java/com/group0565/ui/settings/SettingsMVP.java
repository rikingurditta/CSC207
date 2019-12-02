package com.group0565.ui.settings;

import com.group0565.basepatterns.mvp.BaseMVP;

/** An interface for the Settings module MVP */
public interface SettingsMVP extends BaseMVP {
  /** An interface for the Settings presenter */
  interface SettingsPresenter extends BaseMVP.BasePresenter {}

  /** An interface for the Settings view */
  interface SettingsView extends BaseMVP.BaseView, MySettingsFragment.RefreshCallback {}
}
