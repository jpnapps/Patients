package com.jpndev.patients.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jpndev.patients.databinding.ActivitySplashBinding
import com.jpndev.patients.utils.Common.DELAY_2000
import com.jpndev.patients.utils.Common.DELAY_500
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var  factory: SplashVMFactory
    lateinit var viewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this,factory).get(SplashViewModel::class.java)
        viewModel.activity=this@SplashActivity
        lifecycleScope.launch {
            delay(DELAY_2000)
            viewModel.showMainAcivity(this@SplashActivity)
        }
    }
 /*   private fun observeDatas() {
        viewModel.app_update_mld.observe(this,{response->
            when(response)
            {
                is Resource.Loading->{
                    showProgressBar()
                }
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let { viewModel.checkUpdate(it) }
                }
                is Resource.ServerError->{
                    hideProgressBar()
                    viewModel.showMainAcivity(this@SplashActivity)
                  //  response.data?.let { viewModel.checkUpdate(it) }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let{
                        Toast.makeText(this,"Error : "+it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }*/

    private fun hideProgressBar() {
        binding.progressBar.visibility= View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility= View.VISIBLE
    }
}