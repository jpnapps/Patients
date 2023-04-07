package com.jpndev.patients.ui.backup

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jpndev.patients.ui.patient.PatientRepository

class BackupVMF (
    private val app: Application,
    private val repository: PatientRepository

): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BackupViewModel(
            app,
            repository,
        ) as T
    }
}