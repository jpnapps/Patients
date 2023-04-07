package com.jpndev.newsapiclient.presentation.di

import com.jpndev.patients.data.api.APIService
import com.jpndev.patients.data.repository.dataSource.RemoteDataSource
import com.jpndev.patients.data.repository.dataSourceImpl.RemoteDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        apiService: APIService
    ): RemoteDataSource {
       return RemoteDataSourceImpl(apiService)
    }

}












