package com.jpndev.newsapiclient.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jpndev.patients.R
import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.databinding.RcvItemPitemBinding
import com.jpndev.patients.presentation.ui.MainViewModel
import com.jpndev.patients.utils.Common.SEMICOLON_SPACE
import com.jpndev.patients.utils.extensions.conversionTime


class PItemAdapter( val context: Context) : RecyclerView.Adapter<PItemAdapter.MyViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RcvItemPitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun getItems(): MutableList<Patient> {
        return differ.currentList
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = differ.currentList.get(position)
        holder.bind(item)
    }
    private var viewModel: MainViewModel? = null

    fun setViewModel(temp: MainViewModel) {
        viewModel = temp
    }
    val options: RequestOptions =
        RequestOptions().override(350).transform(CenterCrop(), RoundedCorners(40))

    inner class MyViewHolder(val binding: RcvItemPitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Patient) {
            binding.nameTxv.text = item.name
            binding.phoneTxv.text = item.phone
            binding.ageTxv.text = context.getString(R.string.age)+SEMICOLON_SPACE+item.age.toString()
            binding.lastVisitedDateTxv.text= item.lastConsulatedDate.time.conversionTime()
            item.imageList?.takeIf { it.isNotEmpty() }?.apply {
                binding.imgRlay.visibility = View.VISIBLE
                Glide.with(binding.imgDimv.context).asBitmap()
                    .load(this.get(0))
                    .apply(options)
                    .into(binding.imgDimv)
            }?:let {
                binding.imgRlay.visibility = View.GONE
            }
            binding.root.setOnClickListener {
                OnItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var OnItemClickListener: ((Patient) -> Unit)? = null

    fun setOnItemClickListner(listner: (Patient) -> Unit) {
        OnItemClickListener = listner
    }

}