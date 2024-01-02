package com.test.walmartcountrylist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.walmartcountrylist.R
import com.test.walmartcountrylist.domain.data.CountryUiModel

class CountryListAdapter : ListAdapter<CountryUiModel, CountryItemViewHolder>(CountryDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryItemViewHolder {
        return CountryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.country_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CountryItemViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.itemView){
            findViewById<TextView>(R.id.textName).text = item.formattedName
            findViewById<TextView>(R.id.textCode).text = item.code
            findViewById<TextView>(R.id.textCapital).text = item.capital
        }
    }

}


class CountryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


class CountryDiff : DiffUtil.ItemCallback<CountryUiModel>() {
    override fun areItemsTheSame(oldItem: CountryUiModel, newItem: CountryUiModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CountryUiModel, newItem: CountryUiModel): Boolean {
        return oldItem.code == newItem.code
    }

}