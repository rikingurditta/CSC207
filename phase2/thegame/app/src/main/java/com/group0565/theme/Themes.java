package com.group0565.theme;

import com.example.thegame.R;

import java.util.HashMap;
import java.util.Map;

public enum Themes {
    DARK(R.style.AppThemeDark),
    LIGHT(R.style.AppThemeLight);

    private static HashMap<Integer, Themes> map = new HashMap<>();

    static {
        for (Themes Theme : Themes.values()) {
            map.put(Theme.value, Theme);
        }
    }

    private int value;

    Themes(int value) {
        this.value = value;
    }

    public static Themes valueOf(int Theme) {
        return map.get(Theme);
    }

    public static Themes getDefault() {
        return Themes.LIGHT;
    }

    public int getValue() {
        return value;
    }
}
