package com.example.coronavirus.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronavirus.databinding.ActivityMainBinding
import com.example.coronavirus.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = WeeklyCaseAdapter(dataSet = mutableListOf()).also {
            binding.recyclerView.adapter = it
        }
        binding.fab.setOnClickListener {
            viewModel.fetchWeeklyCaseList()
        }

        viewModel.weeklyCaseList().observe(this) {
            adapter.updateDateSet(it)
        }
    }
}