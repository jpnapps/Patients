package com.jpndev.patients.ui.dashboard

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jpndev.patients.ui.patient.PatientRepository

class SearchPatientVMF (
    private val app: Application,
    private val repository: PatientRepository

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(
            app,
            repository,
        ) as T
    }
}