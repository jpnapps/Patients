package com.jpndev.patients.data.repository

import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.repository.dataSource.LocalDataSource
import com.jpndev.patients.data.repository.dataSource.RemoteDataSource
import com.jpndev.patients.data.util.Resource
import com.jpndev.patients.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

import retrofit2.Response

class AppRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
):AppRepository {


    override suspend fun savePItem(item: Patient) : Long{
      return  localDataSource.savePatientoDb(item)
    }

    override suspend fun updatePItem(item: Patient): Int {
        return  localDataSource.updatePatient(item)
    }

    override suspend fun deletePItem(item: Patient) {
        localDataSource.deletePatient(item)
    }

    override fun getPItems(): Flow<List<Patient>> {
        return localDataSource.getPatientsFromDB()
    }

    override suspend fun getDownloadBody(url: String): Resource<ResponseBody> {
        return responseToResourceBody(remoteDataSource.getDownloadBody(url))
    }

    private fun responseToResourceBody(response: Response<ResponseBody>):Resource<ResponseBody>{
        response.body()?.let {
            result->
            return Resource.Success(result)
        }

        return Resource.Error(response.message())
    }
}