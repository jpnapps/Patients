package com.jpndev.patients.ui.about

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.jpndev.patients.BuildConfig
import com.jpndev.patients.R
import com.jpndev.patients.databinding.ActivityVersionBinding

class VersionActivity : AppCompatActivity() {


    private lateinit var binding: ActivityVersionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVersionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateStatusColor(R.color.white)
        val versionName = BuildConfig.VERSION_NAME
        binding.latestversionCtxv.text="Version "+versionName
    }

    override fun onBackPressed() {
        // super.onBackPressed()
       finish()
    }

    fun updateStatusColor(resourseColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(applicationContext, resourseColor)
            window.navigationBarColor =
                ContextCompat.getColor(applicationContext, resourseColor)
        }
    }
}