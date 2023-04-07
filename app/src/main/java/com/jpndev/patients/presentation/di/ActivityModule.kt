package com.jpndev.patients.presentation.di

import android.content.Context
import com.jpndev.patients.custom.LoadingIndicator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @Provides
    fun provideContext(@ActivityContext context: Context): Context {
        return context
    }


    @Provides
    fun provideLoadingIndicator(@ActivityContext  context: Context): LoadingIndicator {
        return LoadingIndicator(context)
    }
}