package com.kamira.mortyverse.presentation.extensions

import android.util.TypedValue
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.applyToolbarInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val topInset =
            insets.getInsets(
                WindowInsetsCompat.Type.statusBars()
            ).top
        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            androidx.appcompat.R.attr.actionBarSize,
            typedValue,
            true
        )
        val actionBarSize =
            TypedValue.complexToDimensionPixelSize(
                typedValue.data,
                resources.displayMetrics
            )
        view.layoutParams.height =
            topInset + actionBarSize
        view.setPadding(
            view.paddingLeft,
            topInset,
            view.paddingRight,
            view.paddingBottom
        )
        insets
    }
}

fun View.applyTopInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val topInset =
            insets.getInsets(
                WindowInsetsCompat.Type.statusBars()
            ).top
        view.setPadding(
            view.paddingLeft,
            topInset,
            view.paddingRight,
            view.paddingBottom
        )
        insets
    }
}

fun View.applyBottomInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val bottomInset =
            insets.getInsets(
                WindowInsetsCompat.Type.navigationBars()
            ).bottom
        view.setPadding(
            view.paddingLeft,
            view.paddingTop,
            view.paddingRight,
            bottomInset
        )
        insets
    }
}

fun View.applySystemBarInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemInsets =
            insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
        view.setPadding(
            systemInsets.left,
            systemInsets.top,
            systemInsets.right,
            systemInsets.bottom
        )
        insets
    }
}

fun View.show(isVisible: Boolean) {
    visibility =
        if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}