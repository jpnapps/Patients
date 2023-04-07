package com.jpndev.patients.ui.patient

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PatientDetailViewModelFactory (
    private val app: Application,
    private val repository: PatientRepository

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PatientDetailViewModel(
            app,
            repository,
        ) as T
    }
}