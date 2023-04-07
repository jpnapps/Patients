package com.jpndev.patients.data.repository.dataSource

import okhttp3.ResponseBody
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getDownloadBody(url: String): Response<ResponseBody>
}