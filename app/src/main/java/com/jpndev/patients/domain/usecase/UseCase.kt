package com.jpndev.patients.domain.usecase

import android.content.Context
import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.patients.data.util.Resource
import com.jpndev.patients.domain.repository.AppRepository
import com.jpndev.patients.utils.PrefUtils
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

/* @Inject constructor */
class UseCase(
    private val repository: AppRepository,
    private val appContext: Context,
    public val prefUtils: PrefUtils,
    public val logsource: LogSourceImpl
) {
    suspend fun executeDeletePItrm(item: Patient) = repository.deletePItem(item)
    suspend fun executeSavePItem(item: Patient): Long = repository.savePItem(item)
    suspend fun executeUpdatePItem(item: Patient): Int = repository.updatePItem(item)
    fun executeGetPList(): Flow<List<Patient>> {
        return repository.getPItems()
    }
    suspend fun executeDownloadRequest(url: String): Resource<ResponseBody> {
        logsource.addLog("UseCase executeDownloadRequest : ${Thread.currentThread().name}")
        return repository.getDownloadBody(url)
    }
}