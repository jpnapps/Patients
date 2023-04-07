package com.jpndev.patients.presentation.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.jpndev.patients.domain.usecase.UseCase
import com.jpndev.patients.ui.patient.PatientRepository

class MainViewModelFactory (
    private val app: Application,
    public val usecase: UseCase ,
    private val repository: PatientRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            app,
            usecase,
            repository
        ) as T
    }


}