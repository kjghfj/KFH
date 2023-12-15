package com.kch.testkuwait.di

import android.content.Context
import com.crosslynx.customertrackerapp.api.ApiConfig
import com.kch.testkuwait.datasource.CurrencySymboleRepository

object Injection {
   /*
    Currency Symbol List with conversion
    */
    fun getCurrencyConversionRepository(context: Context): CurrencySymboleRepository {
        val apiServices = ApiConfig.getApiService()
        val remoteDataSource = RemoteDataSource(apiServices)
        return CurrencySymboleRepository(remoteDataSource)
    }
}