package com.example.coronavirus.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coronavirus.R
import com.example.coronavirus.databinding.ItemDailyCaseBinding
import com.example.coronavirus.databinding.ItemWeeklyCaseBinding
import com.example.coronavirus.presentation.viewmodel.WeeklyCase

/**
 * Class for handling weekly cases.
 */
class WeeklyCasesAdapter(private val weeklyCaseList: MutableList<WeeklyCase>) :
    RecyclerView.Adapter<WeeklyCasesAdapter.ViewHolder>() {

    /**
     * Updates all data set.
     *
     * @param weeklyCaseList the data you want to show in recycler view.
     */
    fun updateDateSet(weeklyCaseList: List<WeeklyCase>) {
        this.weeklyCaseList.clear()
        this.weeklyCaseList.addAll(weeklyCaseList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWeeklyCaseBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.binding.root.context
        with(viewHolder.binding) {
            val weeklyCase = weeklyCaseList[position]
            date.text = weeklyCase.date
            weeklyCaseNumber.text = weeklyCase.weeklyCumCases.toString()
            totalCaseNumber.text = "Total: ${weeklyCase.totalCumCases}"

            if (weeklyCase.expand) {
                weeklyCaseNumber.visibility = View.INVISIBLE
                totalCaseNumber.visibility = View.VISIBLE
                dailyList.visibility = View.VISIBLE
                dailyList.removeAllViews()
                weeklyCase.dailyCaseList.forEach {
                    val binding =
                        ItemDailyCaseBinding.inflate(LayoutInflater.from(context))
                    binding.dayOfWeek.text = it.dayOfWeek
                    binding.number.text = it.dailyCumCases.toString()
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
                weeklyCaseNumber.visibility = View.VISIBLE
                totalCaseNumber.visibility = View.INVISIBLE
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
                weeklyCase.expand = !weeklyCase.expand
                notifyItemChanged(position)
            }
        }
    }

    override fun getItemCount() = weeklyCaseList.size

    /**
     * Class for weekly case list adapter.
     */
    class ViewHolder(val binding: ItemWeeklyCaseBinding) : RecyclerView.ViewHolder(binding.root)
}