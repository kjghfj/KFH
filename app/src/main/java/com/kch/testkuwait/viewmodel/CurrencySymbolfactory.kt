package com.crosslynx.customertrackerapp.payment.paymenthistory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kch.testkuwait.datasource.CurrencySymboleRepository
import com.kch.testkuwait.di.Injection

class CurrencySymbolfactory (private val currencyconversionRepository: CurrencySymboleRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencySymbolViewModel::class.java)) {
            return CurrencySymbolViewModel(currencyconversionRepository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: CurrencySymbolfactory? = null
        fun getInstance(context: Context): CurrencySymbolfactory =
            instance ?: synchronized(this) {
                instance ?: CurrencySymbolfactory(Injection.getCurrencyConversionRepository(context))
            }.also {
                instance = it
            }
    }
}