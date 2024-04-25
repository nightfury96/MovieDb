package com.nightfury.moviedb.presentation.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun Activity.hideKeyboard(): Boolean {
    window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    val inputManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            ?: return false
    return currentFocus != null && inputManager.hideSoftInputFromWindow(
        currentFocus!!.windowToken,
        0
    )
}