package com.jpndev.patients.presentation.ui

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.util.Resource
import com.jpndev.patients.domain.usecase.UseCase
import com.jpndev.patients.ui.dashboard.SearchPatientActivity
import com.jpndev.patients.ui.patient.AddPatientActivity
import com.jpndev.patients.ui.patient.PatientRepository
import com.jpndev.patients.utils.LogUtils
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainViewModel(
    private val app: Application,
    public val usecase: UseCase,
    public val repository: PatientRepository
) : AndroidViewModel(app) {
    /*   @Inject
       lateinit var user: User
*/

    public fun getUseCase() = usecase
    var text = MutableLiveData<String>()

    init {
        LogUtils.LOGD(
            "pref_lc",
            "\n MainViewModel = " + usecase.prefUtils.getString("lifecycle", "Nothing found")
        )
        text.value = usecase.prefUtils.getString("lifecycle", "Nothing found")
    }

/*

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    public fun setBioPrompt(activity: FragmentActivity, lmbd: () -> Unit) {
        executor = ContextCompat.getMainExecutor(activity)
        biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        app,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        app,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                    lmbd()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        app, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    // finish()
                }
            })
    }

    public fun setBioAuth() {
        // Allows user to authenticate using either a Class 3 biometric or
// their lock screen credential (PIN, pattern, or password).
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()
        // Can't call setNegativeButtonText() and
        // setAllowedAuthenticators(... or DEVICE_CREDENTIAL) at the same time.
        // .setNegativeButtonText("Use account password")

        biometricPrompt.authenticate(promptInfo)
    }
*/


    fun deletePItem(article: Patient) = viewModelScope.launch {
        usecase.executeDeletePItrm(article)
    }


    fun savePItem(article: Patient) = viewModelScope.launch {
        usecase.executeSavePItem(article)
    }

    fun getSavedPItems(): LiveData<List<Patient>> = liveData<List<Patient>> {
        mld_Progress.postValue(Resource.Loading())
        usecase.executeGetPList().collect {
            emit(it)
        }

    }

    fun showAddPItemActivity(activity: Activity) = viewModelScope.launch {
        val intent = Intent(activity, AddPatientActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    fun showSearchActivity(activity: Activity) = viewModelScope.launch {
        val intent = Intent(activity, SearchPatientActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
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

   /* public fun setBioAuth(activity: FragmentActivity, lmbd: () -> Unit) {
        // Allows user to authenticate using either a Class 3 biometric or
// their lock screen credential (PIN, pattern, or password).
        setBioPrompt(activity, lmbd)
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            // .setNegativeButtonText("Use account password")
            .setDeviceCredentialAllowed(true)
            .build()
        // Can't call setNegativeButtonText() and
        // setAllowedAuthenticators(... or DEVICE_CREDENTIAL) at the same time.
        // .setNegativeButtonText("Use account password")

        biometricPrompt.authenticate(promptInfo)
    }*/
}


