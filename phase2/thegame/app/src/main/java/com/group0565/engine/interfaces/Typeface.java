package com.group0565.engine.interfaces;

/** An abstract class representation of TypeFace */
public abstract class Typeface {
  /** The default Typeface for normal text */
  public static final Typeface DEFAULT_NORMAL = TypeFaceFactory.create(null, Style.NORMAL);
  /** The default Typeface for bold text */
  public static final Typeface DEFAULT_BOLD = TypeFaceFactory.create(null, Style.BOLD);
  /** The default Typeface for italic text */
  public static final Typeface DEFAULT_ITALIC = TypeFaceFactory.create(null, Style.ITALIC);
  /** The default Typeface for bold italic text */
  public static final Typeface DEFAULT_BOLD_ITALIC =
      TypeFaceFactory.create(null, Style.BOLD_ITALIC);

  /** The font family */
  private String family = null;
  /** The font style */
  private Style style = Style.NORMAL;

  /** Create a new Typeface */
  public Typeface() {}

  /**
   * Create a new Typeface
   *
   * @param family The font family
   * @param style The font style
   */
  public Typeface(String family, Style style) {
    this.family = family;
    this.style = style;
  }

  /**
   * Get the font family
   *
   * @return The font family
   */
  public String getFamily() {
    return family;
  }

  /**
   * Set the font family
   *
   * @param family The target font family
   */
  public void setFamily(String family) {
    this.family = family;
  }

  /**
   * Get the font style
   *
   * @return The font style
   */
  public Style getStyle() {
    return style;
  }

  /**
   * Set the font style
   *
   * @param style The target font style
   */
  public void setStyle(Style style) {
    this.style = style;
  }

  /** An enum for text styles */
  public enum Style {
    NORMAL(0),
    BOLD(1),
    ITALIC(2),
    BOLD_ITALIC(3);

    /** The enum value */
    private final int value;

    /**
     * Create a new style
     *
     * @param newValue The int value of the style
     */
    Style(int newValue) {
      value = newValue;
    }

    /**
     * Get the value of the enum
     *
     * @return The value
     */
    public int getValue() {
      return value;
    }
  }
}
