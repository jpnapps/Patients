 package com.jpndev.patients.presentation.di

import android.app.Application
import androidx.room.Room
import com.jpndev.patients.data.db.AppDatabase
import com.jpndev.patients.data.db.DAO

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(app: Application): AppDatabase {
        return  Room.databaseBuilder(app,
            AppDatabase::class.java,"jpndev_patients_db").
        /*    addMigrations(MIGRATION_5_6).
            addMigrations(MIGRATION_6_7).*/
            build()
    }
// fallbackToDestructiveMigration().
    @Singleton
    @Provides
    fun providesDAO(database: AppDatabase): DAO {
        return  database.appDao()
    }
}














