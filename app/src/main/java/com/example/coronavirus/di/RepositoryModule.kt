package com.example.coronavirus.di

import com.example.coronavirus.data.repository.CovidRepository
import com.example.coronavirus.data.repository.CovidRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
abstract class CovidRepositoryModule {

    @Binds
    abstract fun bindCovidRepository(
        covidRepositoryImpl: CovidRepositoryImpl
    ): CovidRepository
}
