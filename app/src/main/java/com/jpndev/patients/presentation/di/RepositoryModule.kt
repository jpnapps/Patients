package com.jpndev.newsapiclient.presentation.di


import com.jpndev.patients.data.repository.AppRepositoryImpl
import com.jpndev.patients.data.repository.dataSource.LocalDataSource
import com.jpndev.patients.data.repository.dataSource.RemoteDataSource
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.patients.domain.repository.AppRepository
import com.jpndev.patients.domain.usecase.UseCase
import com.jpndev.patients.ui.patient.PatientRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAppRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): AppRepository {
        return AppRepositoryImpl(remoteDataSource,localDataSource)
    }

    @Singleton
    @Provides
    fun providePatientRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        logsource: LogSourceImpl
    ): PatientRepository {
        return PatientRepository(remoteDataSource,localDataSource,logsource)
    }

}














