package com.kch.testkuwait.di

import android.util.Log
import com.crosslynx.customertrackerapp.api.ApiServices
import com.kch.testkuwait.ApiConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiServices: ApiServices) {
    /*
        Currenyc conversion rate list
     */
    fun getCurrencyConverion() = flow {
        emit(apiServices.getCurrencyConversionList(ApiConstant.SYMBOL,ApiConstant.BASE))
    }.catch {
        Log.d("login", "login: failed = ${it.message}")
    }.flowOn(Dispatchers.IO)
}