package com.jpndev.patients.ui.patient

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jpndev.patients.R
import com.jpndev.patients.custom.ImagePickView
import com.jpndev.patients.data.model.EStatus
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.patients.databinding.ActivityAddPatientBinding
import com.jpndev.patients.ui.alert.MBaseDialog
import com.jpndev.patients.ui.alert.OkDialog
import com.jpndev.patients.ui.interfaces.OnDismissListner
import com.jpndev.patients.utils.DIGITS
import com.jpndev.patients.utils.extensions.conversionTime
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import dagger.hilt.android.AndroidEntryPoint
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddPatientActivity : AppCompatActivity(), OnDismissListner {
    @Inject
    lateinit var factory: AddPatientViewModelFactory
    lateinit var viewModel: AddPatientViewModel
    private lateinit var binding: ActivityAddPatientBinding

    @Inject
    lateinit var logSourceImpl: LogSourceImpl
    val options: RequestOptions =
        RequestOptions().override(350).transform(CenterCrop(), RoundedCorners(40))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateStatusColor(R.color.white)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(AddPatientViewModel::class.java)
        logSourceImpl.addLog("AddPatientActivity onCreate temp: " + viewModel.repository.temp)
        binding.viewmodel = viewModel
        observerImages()
        observerPatient()
        //viewModel.setInitialValues(intent)
        viewModel.message.observe(this) {
            it.getContentIfNotHandled()?.let {

                OkDialog.showDialog(
                    this@AddPatientActivity,
                    MBaseDialog(desc = it.message, close_icon = false).apply {
                        if (it.id == EStatus.SUCCESS) {
                            title = getString(R.string.sucess_dialog_title)
                            title_color = ContextCompat.getColor(
                                this@AddPatientActivity,
                                R.color.md_green_new
                            )
                            lottieResId = R.raw.success_tick_lottie
                        } else if (it.id == EStatus.ERROR) {
                            title = getString(R.string.error_dialog_title)
                            title_color =
                                ContextCompat.getColor(this@AddPatientActivity, R.color.mintIcon)
                            lottieResId = R.raw.error_lottie
                        }
                    }
                )
            }
        }
        binding.closeDimv.setOnClickListener {
            onBackPressed()
        }
        binding.addimagePickView.setOnClickListener {
            showCameraFragment()
        }
        binding.dateLlay.setOnClickListener {
            showDatePicker()
        }
    }

    fun updateStatusColor(resourseColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor =
                ContextCompat.getColor(applicationContext, resourseColor)
            window.navigationBarColor =
                ContextCompat.getColor(applicationContext, resourseColor)
        }
    }

    private fun showDatePicker() {
        var dialog = CardDatePickerDialog.builder(this@AddPatientActivity)
            .setTitle("Date & Time")
            .setDisplayType(null)
            .setBackGroundModel(CardDatePickerDialog.CARD)
//                .setBackGroundModel(if(isDark) R.drawable.shape_bg_dialog_dark else R.drawable.shape_bg_dialog_light)
            .showBackNow(false)
            .setMaxTime(Date().time)
            .setPickerLayout(R.layout.layout_date_picker_segmentation)
            .setDefaultTime(Date().time)
            .setTouchHideable(true)
            .setChooseDateModel(DateTimeConfig.DATE_DEFAULT)
            .setWrapSelectorWheel(false)
            .setThemeColor(0)
//                .setAssistColor(Color.parseColor("#DDFFFFFF"))
//                .setDividerColor(Color.parseColor("#222222"))
            .showDateLabel(false)
            .showFocusDateInfo(true)
            .setOnChoose("OK") {
                viewModel.patient_mld.value?.lastConsulatedDate = Date(it)
                binding.lastConsulatedDateTxv.text = it.conversionTime()
                /*binding.lastConsulatedDateTxv.text = "${
                    it.conversionTime("yyyy-MM-dd HH:mm:ss")
                } ${it.getWeek()}"*/
            }
            .setOnCancel("Cancel") {
            }.build()
        dialog.show()
        (dialog as BottomSheetDialog).behavior.isHideable = false
    }

    private fun observerPatient() {
        viewModel.setInitialValues(viewModel.getCurrentPatient().value)
        //viewModel.getCurrentPatient()?.let {  viewModel.setInitialValues(it.value) }
        viewModel.getCurrentPatient().observe(this) { patient ->
            viewModel.setInitialValues(patient)
        }
        viewModel.patient_mld.observe(this) { patient ->
            binding.lastConsulatedDateTxv.text = patient.lastConsulatedDate.time.conversionTime()
        }
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }

    override fun onDismiss(obj: Any?) {
        onBackPressed()
    }

    private fun observerImages() {
        logSourceImpl.addLog("AddPatientActivity observerImages : ")
        viewModel.observerForImages().observe(this) { list ->
            logSourceImpl.addLog("AddPatientActivity observerImages  observed : " + list.size)
            when {
                list.size > 1 -> {
                    logSourceImpl.addLog("AddPatientActivity list.size >1 : " + list.size)
                    binding.image1PickView.visibility = View.VISIBLE
                    binding.image2PickView.visibility = View.VISIBLE
                    binding.addimagePickView.visibility = View.GONE
                    Glide.with(binding.image1PickView.context).asBitmap()
                        .load(list.get(0))
                        .apply(options)
                        .into(binding.image1PickView.binding.imageDimv)
                    Glide.with(binding.image2PickView.context).asBitmap()
                        .load(list.get(1))
                        .apply(options)
                        .into(binding.image2PickView.binding.imageDimv)
                    /* binding.image1Rlay.visibility = View.VISIBLE
                     binding.image2Rlay.visibility = View.VISIBLE
                     Glide.with(binding.image1Dimv.context).asBitmap()
                         .load(list.get(0))
                         .apply(options)
                         .into(binding.image1Dimv)
                     Glide.with(binding.image2Dimv.context).asBitmap()
                         .load(list.get(1))
                         .apply(options)
                         .into(binding.image2Dimv)
                     binding.addImageRlay.visibility=View.GONE*/
                }
                list.size == 1 -> {
                    logSourceImpl.addLog("AddPatientActivity list.size == 1")
                    binding.image1PickView.visibility = View.VISIBLE
                    binding.image2PickView.visibility = View.GONE
                    binding.addimagePickView.visibility = View.VISIBLE
                    Glide.with(binding.image1PickView.context).asBitmap()
                        .load(list.get(0))
                        .apply(options)
                        .into(binding.image1PickView.binding.imageDimv)

                }
                list.size == 0 -> {
                    logSourceImpl.addLog("AddPatientActivity list.size 0 : " + list.size)
                    binding.image1PickView.visibility = View.GONE
                    binding.image2PickView.visibility = View.GONE
                    binding.addimagePickView.visibility = View.VISIBLE
                }
            }
        }

        binding.image1PickView.setListener(object : ImagePickView.ImagePickViewClickListener{
            override fun onCloseClicked() {
                viewModel.repository.image_list.takeIf { it.isNotEmpty() }?.apply {
                    viewModel.repository.image_list.removeAt(DIGITS.ZERO)
                    viewModel.repository.notifyImageList()
                }
            }
        })
        binding.image2PickView.setListener(object : ImagePickView.ImagePickViewClickListener{
            override fun onCloseClicked() {
                viewModel.repository.image_list.takeIf { it.size>DIGITS.ONE }?.apply {
                    viewModel.repository.image_list.removeAt(DIGITS.ONE)
                    viewModel.repository.notifyImageList()
                }
            }
        })

    }

    private fun showCameraFragment() {
        binding.imageClay.visibility = View.VISIBLE
        var options = Options()
        options.count = 1
        options.mode = Mode.Picture
        addPixToActivity(R.id.imageClay, options) {
            binding.imageClay.visibility = View.GONE
            when (it.status) {
                PixEventCallback.Status.SUCCESS -> {
                    it.data.forEach {
                        logSourceImpl.addLog("showCameraFragment: ${it.path}")
                        viewModel.addImage(it)
                    }
                }
                PixEventCallback.Status.BACK_PRESSED -> {
                    supportFragmentManager.popBackStack()
                }
            }

        }
    }
}