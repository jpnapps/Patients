package com.jpndev.patients.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.jpndev.patients.R
import com.jpndev.patients.databinding.ImagePickViewBinding
import com.jpndev.patients.utils.DIGITS

/**
 * Custom class for cardiogram values View to set the text color and background color
 * Using in fragment_cardiogram and CardiogramFragment used to show the proper formatting in value and unit
 * Also will be used for hrv, respiratory, heart_rate
 *
 * @param context of calling activity or fragment
 * @param attrs attribute value like color,text,background for cardiogram values
 * @author Sonam Gupta
 */
class ImagePickView(context: Context, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {
    interface ImagePickViewClickListener {
        fun onCloseClicked()
    }

    var binding: ImagePickViewBinding

    private var listener: ImagePickViewClickListener? = null

    /**
     * init view to initialize attributes
     */
    init {
        // View.inflate(context, R.layout.image_pick_view, this)
        binding = ImagePickViewBinding.inflate(LayoutInflater.from(context), this, true)
        val typedArray =
            context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ImagePickView,
                DIGITS.ZERO,
                DIGITS.ZERO
            )

        try {
            val show_close_icon =
                typedArray.getBoolean(R.styleable.ImagePickView_show_close_icon, false)
            val add_image_mode =
                typedArray.getBoolean(R.styleable.ImagePickView_add_image_mode, false)
            setCloseIcon(show_close_icon)
            setAddMode(add_image_mode)
            binding.imageCloseRlay.setOnClickListener {
                listener?.onCloseClicked()
            }
        } finally {
            typedArray.recycle()
        }
    }

    /**
     * Used to update the hrv respiratory and heart_rate value from server
     *
     * @param value for pulse, cardiogram data
     */
    fun setCloseIcon(show_close_icon: Boolean) {
        if (show_close_icon)
            binding.imageCloseRlay.visibility = View.VISIBLE
        else
            binding.imageCloseRlay.visibility = View.INVISIBLE
    }

    /**
     * Used to update the hrv respiratory and heart_rate value from server
     *
     * @param value for pulse, cardiogram data
     */
    fun setAddMode(add_image_mode: Boolean) {
        Log.d("jp", "ImagePickview setAddMode " + add_image_mode)
        if (add_image_mode) {
            binding.addImageDimv.visibility = View.VISIBLE
            binding.imageDimv.visibility = View.GONE
        } else {
            binding.addImageDimv.visibility = View.GONE
            binding.imageDimv.visibility = View.VISIBLE
        }
    }

    /**
     * Used to update the hrv respiratory and heart_rate value from server
     *
     * @param value for pulse, cardiogram data
     */
    fun setListener(listener: ImagePickViewClickListener) {
        this.listener = listener
    }
}
