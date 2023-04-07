package  com.jpndev.patients.ui.patient

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.lifecycle.*
import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.model.EStatus
import com.jpndev.patients.data.model.Status
import com.jpndev.patients.data.util.Resource
import com.jpndev.patients.utils.*
import com.jpndev.patients.utils.extensions.hideKeyboard
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import java.util.*

class AddPatientViewModel(
    private val app: Application,
    val repository: PatientRepository
) : AndroidViewModel(app) {

    var action_menu_text: String = "Save"
    var isUpdate: Boolean = false
    var patient_mld = MutableLiveData<Patient>()

    private val statusMessage = MutableLiveData<Event<Status>>()
    val message: LiveData<Event<Status>>
        get() = statusMessage

    val mld_Progress: MutableLiveData<Resource<Patient>> = MutableLiveData()

    val isDataValid = MutableLiveData<Boolean>().apply { value = false }

    /**
     * Method to set patient initials using intent
     * */
    fun setInitialValues(intent: Intent) = viewModelScope.launch {
        val item = intent.getSerializableExtra(Common.SELECTED_ITEM)
        item?.let {
            it as Patient
            setAsUpdateUI(it)
        } ?: let {
            setAsSaveUI()
        }
    }

    /**
     * Method to set patient initials by using patient object
     * @param patient: Patient , it may be null
     * */
    fun setInitialValues(patient: Patient?) = viewModelScope.launch {
        patient?.let {
            setAsUpdateUI(it)
        } ?: let {
            setAsSaveUI()
        }
    }

    /**
     * Method to set as update ui
     * @param patient: Patient
     * */
    private fun setAsUpdateUI(it: Patient) {
        patient_mld.value = it
        repository.logsource.addLog("APVM setAsUpdateUI notes: " + it.notes)
        it.imageList?.apply {
            repository.addImages(this)
        }
        action_menu_text = "Update"
        isUpdate = true
        validateData()
    }

    /**
     * Method to set as save ui
     * @param patient: Patient
     * */
    private fun setAsSaveUI() {
        patient_mld.value = Patient()
        action_menu_text = "Save"
        isUpdate = false
        clearImageList()
    }

    /**
     * Method to delete patient from db
     * */
    fun deletePatient(article: Patient) = viewModelScope.launch {
        repository.deletePatient(article)
        patient_mld.value?.let {

        } ?: let {

        }
    }

    /**
     * Method to check save or update patient
     * */
    fun checkExcute(view: View) = viewModelScope.launch {
        view.hideKeyboard()
        if (isUpdate)
            updatePatient()
        else
            savePatient()

    }

    /**
     * Method to validate data when user enters data
     * */
    fun validateData() = viewModelScope.launch {
        isDataValid.value = isPatientValid(patient_mld.value?.name, patient_mld.value?.age)
    }

    /**
     * Method to save patient to db
     * */
    fun savePatient() = viewModelScope.launch {
        repository.logsource.addLog("Add before =" + patient_mld.value?.notes)
        repository.logsource.addLog("Add after= " + patient_mld.value?.notes)
        var patient = patient_mld.value
        patient?.let {
            it.imageList = repository.image_list
            it.updatedAt = Date()
            it.createdAt = Date()
            val newRowId = repository.savePatient(it)
            /*   val tempPatient=it
               for (index in 1..100) {
                   tempPatient.name=generateRandomName()
                   tempPatient.age=generateRandomAge(20,40)
                   repository.savePatient(tempPatient)
               }*/
            if (newRowId > -1) {
                statusMessage.value = Event(Status(EStatus.SUCCESS, "Patient added successfully"))
                repository.setCurrentPatient(it)
                repository.logsource.addLog("AddPatientViewModel savePatient name: " + repository.getCurrentPatient().value?.name)
            } else {
                statusMessage.value =
                    Event(Status(EStatus.ERROR, "Something went wrong, Please try later."))
            }
        }

    }

    /**
     * Method to update patient to db
     * */
    fun updatePatient() = viewModelScope.launch {
        repository.logsource.addLog("APVM up before =" + patient_mld.value?.notes)
        repository.logsource.addLog("APVM up after= " + patient_mld.value?.notes)
        var patient = patient_mld.value
        patient?.let {
            it.updatedAt = Date()
            it.imageList = repository.image_list
            val newRowId = repository.updatePatient(it)
            if (newRowId > -1) {
                statusMessage.value =
                    Event(Status(EStatus.SUCCESS, "Patient details updated successfully "))
                repository.setCurrentPatient(it)
                repository.logsource.addLog("AddPatientViewModel updatePatient name: " + repository.getCurrentPatient().value?.name + " image_list: " + repository.image_list.size)
            } else {
                statusMessage.value =
                    Event(Status(EStatus.ERROR, "Something went wrong, Please try later."))
            }
        }
    }

    fun getSavedPatients(): LiveData<List<Patient>> = liveData<List<Patient>> {
        mld_Progress.postValue(Resource.Loading())
        repository.getPatients().collect {
            emit(it)
        }
    }


    /**
     * Method to add image in repository
     * */
    fun addImage(item: Uri) {
        repository.addImage(item)
    }

    /**
     * Method to clear images in repository
     * */
    fun clearImageList() {
        repository.clearImageList()
    }

    /**
     * Method to get imagelist mutuable live data
     * */
    fun observerForImages() = repository.imageseLiveData

    /**
     * Method to get current patient mutuable live data
     * */
    fun getCurrentPatient() = repository.getCurrentPatient()

    /**
     * Method to copy the text
     * */
    fun copyTextFn(text: String) {
        var clipboardManager = app.getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager?
        val separator = "\n"
        var clipData = ClipData.newPlainText("  ", text)
        clipboardManager!!.setPrimaryClip(clipData)
        ToastHandler.newInstance(app).mustShowToast("Copied data and cleared")
    }

    /**
     * Method to copy the text
     * */
    fun isPatientValid(name: String?, age: Int?): Boolean {
        // Perform validation logic here
        return !name.isNullOrEmpty() && age != null && age > 0
    }

    /**
     * Method to check Network available or not
     * */
    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}


