package com.kamira.mortyverse.presentation.extensions

import android.util.TypedValue
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.applyToolbarInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val topInset =
            insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            androidx.appcompat.R.attr.actionBarSize,
            typedValue,
            true
        )
        val actionBarSize = TypedValue.complexToDimensionPixelSize(
            typedValue.data,
            resources.displayMetrics
        )
        val params = view.layoutParams
        params.height = topInset + actionBarSize
        view.layoutParams = params
        view.setPadding(
            view.paddingLeft,
            topInset,
            view.paddingRight,
            view.paddingBottom
        )
        insets
    }
}

fun View.applyTopInsets() {}
fun View.applyBottomInsets() {}
fun View.applySystemBarInsets() {}