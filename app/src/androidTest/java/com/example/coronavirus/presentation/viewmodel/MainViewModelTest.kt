package com.example.coronavirus.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpViewModel() {
        viewModel = MainViewModel()
    }

    @Test
    fun onShowDialog_isShowDialogIsTrue() {
        val expected = true

        viewModel.onShowDialog()

        assertEquals(expected, viewModel.searchDialog().value?.isShowDialog ?: false)
    }

    @Test
    fun onAreaSelected_getsCorrectArea() {
        val expected = 1

        viewModel.onAreaSelected(1)

        assertEquals(expected, viewModel.searchDialog().value?.selectedItem ?: 0)
    }
}