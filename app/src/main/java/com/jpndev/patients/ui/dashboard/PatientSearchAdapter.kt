package com.jpndev.newsapiclient.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jpndev.patients.R
import com.jpndev.patients.data.model.Patient
import com.jpndev.patients.data.repository.dataSourceImpl.LogSourceImpl
import com.jpndev.patients.databinding.RcvItemPitemBinding
import com.jpndev.patients.ui.dashboard.SearchViewModel
import com.jpndev.patients.utils.Common
import com.jpndev.patients.utils.DIGITS
import com.jpndev.patients.utils.extensions.conversionTime


class PatientSearchAdapter(val context: Context) :
    RecyclerView.Adapter<PatientSearchAdapter.MyViewHolder>(),
    Filterable {
    //private var filteredList = itemList.toMutableList()
    private var itemlist: MutableList<Patient> = mutableListOf()
    private var filteredList: MutableList<Patient> = mutableListOf()

    init {
        this.filteredList = itemlist
    }

    fun setList(list: MutableList<Patient>) {
        filteredList= mutableListOf()
        filteredList.clear()
        filteredList.addAll(list)
        itemlist= mutableListOf()
        itemlist.clear()
        itemlist.addAll(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charString: CharSequence?): FilterResults {

                val filteredResults = itemlist.filter {
                    LogSourceImpl().addLog("PSA performFiltering it.name: " + it.name + " charString.toString(): " + charString.toString())
                    it.name.contains(charString.toString(), ignoreCase = true) ||
                            it.phone.contains(charString.toString(), ignoreCase = true) ||
                            it.email.contains(charString.toString(), ignoreCase = true) ||
                            it.notes.contains(charString.toString(), ignoreCase = true)||
                            it.age.toString().contains(charString.toString(), ignoreCase = true)
                }
                LogSourceImpl().addLog("PSA performFiltering newText: " + charString+" filteredResults szie: "+filteredResults?.size)
                /*     if (charString.isEmpty()) {
                         filteredList = itemlist as ArrayList<Patient>
                     } else {
                         val filteredList = ArrayList<Patient>()
                         if(itemlist?.isNotEmpty()?:false) {
                             for (row in itemlist!!) {
                                 if (row.name!!.toLowerCase().contains(charString.toLowerCase())) {
                                     filteredList.add(row)
                                 }
                             }
                         }else
                         {
                             filteredList = result_searchlist as ArrayList<Patient>
                         }
                         filteredList = filteredList
                     }*/
                val results = FilterResults()
                results.values = filteredResults
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList.clear()
                filteredList.addAll(results?.values as MutableList<Patient>)
                LogSourceImpl().addLog("PSA publishResults filteredList: "+filteredList.size )

                    notifyDataSetChanged()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RcvItemPitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        LogSourceImpl().addLog("PSA getItemCount: "+filteredList.size )
        return filteredList.size
    }

    fun getItems(): MutableList<Patient>? {
        return filteredList
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        LogSourceImpl().addLog("PSA onBindViewHolder: "+filteredList.size+" position: "+position )
        val item = filteredList.get(position)
        holder.bind(item)
    }

    private var viewModel: SearchViewModel? = null

    fun setViewModel(temp: SearchViewModel) {
        viewModel = temp
    }

    val options: RequestOptions =
        RequestOptions().override(350).transform(CenterCrop(), RoundedCorners(40))

    inner class MyViewHolder(val binding: RcvItemPitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Patient) {
            binding.nameTxv.text = item.name
            binding.phoneTxv.text = item.phone
            binding.ageTxv.text = context.getString(R.string.age)+ Common.SEMICOLON_SPACE +item.age.toString()
            binding.lastVisitedDateTxv.text= item.lastConsulatedDate.time.conversionTime()
            item.imageList?.takeIf { it.isNotEmpty() }?.apply {
                binding.imgRlay.visibility = View.VISIBLE
                Glide.with(binding.imgDimv.context).asBitmap()
                    .load(this.get(DIGITS.ZERO))
                    .apply(options)
                    .into(binding.imgDimv)
            } ?: let {
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