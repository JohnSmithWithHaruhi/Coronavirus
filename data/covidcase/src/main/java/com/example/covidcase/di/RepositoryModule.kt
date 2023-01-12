package com.example.covidcase.di

import com.example.covidcase.datasource.CovidCaseNetworkDatasource
import com.example.covidcase.datasource.CovidCaseService
import com.example.covidcase.repository.CovidCaseRepository
import com.example.covidcase.repository.CovidCaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCovidCaseRepository(
        covidRepositoryImpl: CovidCaseRepositoryImpl
    ): CovidCaseRepository
}

@InstallIn(SingletonComponent::class)
@Module
object NetworkDatasourceModule {

    @Provides
    @Singleton
    fun provideCovidCaseNetworkDatasource(service: CovidCaseService): CovidCaseNetworkDatasource {
        return CovidCaseNetworkDatasource(service)
    }

    @Provides
    @Singleton
    fun provideCovidCaseService(retrofit: Retrofit): CovidCaseService {
        return retrofit.create(CovidCaseService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val baseUrl = "https://api.coronavirus.data.gov.uk/v1/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}