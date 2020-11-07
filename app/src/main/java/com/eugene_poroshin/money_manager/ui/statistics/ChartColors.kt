package com.eugene_poroshin.money_manager.ui.statistics

import android.graphics.Color

object ChartColors {
    @JvmField
    val MATERIAL_COLORS = intArrayOf(
        rgb("#FF5252"),
        rgb("#FF9800"),
        rgb("#FFEB3B"),
        rgb("#8BC34A"),
        rgb("#03A9F4"),
        rgb("#536DFE"),
        rgb("#E040FB"),
        rgb("#CDDC39")
    )

    fun rgb(hex: String): Int {
        val color = hex.replace("#", "").toLong(16).toInt()
        val r = color shr 16 and 0xFF
        val g = color shr 8 and 0xFF
        val b = color shr 0 and 0xFF
        return Color.rgb(r, g, b)
    }
}