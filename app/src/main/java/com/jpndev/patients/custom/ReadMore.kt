package com.jpndev.patients.custom

import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.jpndev.patients.R
import com.jpndev.patients.utils.DIGITS

class ReadMore(
    var context: Context? = null,
    val textLength: Int = 0,
    val textLengthType: Int = 0,
    val moreLabel: String? = null,
    val lessLabel: String? = null,
) {

    private constructor(builder: Builder) : this(
        builder.context,
        builder.textLength,
        builder.textLengthType,
        builder.moreLabel,
        builder.lessLabel,
    )

    fun addReadMoreTo(textView: TextView, text: CharSequence) {
        if (textLengthType == ReadMore.TYPE_CHARACTER) {
            if (text.length <= textLength) {
                textView.text = text
                return
            }
        } else {
            // If TYPE_LINE
            textView.setLines(textLength)
            textView.text = text
        }
        textView.post(Runnable {
            var textLengthNew = textLength
            if (textLengthType == ReadMore.TYPE_LINE) {
                if (textView.layout.lineCount <= textLength) {
                    textView.text = text
                    return@Runnable
                }
                val lp = textView.layoutParams as MarginLayoutParams
                val subString = text.toString().substring(
                    textView.layout.getLineStart(0),
                    textView.layout.getLineEnd(textLength - 1)
                )
                val templength =subString.length - (moreLabel!!.length + 4 + lp.rightMargin / 6)
                textLengthNew = if(templength> DIGITS.ONE) templength else textLengthNew
                Log.d("jp","subString length"+ subString.length+" more length"+(moreLabel!!.length + 4 + lp.rightMargin / 6)+" textLengthNew: "+textLengthNew)
            }

            val spannableStringBuilder = SpannableStringBuilder(text.subSequence(0, textLengthNew))
                .append("...")
                .append(moreLabel)
            val ss = SpannableString.valueOf(spannableStringBuilder)
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(view: View) {
                    addReadLess(textView, text)
                }
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = Color.BLUE // Set text color to blue
                    ds.isUnderlineText = true // Add underline to text
                }
            }
            ss.setSpan(
                clickableSpan,
                ss.length - moreLabel!!.length,
                ss.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            val layoutTransition = LayoutTransition()
            layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            (textView.parent as ViewGroup).layoutTransition = layoutTransition
            textView.text = ss
            textView.movementMethod = LinkMovementMethod.getInstance()
            /*context?.apply {
                textView.setTextColor( ContextCompat.getColor(this, R.color.md_red_100))
            }*/
        })
    }

    private fun addReadLess(textView: TextView, text: CharSequence) {
        textView.maxLines = Int.MAX_VALUE
        val spannableStringBuilder = SpannableStringBuilder(text)
            .append(lessLabel)
        val ss = SpannableString.valueOf(spannableStringBuilder)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                Handler().post { addReadMoreTo(textView, text) }
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.RED // Set text color to blue
                ds.isUnderlineText = true // Add underline to text
            }
        }
        ss.setSpan(
            clickableSpan,
            ss.length - lessLabel!!.length,
            ss.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    class Builder(c: Context) {
        val context: Context = c

        var textLength = 100
            private set
        var textLengthType = ReadMore.TYPE_CHARACTER
            private set
        var moreLabel = "Read more"
            private set
        var lessLabel = "Read less"
            private set


        fun textLength(length: Int?, typeLine: Int) = apply { textLength = length ?: 100
            textLengthType = typeLine}
        fun textLengthType(type: Int) = apply { textLengthType = type }
        fun moreLabel(more: String) = apply { moreLabel = more }
        fun lessLabel(less: String) = apply { lessLabel = less }

        fun build() = ReadMore(this)
    }

    companion object {
        private val TAG = ReadMore::class.java.simpleName
        const val TYPE_LINE = 1
        const val TYPE_CHARACTER = 2
    }

}