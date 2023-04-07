package com.jpndev.newsapiclient.presentation.di

import com.jpndev.patients.data.db.DAO
import com.jpndev.patients.data.repository.dataSource.LocalDataSource
import com.jpndev.patients.data.repository.dataSourceImpl.LocalDataSourceImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(
        pitemDAO: DAO
    ): LocalDataSource {
       return LocalDataSourceImpl(pitemDAO)
    }

}












