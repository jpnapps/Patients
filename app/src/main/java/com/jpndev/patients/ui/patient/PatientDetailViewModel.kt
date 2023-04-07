package  com.jpndev.patients.ui.patient

import android.app.Activity
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
import com.github.tntkhang.fullscreenimageview.library.FullScreenImageViewActivity


import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.util.Resource
import com.jpndev.patients.utils.*
import com.jpndev.patients.utils.extensions.conversionTime
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class PatientDetailViewModel(
    private val app: Application,
    val repository: PatientRepository
) : AndroidViewModel(app) {

    var action_menu_text: String = "Edit"
    var isUpdate: Boolean = false
    var patient_mld = MutableLiveData<Patient>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    fun copyTextFn(text: String) {
        var clipboardManager = app.getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager?
        val separator = "\n"
        var clipData = ClipData.newPlainText("  ", text)
        clipboardManager!!.setPrimaryClip(clipData)
        ToastHandler.newInstance(app).mustShowToast("Copied data and cleared")
    }

    fun conversionTime(time: Long) =time.conversionTime()

    /**
     * Method to Edit Activity
     * */
    fun showEditPatientActivity(activity: Activity) = viewModelScope.launch {
        val intent = Intent(activity, AddPatientActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //repository.getCurrentPatient()?.value=patient_mld.value
        repository.setCurrentPatient(patient_mld.value)
        //intent.putExtra(Common.SELECTED_ITEM, patient_mld.value)
        activity.startActivity(intent)
    }
    /**
     * Method to delete patient from db
     * */
    fun setInitialValues(intent: Intent) = viewModelScope.launch {
        val item = intent.getSerializableExtra(Common.SELECTED_ITEM)
        item?.let {
            it as Patient
            patient_mld.value = it
            it.imageList?.apply {
                repository.logsource.addLog("PatientDetailViewModel setInitialValues calling addImages: ")
                repository.addImages(this)
            }
        } ?: let {
            patient_mld.value = Patient()
            clearImageList()
        }
    }
    /**
     * Method to set patient initials
     * */
    fun setInitialValues(patient: Patient?) = viewModelScope.launch {
        patient?.let {
            //it as Patient
            patient_mld.value = it
            it.imageList?.apply {
                repository.logsource.addLog("PatientDetailViewModel setInitialValues calling addImages: "+this.size)
                repository.addImages(this)
            }
        } ?: let {
            patient_mld.value = Patient()
            clearImageList()
        }
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

    fun checkExcute() = viewModelScope.launch {
        if (isUpdate)
            updatePatient()
        else
            savePatient()

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
            val newRowId = repository.savePatient(it)
            if (newRowId > -1) {
                statusMessage.value = Event("Added Successfully $newRowId")
                repository.setCurrentPatient(it)
            } else {
                statusMessage.value = Event("Error Occurred")
            }
        }

    }

    fun updatePatient() = viewModelScope.launch {
        repository.logsource.addLog("up before =" + patient_mld.value?.notes)
        repository.logsource.addLog("up after= " + patient_mld.value?.notes)
        val newRowId = repository.updatePatient(patient_mld.value!!)
        if (newRowId > -1) {
            statusMessage.value = Event("Updated Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun getSavedPatients(): LiveData<List<Patient>> = liveData<List<Patient>> {
        mld_Progress.postValue(Resource.Loading())
        repository.getPatients().collect {
            emit(it)
        }
    }


    val mld_Progress: MutableLiveData<Resource<Patient>> = MutableLiveData()
    private fun isNetworkAvailable(context: Context?): Boolean {
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

    fun addImage(item: Uri) {
        repository.addImage(item)
    }

    fun clearImageList() {
        repository.clearImageList()
    }

    fun observerForImages() = repository.imageseLiveData

    fun fullScreenImage(activity: Activity, list: ArrayList<String>, pos: Int) {
        val fullImageIntent = Intent(
            activity,
            FullScreenImageViewActivity::class.java
        )
        // uriString is an ArrayList<String> of URI of all images
        fullImageIntent.putExtra(
            FullScreenImageViewActivity.URI_LIST_DATA,
            list
        )
        // pos is the position of image will be showned when open
        fullImageIntent.putExtra(
            FullScreenImageViewActivity.IMAGE_FULL_SCREEN_CURRENT_POS,
            pos
        )
        activity.startActivity(fullImageIntent)
    }

    fun getCurrentPatient() = repository.getCurrentPatient()
}


