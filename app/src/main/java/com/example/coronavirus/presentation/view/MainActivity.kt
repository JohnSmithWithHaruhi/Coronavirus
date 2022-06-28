package com.example.coronavirus.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronavirus.R
import com.example.coronavirus.databinding.ActivityMainBinding
import com.example.coronavirus.presentation.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Main screen for this application.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeeklyCasesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = WeeklyCasesAdapter(mutableListOf()).also {
                this@MainActivity.adapter = it
            }
        }

        binding.fab.setOnClickListener {
            viewModel.onShowDialog()
        }

        binding.reloadButton.setOnClickListener {
            viewModel.fetchWeeklyCaseList()
        }

        viewModel.weeklyCaseList().observe(this) {
            if (it.isEmpty()) {
                showReloadView()
            } else {
                hideReloadView()
                adapter.updateDateSet(it)
            }
        }

        viewModel.searchDialog().observe(this) {
            if (it.isShowDialog) {
                var selectedPosition = it.selectedItem
                MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.dialog_search_title)
                    .setSingleChoiceItems(
                        it.itemList.toTypedArray(),
                        it.selectedItem
                    ) { _, position ->
                        selectedPosition = position
                    }
                    .setNegativeButton(R.string.dialog_search_negative) { _, _ -> }
                    .setPositiveButton(R.string.dialog_search_positive) { _, _ ->
                        viewModel.onAreaSelected(selectedPosition)
                    }
                    .show()
            }
        }

        viewModel.fetchWeeklyCaseList()
    }

    private fun showReloadView() {
        binding.reloadView.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.INVISIBLE
    }

    private fun hideReloadView() {
        binding.reloadView.visibility = View.INVISIBLE
        binding.recyclerView.visibility = View.VISIBLE
    }
}