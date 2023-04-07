package com.jpndev.patients.ui.splash

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jpndev.patients.ui.patient.PatientRepository

class SplashVMFactory(
    val app: Application,
    val repository: PatientRepository

) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(
            app,
            repository,
            ) as T
    }
}