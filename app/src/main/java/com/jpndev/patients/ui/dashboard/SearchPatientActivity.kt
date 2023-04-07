package com.jpndev.patients.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.newsapiclient.presentation.PatientSearchAdapter
import com.jpndev.patients.R
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.patients.databinding.ActivitySearchPatientBinding
import com.jpndev.patients.ui.alert.MBaseDialog
import com.jpndev.patients.ui.alert.OkDialog
import com.jpndev.patients.ui.interfaces.OnDismissListner
import com.jpndev.patients.ui.patient.PatientDetailActivity
import com.jpndev.patients.utils.Common
import com.paulrybitskyi.persistentsearchview.utils.VoiceRecognitionDelegate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchPatientActivity : AppCompatActivity() , OnDismissListner {

    @Inject
    lateinit var  factory: SearchPatientVMF
    lateinit var viewModel: SearchViewModel
    private lateinit var binding: ActivitySearchPatientBinding
    @Inject
    lateinit var patientSearchAdapter: PatientSearchAdapter
    @Inject
    lateinit var logSourceImpl: LogSourceImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        logSourceImpl.addLog("SearchPatientActivity onCreate temp: " + viewModel.repository.temp)
        binding.viewmodel = viewModel
        onObservers()
        initRcv()
        searchClickListner()
        patientListObserver()
    }

    private fun searchClickListner() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                logSourceImpl.addLog("onQueryTextSubmit query: " + query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                logSourceImpl.addLog("onQueryTextSubmit newText: " + newText)
                patientSearchAdapter.filter.filter(newText)
                return false
            }
        })
        /*with(binding.persistentSearchView) {
            setOnLeftBtnClickListener {
                // Handle the left button click
                patientSearchAdapter.filter.filter(Common.BLANK)
            }
            setOnClearInputBtnClickListener {
                // Handle the clear input button click
                patientSearchAdapter.filter.filter(Common.BLANK)
            }

            // Setting a delegate for the voice recognition input
            setVoiceRecognitionDelegate(VoiceRecognitionDelegate(this@SearchPatientActivity))

            setOnSearchConfirmedListener { searchView, query ->
                // Handle a search confirmation. This is the place where you'd
                // want to perform a search against your data provider.
                patientSearchAdapter.filter.filter(query)
            }

            // Disabling the suggestions since they are unused in
            // the simple implementation
            setSuggestionsDisabled(true)
        }*/
    }

    private fun onObservers() {
    }

    override fun onDismiss(obj: Any?) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Calling the voice recognition delegate to properly handle voice input results
        //VoiceRecognitionDelegate.handleResult(binding.persistentSearchView, requestCode, resultCode, data)
    }

    private fun initRcv() {
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(this@SearchPatientActivity)
            adapter = patientSearchAdapter
            //addOnScrollListener(activity.onScrollListner)
        }
    }
    private fun patientListObserver() {
        viewModel.getSavedPItems().observe(this@SearchPatientActivity) {
        /*    it.takeIf { it.isNotEmpty() }?.apply {
                binding.rcv.visibility= View.VISIBLE
            }?:apply {
                binding.rcv.visibility= View.GONE
            }*/
            patientSearchAdapter.setList(it.toMutableList())
            patientSearchAdapter.notifyDataSetChanged()
        }

        patientSearchAdapter.setOnItemClickListner {
            val intent = Intent(this@SearchPatientActivity, PatientDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Common.SELECTED_ITEM, it)
            viewModel.repository.setCurrentPatient(it)
            viewModel.repository.temp = 10
            viewModel.repository.logsource.addLog("DashboardFragment temp: " + viewModel.repository.temp)
            viewModel.repository.logsource.addLog("DashboardFragment name: " + viewModel.repository.getCurrentPatient()?.value?.name)
            startActivity(intent)
        }
    }
}