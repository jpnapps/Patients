package com.jpndev.patients.ui.manage_log

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jpndev.patients.domain.usecase.UseCase

class ViewLogosViewModelFactory (
    private val app: Application,
    public val usecase: UseCase

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewLogosViewModel(
            app,
            usecase,

        ) as T
    }


}