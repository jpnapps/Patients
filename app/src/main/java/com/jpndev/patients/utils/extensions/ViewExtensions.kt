package com.jpndev.patients.utils.extensions

import android.content.Context
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.Group


/**
 * Method to click action on Group
 * @param listener View.OnClickListener
 */
fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

/**
 * Method to hide Keyboad
 * @param listener View.OnClickListener
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Method to change title color of menu
 * @param color color
 */
fun MenuItem.setTitleColor(color: Int) {
    val spannable = SpannableString(title)
    spannable.setSpan(ForegroundColorSpan(color), 0, spannable.length, 0)
    setTitle(spannable)
}