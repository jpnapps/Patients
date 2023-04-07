package com.jpndev.patients.ui.interfaces

interface OnDismissListner {
    abstract fun onDismiss(obj: Any?=null)
}
interface OnTwoButtonDialogListner {
    abstract fun onRightButtonClick(obj: Any?=null)
    abstract fun onLeftButtonClick(obj: Any?=null)
    abstract fun onDismiss(obj: Any?=null)
}