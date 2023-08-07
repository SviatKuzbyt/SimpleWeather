package com.example.simpleweather.ui.elements

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweather.R
import com.example.simpleweather.data.elements.InfoDetail

class DetailAdapter(private var dataSet: List<InfoDetail>, private val context: Context) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val listLabel: TextView = view.findViewById(R.id.listLabel)
        val listValue: TextView = view.findViewById(R.id.listValue)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.detail_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.listLabel.text = context.getString(dataSet[position].label)
        viewHolder.listValue.text = dataSet[position].value
    }

    override fun getItemCount() = dataSet.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: List<InfoDetail>){
        dataSet = list
        notifyDataSetChanged()
    }
}