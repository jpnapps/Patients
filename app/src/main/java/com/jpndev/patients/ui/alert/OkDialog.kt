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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.jpndev.patients.R
import com.jpndev.patients.databinding.AlertOkBinding
import com.jpndev.patients.databinding.FragmentMoreBinding
import com.jpndev.patients.ui.interfaces.OnDismissListner


class OkDialog : DialogFragment() {


    var mBaseDialog: MBaseDialog? = null

    var from: String? = "Coming Soon"

    companion object {

        var closed = true

        fun newInstance(content: String): OkDialog {
            val f = OkDialog()

            val args = Bundle()

            f.arguments = args

            return f
        }

        fun newInstance(): OkDialog {
            val f = OkDialog()
            val args = Bundle()
            f.arguments = args

            return f
        }

        fun newInstance(mBaseDialog: MBaseDialog): OkDialog {
            val f = OkDialog()

            val args = Bundle()
            args.putParcelable("ok_dialog_bundle", mBaseDialog)

            f.arguments = args

            return f
        }

        fun newInstance(mBaseDialog: MBaseDialog, from: String): OkDialog {
            val f = OkDialog()

            val args = Bundle()
            args.putParcelable("ok_dialog_bundle", mBaseDialog)
            args.putString("from", from)

            f.arguments = args

            return f
        }

        fun showDialog(context: Activity): OkDialog {

            val newFragment = OkDialog.newInstance()
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

        fun showDialog(context: Activity, mBaseDialog: MBaseDialog): OkDialog {
            val newFragment = OkDialog.newInstance(mBaseDialog)
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

        fun showDialog(context: Activity, mBaseDialog: MBaseDialog, from: String): OkDialog {
            val newFragment = OkDialog.newInstance(mBaseDialog, from)

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

    private var _binding: AlertOkBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = AlertOkBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closed = false
        binding.text2Txv.visibility = View.GONE
        binding.animationView.visibility=View.INVISIBLE
        mBaseDialog?.apply {
            desc?.apply {
                binding.text2Txv.setText(this)
                if (isNotEmpty())
                    binding.text2Txv.visibility = View.VISIBLE
            }
            if (close_icon)
                binding.closeRlay.visibility = View.VISIBLE
            else
                binding.closeRlay.visibility = View.GONE
            title?.apply {
                binding.text1Txv.setText(this)
                if (isNotEmpty())
                    binding.text1Txv.visibility = View.VISIBLE

            }
            title_color?.apply {
                binding.text1Txv.setTextColor(this)
                binding.buttonCard.setCardBackgroundColor(this)
            }
            lottieResId?.apply {
                binding.animationView.visibility=View.VISIBLE
                binding.animationView.setAnimation(this)
            }
            //binding.animationView.setAnimation()
        }
        setErrorText("")
        val listener = activity as OnDismissListner?
        binding.topRlay.setOnClickListener { }
        binding.topRlay.setOnTouchListener { v, event -> false }
        binding.closeRlay.setOnClickListener {
            listener?.onDismiss(false)
            onBackFinish()
        }
        binding.buttonCard.setOnClickListener {
            listener?.onDismiss(true)
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

/*package com.jpndev.patients.ui.alert

import android.os.Parcel
import android.os.Parcelable

//import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

//@Parcelize
data class MBaseDialog(
    var title: String? = "", // 5.011000
    var desc: String? = "", // 2
    var ok_text: String? = "Ok", // 4556347952822508
    var cancel_text: String? = "Cancel", // 5.011000
    var extra_text: String? = "",
    var from: String? = "",
    var is_default_linked_wallet: Boolean = false,
    var html_text: Boolean = false,
    var close_icon: Boolean = true, // 2018-11-28 09:48:56
    var cancel_icon: Boolean = true,
    var icon_url: String? = null,
    var position: Int = -1,
    var upto_value: Double? = null,
    var title_color: Int?
) : Parcelable/*  var ok_click: View.OnClickListener,*/ {
    constructor(parcel: Parcel) : this(
        parcel.readString(), parcel.readString(),
        parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Boolean::class.java.classLoader) as Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as Boolean, parcel.readString(),
        parcel.readInt(), parcel.readValue(Double::class.java.classLoader) as? Double,
                parcel.readValue(Int::class.java.classLoader) as Int?
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(ok_text)
        parcel.writeString(cancel_text)
        parcel.writeString(extra_text)
        parcel.writeString(from)
        parcel.writeByte(if (is_default_linked_wallet) 1 else 0)
        parcel.writeValue(html_text)
        parcel.writeValue(close_icon)
        parcel.writeValue(cancel_icon)
        parcel.writeString(icon_url)
        parcel.writeInt(position)
        parcel.writeValue(upto_value)
        if (title_color != null) {
            parcel.writeInt(title_color!!)
        } else {
            parcel.writeValue(null)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MBaseDialog> {
        override fun createFromParcel(parcel: Parcel): MBaseDialog {
            return MBaseDialog(parcel)
        }

        override fun newArray(size: Int): Array<MBaseDialog?> {
            return arrayOfNulls(size)
        }
    }
}*/
