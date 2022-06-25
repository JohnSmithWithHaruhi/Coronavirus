package com.example.coronavirus.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coronavirus.databinding.ActivityMainBinding
import com.example.coronavirus.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WeeklyCaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = WeeklyCaseAdapter(mutableListOf()).also {
                this@MainActivity.adapter = it
            }
        }
        binding.fab.setOnClickListener {
            viewModel.fetchWeeklyCaseList()
        }

        viewModel.weeklyCaseList().observe(this) {
            adapter.updateDateSet(it)
        }
    }
}