package  com.jpndev.patients.ui.backup

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build

import androidx.lifecycle.*


import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.util.Resource
import com.jpndev.patients.ui.patient.PatientRepository
import com.jpndev.patients.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class BackupViewModel(
    private val app: Application,
    val repository: PatientRepository
) : AndroidViewModel(app) {

    var action_menu_text: String = "Save"
    var isUpdate: Boolean = false
    var patient_mld = MutableLiveData<Patient>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    val mld_Progress: MutableLiveData<Resource<Patient>> = MutableLiveData()

    /**
     * Method to backup patient datas
     * @param context: Context
     * @param list :List<Patient>? Retrieve the data from the Room table using a DAO method
     * @param gson: Gson
     * */
    fun backUpDatas(context: Context, export: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.logsource.addLog("backUpDatas 00 Thread : "+Thread.currentThread().name)
            val list=repository.getLcalDataSource().getPatientListFromDB()
            repository.backUpDatas(context, export,list)
        }
    }

}


