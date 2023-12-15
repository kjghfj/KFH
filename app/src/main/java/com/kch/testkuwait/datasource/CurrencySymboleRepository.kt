package com.kch.testkuwait.datasource

import com.kch.testkuwait.di.RemoteDataSource

class CurrencySymboleRepository  (private val remoteDataSource: RemoteDataSource) {
    fun currencyConversion() = remoteDataSource.getCurrencyConverion()
}