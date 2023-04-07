package com.jpndev.patients.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibleIf")
fun setVisibility(view: View, text: String) {
    view.visibility = if (text.isNotEmpty()) View.VISIBLE else View.GONE
}
/*
@BindingAdapter("app:visibleIf")
fun setVisibility(view: TextView, text: String) {
    view.visibility = if (text.isNotEmpty()) View.VISIBLE else View.GONE
}*/
