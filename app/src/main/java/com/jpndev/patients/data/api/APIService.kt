package com.jpndev.patients.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Streaming


interface APIService {

    @Streaming
    @GET
    fun downloadFileWithDynamicUrlSync(@Url fileUrl: String): Call<ResponseBody>

    @Streaming
    @GET
    suspend fun downloadFileWithDynamicUrlSyncResponse(@Url fileUrl: String): Response<ResponseBody>
}