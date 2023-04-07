package com.jpndev.patients.presentation.di

import android.content.Context
import com.jpndev.newsapiclient.presentation.PItemAdapter
import com.jpndev.newsapiclient.presentation.PatientSearchAdapter

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdpaterModule {

   @Singleton
   @Provides
   fun providePItemAdapter(
       context: Context
   ): PItemAdapter {
      return PItemAdapter(context)
   }

   @Singleton
   @Provides
   fun providePatientSearchAdapter(
      context: Context
   ): PatientSearchAdapter {
      return PatientSearchAdapter(context)
   }
}


















