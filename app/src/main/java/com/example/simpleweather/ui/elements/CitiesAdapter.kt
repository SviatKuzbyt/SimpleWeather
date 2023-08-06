package com.example.simpleweather.ui.elements

import android.annotation.SuppressLint
import android.widget.Button
import com.example.simpleweather.data.City
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweather.R
import com.example.simpleweather.ui.cities.CitiesViewModel
import java.util.Locale

class CitiesAdapter(private var dataSet: MutableList<City>, private val viewModel: CitiesViewModel) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val deleteBtn = view.findViewById<Button>(R.id.deleteBtn)
        val textCity = view.findViewById<TextView>(R.id.textCity)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.city_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textCity.text =
            if(Locale.getDefault().language == "uk") dataSet[position].nameUa
            else dataSet[position].nameEn

        viewHolder.deleteBtn.setOnClickListener {
            viewModel.deleteCity(dataSet[position])
            dataSet.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(list: MutableList<City>){
        dataSet = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataSet.size

}
