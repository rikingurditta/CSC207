package com.group0565.engine.android;

import com.group0565.engine.interfaces.Typeface;

import java.lang.reflect.Type;

/** Implementation of Typeface for Android */
public class AndroidTypeface extends Typeface {

  /** Creates a new AndroidTypeFace */
  public AndroidTypeface() {
    super();
  }

  /**
   * Creates a Typeface of family and Style
   *
   * @param family The family of this Typeface
   * @param style The Style of this Typeface
   */
  public AndroidTypeface(String family, Typeface.Style style) {
    super(family, style);
  }

  /**
   * Converts this Typeface to the native android.graphics.Typeface
   *
   * @return The android.graphics.Typeface equivalent
   */
  public android.graphics.Typeface asNativeTypeface() {
    return android.graphics.Typeface.create(getFamily(), getStyle().getValue());
  }
}
