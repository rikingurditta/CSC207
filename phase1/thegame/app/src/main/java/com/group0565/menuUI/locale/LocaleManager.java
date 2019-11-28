/*
   Adapted from https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
*/

package com.group0565.menuUI.locale;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/** A manager for locale changes */
public class LocaleManager {
  /**
   * Update the resources of the given context to the required language
   *
   * @param context The context to change its language
   * @param language The language code to change to
   * @return The modified context
   */
  public static Context updateResources(Context context, String language) {
    Locale locale = new Locale(language);
    Locale.setDefault(locale);

    Resources res = context.getResources();
    Configuration config = new Configuration(res.getConfiguration());
    config.setLocale(locale);
    context = context.createConfigurationContext(config);

    return context;
  }
}
