package com.jpndev.patients.ui.patient

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.tntkhang.fullscreenimageview.library.FullScreenImageViewActivity
import com.jpndev.patients.custom.ReadMore
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.patients.databinding.ActivityPatientDetailBinding
import com.jpndev.patients.ui.alert.MBaseDialog
import com.jpndev.patients.ui.alert.OkDialog
import com.jpndev.patients.utils.DIGITS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PatientDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: PatientDetailViewModelFactory
    lateinit var viewModel: PatientDetailViewModel
    private lateinit var binding: ActivityPatientDetailBinding

    @Inject
    lateinit var logSourceImpl: LogSourceImpl

    val options: RequestOptions =
        RequestOptions().override(350).transform(CenterCrop(), RoundedCorners(40))

    val readMore: ReadMore by lazy {
        ReadMore.Builder(this)
            .textLength(4, ReadMore.TYPE_LINE)
            .moreLabel("Read more")
            .lessLabel("Read Less")
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(PatientDetailViewModel::class.java)
        logSourceImpl.addLog("PatientDetailActivity onCreate temp: " + viewModel.repository.temp)
        logSourceImpl.addLog("PatientDetailActivity name: " + viewModel.repository.getCurrentPatient()?.value?.name)
        binding.viewmodel = viewModel
        //viewModel.setInitialValues(intent)
        observerImages()
        observerPatient()
        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                OkDialog.showDialog(
                    this@PatientDetailActivity,
                    MBaseDialog(title = it, close_icon = false)
                ).showsDialog
                //    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        binding.closeDimv.setOnClickListener {
            onBackPressed()
        }

        binding.editDimv.setOnClickListener {
            viewModel.showEditPatientActivity(this)
        }
        // binding.notesDetailSMTxv.setShowingLine(DIGITS.ONE)
        viewModel.patient_mld.value?.notes?.takeIf { it.isNotEmpty() }?.apply {
            readMore.addReadMoreTo(binding.notesDetailSMTxv, this)
            binding.notesCardV.visibility=View.VISIBLE
        }?:apply {
            binding.notesCardV.visibility=View.GONE
        }

        /*   binding.gradientChart.chartValues= arrayOf(
               30f, 36f, 40f, 36f, 28f, 24f, 12f, 11f, 10f, 11f, 12f, 27f, 31f
           )*/
       /* binding.gradientChart.chartValues = arrayOf(
            30f, 40f, 30f, 11f, 12f, 14f, 18f
        )*/
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }

    fun onDismiss(obj: Any?) {
        onBackPressed()
    }

    private fun observerPatient() {
        logSourceImpl.addLog("PatientDetailActivity observerPatient :" + viewModel.getCurrentPatient()?.value?.name)
        viewModel.getCurrentPatient().let { viewModel.setInitialValues(it.value) }
        viewModel.getCurrentPatient().observe(this) { patient ->
            logSourceImpl.addLog("PatientDetailActivity observerPatient observed name: " + patient?.name)
            viewModel.setInitialValues(patient)
            viewModel.patient_mld.value?.notes?.takeIf { it.isNotEmpty() }?.apply {
                readMore.addReadMoreTo(binding.notesDetailSMTxv, this)
                binding.notesCardV.visibility=View.VISIBLE
            }?:apply {
                binding.notesCardV.visibility=View.GONE
            }
        } ?: {
            viewModel.setInitialValues(null)
        }
    }

    private fun observerImages() {
        viewModel.observerForImages().observe(this) { list ->
            logSourceImpl.addLog("PatientDetailActivity observerImages  observed : " + list.size)
            when {
                list.size > DIGITS.ONE -> {
                    binding.image1Dimv.visibility = View.VISIBLE
                    binding.image2Dimv.visibility = View.VISIBLE
                    Glide.with(binding.image1Dimv.context).asBitmap()
                        .load(list.get(DIGITS.ZERO))
                        .apply(options)
                        .into(binding.image1Dimv)
                    Glide.with(binding.image2Dimv.context).asBitmap()
                        .load(list.get(DIGITS.ONE))
                        .apply(options)
                        .into(binding.image2Dimv)
                    binding.image1Dimv.setOnClickListener {
                        viewModel.fullScreenImage(this@PatientDetailActivity, list, DIGITS.ZERO)
                    }
                    binding.image2Dimv.setOnClickListener {
                        viewModel.fullScreenImage(this@PatientDetailActivity, list, DIGITS.ONE)
                    }
                }
                list.size == DIGITS.ONE -> {
                    binding.image1Dimv.visibility = View.VISIBLE
                    binding.image2Dimv.visibility = View.GONE
                    Glide.with(binding.image1Dimv.context).asBitmap()
                        .load(list.get(DIGITS.ZERO))
                        .apply(options)
                        .into(binding.image1Dimv)
                    binding.image1Dimv.setOnClickListener {
                        viewModel.fullScreenImage(this@PatientDetailActivity, list, DIGITS.ZERO)
                    }
                }
                list.size == DIGITS.ZERO -> {
                    binding.image1Dimv.visibility = View.GONE
                    binding.image2Dimv.visibility = View.GONE
                }
            }
        }
    }


}