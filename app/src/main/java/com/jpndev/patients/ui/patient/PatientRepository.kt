package com.jpndev.patients.ui.patient

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jpndev.patients.data.model.EStatus
import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.model.Status
import com.jpndev.patients.data.repository.dataSource.LocalDataSource
import com.jpndev.patients.data.repository.dataSource.RemoteDataSource
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.patients.utils.Common
import com.jpndev.patients.utils.Common.DELAY_2000
import com.jpndev.patients.utils.DIGITS
import com.jpndev.patients.utils.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.io.File

class PatientRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    val logsource: LogSourceImpl
) {

    var image_list: ArrayList<String> = ArrayList()
    var temp = 0

    // observer for list of image live data
    // var imageseLiveData = SingleLiveEvent<ArrayList<String>>()
    var imageseLiveData = MutableLiveData<ArrayList<String>>()
        get() = field

    private var currentPatient: MutableLiveData<Patient?> = MutableLiveData<Patient?>()

    private val statusMessage = MutableLiveData<Event<Status>>()
    val message: LiveData<Event<Status>>
        get() = statusMessage

    val backupStatusMLF = MutableLiveData<Event<Int>>()
    val progressMLF = MutableLiveData<Event<Int>>()

    suspend fun savePatient(item: Patient): Long {
        return localDataSource.savePatientoDb(item)
    }

    suspend fun updatePatient(item: Patient): Int {
        return localDataSource.updatePatient(item)
    }

    suspend fun deletePatient(item: Patient) {
        localDataSource.deletePatient(item)
    }

    fun getPatients(): Flow<List<Patient>> {
        return localDataSource.getPatientsFromDB()
    }

    fun addImage(item: Uri) {
        image_list.add(item.toString())
        imageseLiveData.value = image_list
    }

    fun addImages(items: ArrayList<String>) {
        val newItems = ArrayList(items)
        image_list.clear()
        image_list.addAll(newItems)
        imageseLiveData.value = image_list
        //imageseLiveData.postValue(image_list)
        logsource.addLog("PatientRepository addImages 33 imageseLiveData updated : " + imageseLiveData.value?.size)
    }

    fun clearImageList() {
        image_list.clear()
        logsource.addLog("PatientRepository clearImageList images size : " + image_list.size)
        imageseLiveData.value = image_list
    }

    fun notifyImageList() {
        imageseLiveData.value = image_list
    }

    fun getCurrentPatient() = currentPatient
    fun setCurrentPatient(patient: Patient?) {
        currentPatient.value = patient
    }

    fun getLcalDataSource() = localDataSource

    /**
     * Method to backup patient datas
     * @param context: Context
     * @param list :List<Patient>? Retrieve the data from the Room table using a DAO method
     * @param gson: Gson
     * */
    suspend fun backUpDatas(
        context: Context,
        isExport: Boolean = false,
        list: List<Patient>?,
        gson: Gson = Gson()
    ) {
        logsource.addLog("backUpDatas 11 Thread : " + Thread.currentThread().name)
        progressMLF.postValue(Event(DIGITS.ONE))
        logsource.addLog("backUpDatas 22 Thread : " + Thread.currentThread().name)
        // Convert the retrieved data to a JSON string using Gson
        val jsonString = gson.toJson(list)
        // Write the JSON string to a file
        val file = File(context.filesDir, Common.BACKUP_FILE)
        try {
            delay(DELAY_2000)
            file.writeText(jsonString)
            progressMLF.postValue(Event(DIGITS.ZERO))
            /*  if(Random.nextBoolean())
                  throw Exception()*/
            if (!isExport)
                statusMessage.postValue(Event(Status(EStatus.SUCCESS, "Backup successfully ")))
            //statusMessage.postValue(Event("Backup Successfully "))
            else
                backupStatusMLF.postValue(Event(1))
        } catch (e: Exception) {
            progressMLF.postValue(Event(DIGITS.ZERO))
            if (!isExport)
                statusMessage.postValue(
                    Event(
                        Status(
                            EStatus.ERROR,
                            "Backup, file write failed: ${e.message}"
                        )
                    )
                )
            // statusMessage.postValue(Event("Backup ,File write failed: ${e.message}"))
            else
                backupStatusMLF.postValue(Event(0))
        }
    }
}