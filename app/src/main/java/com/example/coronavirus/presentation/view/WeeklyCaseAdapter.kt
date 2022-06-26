package com.example.coronavirus.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coronavirus.R
import com.example.coronavirus.databinding.DailyCaseItemBinding
import com.example.coronavirus.databinding.WeeklyCaseItemBinding
import com.example.coronavirus.presentation.viewmodel.WeeklyCase

class WeeklyCaseAdapter(private val dataSet: MutableList<WeeklyCase>) :
    RecyclerView.Adapter<WeeklyCaseAdapter.ViewHolder>() {

    fun updateDateSet(dataSet: List<WeeklyCase>) {
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
        val context = viewHolder.binding.root.context
        with(viewHolder.binding) {
            val data = dataSet[position]
            date.text = data.date.toString()
            caseNumber.text = data.cumCases.toString()

            if (data.expand) {
                dailyList.visibility = View.VISIBLE
                dailyList.removeAllViews()
                data.dailyNewCase.forEach {
                    val binding =
                        DailyCaseItemBinding.inflate(LayoutInflater.from(context))
                    binding.number.text = it.toString()
                    dailyList.addView(binding.root)
                }
                expandIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.ic_expand_less_24dp,
                        context.theme
                    )
                )
            } else {
                dailyList.visibility = View.GONE
                expandIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.ic_expand_more_24dp,
                        context.theme
                    )
                )
            }


            weeklyTitle.setOnClickListener {
                data.expand = !data.expand
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(val binding: WeeklyCaseItemBinding) : RecyclerView.ViewHolder(binding.root)
}