package com.pixabay.core.common.util

import java.text.NumberFormat
import java.util.Locale

fun Int.formatNumber(): String {
    return NumberFormat.getNumberInstance(Locale.KOREAN).format(this)
}