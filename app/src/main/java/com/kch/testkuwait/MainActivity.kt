package com.kch.testkuwait

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.crosslynx.customertrackerapp.payment.paymenthistory.CurrencySymbolViewModel
import com.crosslynx.customertrackerapp.payment.paymenthistory.CurrencySymbolfactory

class MainActivity : BaseActivity() {

    private val currencySymbolViewModel: CurrencySymbolViewModel by viewModels {
        CurrencySymbolfactory.getInstance(applicationContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currencySymbolViewModel.currencyConversionList(this@MainActivity,this@MainActivity)
    }

    companion object{
        var currencyprogress: ProgressBar? = null
        var payhistoryAdapter:CurrencyConverterAdapter? = null
        var currencyconvertRv: RecyclerView? = null
    }
}