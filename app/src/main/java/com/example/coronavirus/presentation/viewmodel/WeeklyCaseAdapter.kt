package com.example.coronavirus.presentation.viewmodel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coronavirus.databinding.WeeklyCaseItemBinding

class WeeklyCaseAdapter(private val dataSet: MutableList<WeeklyCase>) :
    RecyclerView.Adapter<WeeklyCaseAdapter.ViewHolder>() {

    fun updateDateSet(dataSet: List<WeeklyCase>){
        this.dataSet.clear()
        this.dataSet.addAll(dataSet)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            WeeklyCaseItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.binding) {
            title.text = dataSet[position].date.toString()
        }
    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(val binding: WeeklyCaseItemBinding) : RecyclerView.ViewHolder(binding.root)
}