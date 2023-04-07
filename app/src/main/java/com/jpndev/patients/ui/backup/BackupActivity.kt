package com.jpndev.patients.ui.backup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.jpndev.patients.R
import com.jpndev.patients.custom.LoadingIndicator
import com.jpndev.patients.data.model.EStatus
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.patients.databinding.ActivityAddPatientBinding
import com.jpndev.patients.databinding.ActivityBackupBinding
import com.jpndev.patients.ui.alert.MBaseDialog
import com.jpndev.patients.ui.alert.OkDialog
import com.jpndev.patients.ui.interfaces.OnDismissListner
import com.jpndev.patients.ui.patient.AddPatientViewModel
import com.jpndev.patients.ui.patient.AddPatientViewModelFactory
import com.jpndev.patients.utils.Common
import com.jpndev.patients.utils.Common.INTENT_MAIL_TYPE
import com.jpndev.patients.utils.DIGITS
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class BackupActivity : AppCompatActivity(), OnDismissListner {
    @Inject
    lateinit var factory: BackupVMF
    lateinit var viewModel: BackupViewModel
    private lateinit var binding: ActivityBackupBinding

    @Inject
    lateinit var logSourceImpl: LogSourceImpl

    @Inject
    lateinit var loadingIndicator: LoadingIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBackupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this, factory).get(BackupViewModel::class.java)
        logSourceImpl.addLog("BackupActivity onCreate temp: " + viewModel.repository.temp)
        binding.viewmodel = viewModel
        onObservers()
        onClickListners()
    }

    private fun onClickListners() {
        binding.backupCard.setOnClickListener {
            viewModel.backUpDatas(this@BackupActivity)
        }

        binding.exportCard.setOnClickListener {
            viewModel.backUpDatas(this@BackupActivity, true)
        }
        binding.closeDimv.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }

    private fun onObservers() {
        viewModel.repository.message.observe(this) {
            it.getContentIfNotHandled()?.let {
                OkDialog.showDialog(
                    this@BackupActivity,
                    MBaseDialog(desc = it.message, close_icon = false).apply {
                        if (it.id == EStatus.SUCCESS) {
                            title = getString(R.string.sucess_dialog_title)
                            title_color = ContextCompat.getColor(
                                this@BackupActivity,
                                R.color.md_green_new
                            )
                            lottieResId = R.raw.success_tick_lottie
                        } else if (it.id == EStatus.ERROR) {
                            title = getString(R.string.error_dialog_title)
                            title_color =
                                ContextCompat.getColor(this@BackupActivity, R.color.mintIcon)
                            lottieResId = R.raw.error_lottie
                        }
                    })
                /* OkDialog.showDialog(
                     this@BackupActivity,
                     MBaseDialog(title = it, close_icon = false)
                 )*/
            }
        }
        viewModel.repository.progressMLF.observe(this) {
            it.getContentIfNotHandled()?.let { status ->
                if (status == DIGITS.ZERO) {
                    loadingIndicator.hide()
                } else if (status == DIGITS.ONE) {
                    loadingIndicator.show()
                }
            }
        }
        viewModel.repository.backupStatusMLF.observe(this) {
            it.getContentIfNotHandled()?.let { status ->
                if (status == 0) {
                    OkDialog.showDialog(
                        this@BackupActivity,
                        MBaseDialog(
                            desc = "Export Failed, Data corrupted, Please contact admin",
                            close_icon = false
                        ).apply {
                            title = getString(R.string.error_dialog_title)
                            title_color =
                                ContextCompat.getColor(this@BackupActivity, R.color.mintIcon)
                            lottieResId = R.raw.error_lottie
                        }
                        /* MBaseDialog(
                             title = "Export Failed, Data corrupted, Please contact admin",
                             close_icon = false
                         )*/
                    )
                } else if (status == 1) {
                    val file = File(this@BackupActivity.filesDir, Common.BACKUP_FILE)
                    val uri = FileProvider.getUriForFile(
                        this@BackupActivity,
                        "${this@BackupActivity.applicationContext.packageName}.fileprovider",
                        file
                    )
                    /*               val intent = Intent(Intent.ACTION_SEND)
                                   intent.type = Common.INTENT_TYPE
                                   intent.putExtra(Intent.EXTRA_STREAM, uri)
                                   intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                   startActivity(Intent.createChooser(intent, "Share File"))*/

                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = INTENT_MAIL_TYPE // Use message/rfc822 to open email apps only
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Patients app datas")
                    intent.putExtra(Intent.EXTRA_TEXT, "Backup Datas attached in patients.json")
                    intent.putExtra(Intent.EXTRA_STREAM, uri)
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    startActivity(Intent.createChooser(intent, "Share with"))

                }
            }
        }
    }

    override fun onDismiss(obj: Any?) {

    }
}