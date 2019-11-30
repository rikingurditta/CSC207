package com.group0565.engine.interfaces;

import com.group0565.engine.android.AndroidTypeface;

/** A factory for creating TypeFace concretions */
public class TypeFaceFactory {
  /**
   * Create a new concrete Typeface
   *
   * @param family The typeface family
   * @param style The typeface style
   * @return A new instance of typeface with the given family and style
   */
  public static Typeface create(String family, Typeface.Style style) {
    return new AndroidTypeface(family, style);
  }
}
