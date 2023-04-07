package com.jpndev.patients.ui.alert

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.app.BundleCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.jpndev.patients.R
import com.jpndev.patients.databinding.AlertOkBinding
import com.jpndev.patients.databinding.DialogTwoButtonsBinding
import com.jpndev.patients.databinding.FragmentMoreBinding
import com.jpndev.patients.ui.interfaces.OnDismissListner
import com.jpndev.patients.ui.interfaces.OnTwoButtonDialogListner

class TwoButtonsDialog : DialogFragment() {


    var mBaseDialog: MBaseDialog? = null
    var from: String? = "twobuttonsdialog"
    lateinit var callback: OnTwoButtonDialogListner
    companion object {
        var closed = true
        fun newInstance(content: String): TwoButtonsDialog {
            val f = TwoButtonsDialog()
            val args = Bundle()
            f.arguments = args
            return f
        }

        fun newInstance(): TwoButtonsDialog {
            val f = TwoButtonsDialog()
            val args = Bundle()
            f.arguments = args
            return f
        }

        fun newInstance(mBaseDialog: MBaseDialog,callback: OnTwoButtonDialogListner): TwoButtonsDialog {
            val f = TwoButtonsDialog()
            val args = Bundle()
            args.putParcelable("ok_dialog_bundle", mBaseDialog)
            f.callback=callback
            f.arguments = args

            return f
        }

        fun newInstance(mBaseDialog: MBaseDialog, from: String,callback: OnTwoButtonDialogListner): TwoButtonsDialog {
            val f = TwoButtonsDialog()

            val args = Bundle()
            args.putParcelable("ok_dialog_bundle", mBaseDialog)
            args.putString("from", from)
            f.callback=callback
            f.arguments = args

            return f
        }

        fun showDialog(context: Activity): TwoButtonsDialog {

            val newFragment = TwoButtonsDialog.newInstance()
            try {
                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager

                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()
                }
            } catch (e: Exception) {
                e.message
            }
            return newFragment
        }

        fun showDialog(context: Activity, mBaseDialog: MBaseDialog,callback: OnTwoButtonDialogListner): TwoButtonsDialog {
            val newFragment = TwoButtonsDialog.newInstance(mBaseDialog,callback)
            try {

                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager
                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()


                }
            } catch (e: Exception) {
                e.message
            }

            return newFragment

        }

        fun showDialog(
            context: Activity,
            mBaseDialog: MBaseDialog,
            from: String,
            callback: OnTwoButtonDialogListner
        ): TwoButtonsDialog {
            val newFragment = TwoButtonsDialog.newInstance(mBaseDialog, from,callback)

            try {
                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager

                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()
                }
            } catch (e: Exception) {
                e.message
            }
            return newFragment
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseDialog = getArguments()?.getParcelable("ok_dialog_bundle")
        from = mBaseDialog?.from

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private var _binding: DialogTwoButtonsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DialogTwoButtonsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closed = false
        binding.text2Txv.visibility = View.GONE

        mBaseDialog?.let {
            it.desc?.let {
                binding.text2Txv.setText(it)
                if (it.isNotEmpty())
                    binding.text2Txv.visibility = View.VISIBLE
            }
            if (it.close_icon)
                binding.closeRlay.visibility = View.VISIBLE
            else
                binding.closeRlay.visibility = View.GONE
            it.title?.let {
                binding.text1Txv.setText(it)
                if (it.isNotEmpty())
                    binding.text1Txv.visibility = View.VISIBLE
            }
        }
        setErrorText("")
       // val listener = activity as OnTwoButtonDialogListner?
        binding.topRlay.setOnClickListener { }
        binding.topRlay.setOnTouchListener { v, event -> false }
        binding.closeRlay.setOnClickListener {
            callback?.onDismiss(false)
            onBackFinish()
        }
        binding.rightCard.setOnClickListener {
            callback?.onRightButtonClick()
            onBackFinish()
        }
        binding.leftCard.setOnClickListener {
            callback?.onLeftButtonClick()
            onBackFinish()
        }
        //   showKeyboard(otp_pinv)
    }

    fun setOTPText(text: String) {
    }

    fun setErrorText(text: String) {
        Log.d("_sms", "setErrorText text:" + text)
        //  Log.d("_sms", "setErrorText error_txv :" + error_txv?.text)
        //  error_txv?.setText(text)
    }

    fun popupDismiss() {
        val listener = activity as OnDismissListner?
        listener?.onDismiss(false)
        onBackFinish()
    }

    fun redirectLogin(activity: Activity?) {
        activity?.let {
            /*   ToastHandler.newInstance(activity).mustShowToastSessionExpired()
               HomeSignTabActivity.tab_position = 4
               CCRBXActivity.class_name = activity?.javaClass
               val intent = Intent(activity, SignktActivity::class.java)
               startActivity(intent)*/
        }
    }


    fun onBackFinish() {
        closed = true
        dismiss()
    }


}
