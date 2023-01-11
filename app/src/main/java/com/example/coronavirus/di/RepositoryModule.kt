package com.example.coronavirus.di

import com.example.covidcase.datasource.CovidNetworkDatasource
import com.example.covidcase.datasource.CovidService
import com.example.covidcase.repository.CovidRepository
import com.example.covidcase.repository.CovidRepositoryImpl
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
    abstract fun bindCovidRepository(
        covidRepositoryImpl: CovidRepositoryImpl
    ): CovidRepository
}

@InstallIn(SingletonComponent::class)
@Module
object NetworkDatasourceModule {

    @Provides
    @Singleton
    fun provideCovidNetworkDatasource(service: CovidService): CovidNetworkDatasource {
        return CovidNetworkDatasource(service)
    }

    @Provides
    @Singleton
    fun provideCovidService(retrofit: Retrofit): CovidService {
        return retrofit.create(CovidService::class.java)
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