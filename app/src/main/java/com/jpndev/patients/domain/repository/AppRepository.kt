package com.jpndev.patients.domain.repository

import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface AppRepository {

    suspend fun savePItem(article: Patient): Long
    suspend fun updatePItem(article: Patient): Int
    suspend fun deletePItem(article: Patient)
    fun getPItems(): Flow<List<Patient>>
    suspend fun getDownloadBody(url: String):  Resource<ResponseBody>
}