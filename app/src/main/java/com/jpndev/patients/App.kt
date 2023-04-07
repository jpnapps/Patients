package com.jpndev.patients

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
/*import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration*/
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import javax.inject.Inject


@HiltAndroidApp
class App : Application() {
    //, Configuration.Provider {
    @Inject
    lateinit var logSource: LogSourceImpl

    //@Inject lateinit var workerFactory: HiltWorkerFactory

    /* override fun getWorkManagerConfiguration() =
         Configuration.Builder()
             .setWorkerFactory(workerFactory)
             .build()*/

    override fun onCreate() {
        super.onCreate()
       /* createChannel(
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )*/
    }

    override fun onTerminate() {
        super.onTerminate()
        logSource.addLog("app onTerminate   Thread= " + Thread.currentThread().name)
    }

    suspend open fun addLog(obj: Any?) {
        /*     coroutineScope {*/
        try {
            if (obj is String) {
                logSource.addLog("\n Text  = " + obj)
            }
        } catch (e: Exception) {
            // activity.hideProgress()
        }
    }


    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(true)
                }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            //    notificationChannel.description = getString(R.string.breakfast_notification_channel_description)
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }
}
