package com.jpndev.patients.presentation.di

import android.app.Application
import com.jpndev.patients.domain.usecase.UseCase
import com.jpndev.patients.presentation.ui.MainViewModelFactory
import com.jpndev.patients.ui.backup.BackupVMF
import com.jpndev.patients.ui.dashboard.SearchPatientVMF
import com.jpndev.patients.ui.manage_log.ViewLogosViewModelFactory
import com.jpndev.patients.ui.patient.*
import com.jpndev.patients.ui.splash.SplashVMFactory

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideAddPItemViewModelFactory(
        application: Application,
        repository: PatientRepository
    ): AddPatientViewModelFactory {
        return AddPatientViewModelFactory(
            application,
            repository
        )
    }

    @Singleton
    @Provides
    fun providePatientDetailViewModelFactory(
        application: Application,
        repository: PatientRepository
    ): PatientDetailViewModelFactory {
        return PatientDetailViewModelFactory(
            application,
            repository
        )
    }

    @Singleton
    @Provides
    fun provideViewLogosViewModelFactory(
        application: Application,
        useCase: UseCase
    ): ViewLogosViewModelFactory {
        return ViewLogosViewModelFactory(
            application,
            useCase

        )
    }

    @Singleton
    @Provides
    fun provideMainViewModelFactory(
        application: Application,
        useCase: UseCase,
        repository: PatientRepository
    ): MainViewModelFactory {
        return MainViewModelFactory(
            application,
            useCase,
            repository
        )
    }

    @Singleton
    @Provides
    fun provideBackupVMF(
        application: Application,
        repository: PatientRepository
    ): BackupVMF {
        return BackupVMF(
            application,
            repository
        )
    }

    @Singleton
    @Provides
    fun provideSearchPatientVMF(
        application: Application,
        repository: PatientRepository
    ): SearchPatientVMF {
        return SearchPatientVMF(
            application,
            repository
        )
    }

    @Singleton
    @Provides
    fun provideSplashVMFactory(
        application: Application,
        repository: PatientRepository
    ): SplashVMFactory {
        return SplashVMFactory(
            application,
            repository
        )
    }
}