package com.group0565.engine.interfaces;

import com.group0565.engine.android.AndroidTypeface;

public abstract class Typeface {
    public static final Typeface DEFAULT_NORMAL = create(null, Style.NORMAL);
    public static final Typeface DEFAULT_BOLD = create(null, Style.BOLD);
    public static final Typeface DEFAULT_ITALIC = create(null, Style.ITALIC);
    public static final Typeface DEFAULT_BOLD_ITALIC = create(null, Style.BOLD_ITALIC);

    private String family = null;
    private Style style = Style.NORMAL;

    public Typeface() {
    }

    public Typeface(String family, Style style) {
        this.family = family;
        this.style = style;
    }

    public static Typeface create(String family, Style style){
        return new AndroidTypeface(family, style);
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public enum  Style{
        NORMAL(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);
        private final int value;
        Style(int i) {
            value = i;
        }

        public int getValue() {
            return value;
        }
    }
}
