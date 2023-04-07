package com.jpndev.patients.ui.splash

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.*
import com.jpndev.patients.MainActivity
import com.jpndev.patients.ui.patient.PatientRepository
import kotlinx.coroutines.launch

class SplashViewModel(
     val app: Application,
     val repository: PatientRepository

) : AndroidViewModel(app) {

    lateinit var activity: Activity
    fun addLog(log_text: String) =
        repository.logsource.addLog("" + log_text)

    fun addStudioLog(log_text: String) =
        repository.logsource.addStudioLog("" + log_text)

    fun setActiivty(temp: Activity) = viewModelScope.launch {
        activity = temp
    }

    fun showMainAcivity(temp: Activity? = activity) = viewModelScope.launch {
        val intent = Intent(temp, MainActivity::class.java)
        //    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
        activity.finish()
    }

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
/*
    val app_update_mld: MutableLiveData<Resource<MUpdateData>> = MutableLiveData()

    fun refreshAppUpdate() = viewModelScope.launch(Dispatchers.IO) {
        app_update_mld.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = usecase.executeAppUpdate()
                apiResult.data?.let {
                    usecase.saveAPPDatatoDb(it)
                    // checkUpdate(it)
                    app_update_mld.postValue(Resource.Success(it))
                }
            } else {
                app_update_mld.postValue(Resource.ServerError("No Internet Connection"))
            }
        } catch (e: Exception) {
            app_update_mld.postValue(Resource.ServerError("refreshAppUpdate " + e.message.toString()))
        }

    }

    public fun checkUpdate(obj: MUpdateData) {
        addLog("checkUpdate app version" + getCurrentVersionCode() + " latest version" + obj.version_code)
        if (getCurrentVersionCode() > 0) {
            if (obj.version_code > getCurrentVersionCode()) {
                showForceUpdateDialog(obj)
            } else {
                showMainAcivity()
            }
        } else {
            showMainAcivity()

        }
    }

    private fun getCurrentVersionCode(): Int {

        var currentVersion_code: Int = -1
        val packageManager = app.packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(app.packageName, 0)
            currentVersion_code = packageInfo!!.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return currentVersion_code
    }

    fun showForceUpdateDialog(obj: MUpdateData, temp: Activity? = activity) {
*//*        if (!isFinishing && !isDestroyed) {*//*
        temp?.let {
            val alertDialogBuilder = AlertDialog.Builder(temp)
            val title: String = obj.update_title ?: temp.getString(R.string.youAreNotUpdatedTitle)
            val message: String = obj.update_message
                ?: temp.getString(R.string.youAreNotUpdatedMessage) + " " + obj.version_name + temp.getString(
                    R.string.youAreNotUpdatedMessage1
                )
            //alertDialogBuilder.setTitle( Html.fromHtml(title))
            alertDialogBuilder.setTitle(
                HtmlCompat.fromHtml(
                    title,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            )
            // Linkify the message
            val s: SpannableString =
                SpannableString(message); // msg should have url to enable clicking
            Linkify.addLinks(s, Linkify.WEB_URLS);
            alertDialogBuilder.setMessage(s)

            val packagename = "com.jpndev.player"
            alertDialogBuilder.setCancelable(!obj.force_update)
            if (!obj.force_update)
                alertDialogBuilder.setNegativeButton("cancel") { dialog, id -> //getPackageName()
                    *//*       val intent = Intent(activity, HomeActivity::class.java)
                           startActivity(intent)*//*
                    dialog.cancel()
                    showMainAcivity()
                }
            alertDialogBuilder.setPositiveButton(
                R.string.update
            ) { dialog, id -> //getPackageName()
                temp.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$packagename")
                    )
                )
                dialog.cancel()
            }
            val alert = alertDialogBuilder.create()
            alert.show()
            // Make the textview clickable. Must be called after show()
            alert.findViewById<TextView>(android.R.id.message)
                ?.setMovementMethod(LinkMovementMethod.getInstance())
        }
    }*/
}