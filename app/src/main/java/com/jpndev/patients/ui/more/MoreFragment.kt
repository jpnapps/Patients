package com.jpndev.patients.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jpndev.patients.MainActivity
import com.jpndev.patients.databinding.FragmentMoreBinding
import com.jpndev.patients.presentation.ui.MainViewModel
import com.jpndev.patients.ui.about.VersionActivity
import com.jpndev.patients.ui.backup.BackupActivity
import com.jpndev.patients.ui.manage_log.ViewLogosActivity

class MoreFragment  : Fragment() {

    //private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentMoreBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var viewMainModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


      /*  homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)*/

        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewMainModel=(activity as MainActivity ).viewMainModel
        binding.viewLogos.setOnClickListener {
            val intent = Intent(activity, ViewLogosActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)

        }
        binding.dataCard.setOnClickListener {
            //  viewMainModel.setBioAuth()
            val intent = Intent(activity, BackupActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)
        }
        binding.aboutUsCard.setOnClickListener {
            val intent = Intent(activity,  VersionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity?.startActivity(intent)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}