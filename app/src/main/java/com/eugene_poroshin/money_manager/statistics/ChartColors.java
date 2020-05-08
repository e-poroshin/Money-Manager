package com.eugene_poroshin.money_manager.statistics;

import android.graphics.Color;

public class ChartColors {

    public static final int[] MATERIAL_COLORS = {
            rgb("#FF5252"), rgb("#FF9800"), rgb("#FFEB3B"), rgb("#8BC34A"),
            rgb("#03A9F4"), rgb("#536DFE"), rgb("#E040FB"), rgb("#CDDC39")
    };

    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }
}
