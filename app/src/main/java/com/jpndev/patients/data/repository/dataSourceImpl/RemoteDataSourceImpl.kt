package com.jpndev.patients.data.repository.dataSourceImpl

import com.jpndev.patients.data.api.APIService
import com.jpndev.patients.data.repository.dataSource.RemoteDataSource
import okhttp3.ResponseBody
import retrofit2.Response

class RemoteDataSourceImpl(
        private val apiService: APIService
):RemoteDataSource {
    override suspend fun getDownloadBody(url: String): Response<ResponseBody> {
        return  apiService.downloadFileWithDynamicUrlSyncResponse(url)
    }

}