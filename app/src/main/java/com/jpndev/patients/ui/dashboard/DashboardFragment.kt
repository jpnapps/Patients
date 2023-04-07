package com.jpndev.patients.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.newsapiclient.presentation.PItemAdapter
import com.jpndev.patients.MainActivity
import com.jpndev.patients.R
import com.jpndev.patients.custom.SwipeCallback
import com.jpndev.patients.databinding.FragmentDashboardBinding
import com.jpndev.patients.presentation.ui.MainViewModel
import com.jpndev.patients.ui.alert.MBaseDialog
import com.jpndev.patients.ui.alert.OkDialog
import com.jpndev.patients.ui.alert.TwoButtonsDialog
import com.jpndev.patients.ui.interfaces.OnTwoButtonDialogListner
import com.jpndev.patients.ui.patient.AddPatientActivity
import com.jpndev.patients.ui.patient.PatientDetailActivity
import com.jpndev.patients.utils.Common
import com.jpndev.patients.utils.DIGITS
import com.jpndev.patients.utils.extensions.setAllOnClickListener
import javax.inject.Inject

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    lateinit var viewMainModel: MainViewModel
    lateinit var pitemadapter: PItemAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        viewMainModel = (activity as MainActivity).viewMainModel
        pitemadapter = (activity as MainActivity).pitemadapter
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addPatientGroup.visibility = View.GONE
        setClickListner()
        initRcv()
        patientListObserver()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClickListner() {
        binding.addDimv.setOnClickListener {
            viewMainModel.repository.setCurrentPatient(null)
            viewMainModel.showAddPItemActivity(activity as MainActivity)
        }
        binding.addPatientGroup.setAllOnClickListener {
            viewMainModel.repository.setCurrentPatient(null)
            viewMainModel.showAddPItemActivity(activity as MainActivity)
        }
        binding.seachDimv.setOnClickListener {
            viewMainModel.repository.setCurrentPatient(null)
            viewMainModel.showSearchActivity(activity as MainActivity)
        }
        binding.addPatientGroup.setOnClickListener {
            viewMainModel.repository.setCurrentPatient(null)
            viewMainModel.showAddPItemActivity(activity as MainActivity)
        }
        pitemadapter.setOnItemClickListner {
            val intent = Intent(activity, PatientDetailActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Common.SELECTED_ITEM, it)
            viewMainModel.repository.setCurrentPatient(it)
            viewMainModel.repository.temp = 10
            viewMainModel.repository.logsource.addLog("DashboardFragment temp: " + viewMainModel.repository.temp)
            viewMainModel.repository.logsource.addLog("DashboardFragment name: " + viewMainModel.repository.getCurrentPatient()?.value?.name)
            startActivity(intent)
            /*     viewMainModel.setBioAuth(activity as MainActivity) {
                     val intent = Intent(activity, AddPatientActivity::class.java)
                     intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                     intent.putExtra(Common.SELECTED_ITEM, it)
                     startActivity(intent)
                 }*/
        }
    }

    private fun initRcv() {
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = pitemadapter
            //addOnScrollListener(activity.onScrollListner)
        }
    }

    private fun patientListObserver() {
        viewMainModel.getSavedPItems().observe(viewLifecycleOwner) {
            it.takeIf { it.isNotEmpty() }?.apply {
                binding.addPatientGroup.visibility = View.GONE
                binding.searchGroup.visibility = View.VISIBLE
                binding.rcv.visibility = View.VISIBLE
            } ?: apply {
                binding.addPatientGroup.visibility = View.VISIBLE
                binding.searchGroup.visibility = View.GONE
                binding.rcv.visibility = View.GONE
            }
            pitemadapter.differ.submitList(it)
        }
        //swipe delete callback
        val swipeCallback =object : SwipeCallback(activity as MainActivity ){
            override fun onSwipe(position: Int, direction: Int) {
                TwoButtonsDialog.showDialog(
                    activity as MainActivity,
                    MBaseDialog(
                        title = activity?.getString(R.string.delete_dialog_title),
                        desc = activity?.getString(R.string.delete_dialog_message),
                        close_icon = false
                    ),
                    object : OnTwoButtonDialogListner {
                        override fun onRightButtonClick(obj: Any?) {
                            val position = position
                            val article = pitemadapter.differ.currentList[position]
                            viewMainModel.deletePItem(article)
                        }

                        override fun onLeftButtonClick(obj: Any?) {
                            pitemadapter.notifyItemChanged(position)
                        }

                        override fun onDismiss(obj: Any?) {
                        }

                    }
                )
            }
        }
        ItemTouchHelper(swipeCallback).apply { attachToRecyclerView(binding.rcv) }
    }
}