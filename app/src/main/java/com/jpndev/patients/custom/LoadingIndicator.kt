package com.jpndev.patients.custom

import android.app.Dialog
import android.content.Context
import com.jpndev.patients.R


/**
 * Class to display the custom loading indicator(Progress dialog)
 * Will be used through out the app
 */
class LoadingIndicator (context: Context, isCancelable: Boolean = false) {

    /**
     * Initializing the dialog
     */
    private var dialog = Dialog(context, R.style.loaderDialogTheme).apply {
        setCanceledOnTouchOutside(isCancelable)
        setContentView(R.layout.layout_loading_indicator)
    }

    /**
     * Method to show the loading indicator
     */
    fun show() {
        dialog.apply {
            if (!isShowing) show()
        }
    }

    /**
     * Method to hide the loading indicator
     */
    fun hide() {
        dialog.apply {
            if (isShowing) dismiss()
        }
    }

    /**
     * Method to check if loader visible
     */
    fun isShowing(): Boolean {
        dialog.apply {
            if (isShowing) return true
        }
        return false
    }
}