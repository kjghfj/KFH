package com.crosslynx.customertrackerapp.payment.paymenthistory

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.crosslynx.customertrackerapp.api.ApiConfig
import com.kch.testkuwait.*
import com.kch.testkuwait.datasource.CurrencySymboleRepository
import kotlinx.coroutines.launch
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class CurrencySymbolViewModel (private val currencyRateRepository: CurrencySymboleRepository) : ViewModel() {

    // Define the hostname for SSL pinning
    private val pinnedHost = ApiConstant.BASE

    // Define the public key hashes for the server's SSL certificate
    private val publicKeyHash = "2048 SHA256:hYAU9plz1WZ+H+eZCushetKpeT5RXEnR8e5xsbFWRiU" +
            "\n"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .sslSocketFactory(createSSLSocketFactory()!!, createTrustManager())
            .certificatePinner(
                CertificatePinner.Builder()
                    .add(pinnedHost, publicKeyHash)
                    .build()
            )
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://$pinnedHost/") // Replace with your actual base URL
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    private val service: ApiConfig by lazy {
        retrofit.create(ApiConfig::class.java) // Replace with your actual Retrofit service interface
    }

    private fun createSSLSocketFactory(): javax.net.ssl.SSLSocketFactory? {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, createTrustManager(), SecureRandom())
        return sslContext.socketFactory
    }

    private fun createTrustManager(): Array<X509TrustManager> {
        return arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                    // Implement your custom trust logic here
                    // You can compare the server's certificate with the known public key hashes
                    // and decide whether to trust the connection or not.
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )
    }


    fun currencyConversionList(context: Context, baseActivity: BaseActivity) =
        viewModelScope.launch {
            currencyRateRepository.currencyConversion().collect {
                it.enqueue(object : Callback<Responses> {
                    override fun onResponse(
                        call: Call<Responses>,
                        response: Response<Responses>
                    ) {
                        // for getting response
                        var contexts = baseActivity
                        MainActivity.currencyprogress = contexts.findViewById(R.id.currencyprogress)
                        MainActivity.currencyprogress?.visibility = View.GONE

                        if (response.isSuccessful) {
                                val resultItems = response.body()?.base
                                if (resultItems != null && resultItems.isNotEmpty()) {
                                    Toast.makeText(
                                        contexts,
                                        response.body()?.base,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    MainActivity.currencyconvertRv =
                                        contexts.findViewById(R.id.currencyconvert)
                                    MainActivity.currencyconvertRv?.layoutManager =
                                        LinearLayoutManager(
                                            contexts,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                    MainActivity.payhistoryAdapter =
                                        CurrencyConverterAdapter(resultItems as List<Rates>)
                                    MainActivity.currencyconvertRv?.adapter =
                                        MainActivity.payhistoryAdapter
                                }
                            } else {
                                // Handle the case when authentication fails
                                Toast.makeText(
                                    contexts,
                                    "Auth failed: ${response.body()?.base}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }

                    override fun onFailure(call: Call<Responses>, t: Throwable) {
                        val context = baseActivity
                        if (t is SocketTimeoutException) {
                            Toast.makeText(
                                context,
                                "Request timed out. Check your internet connection.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Request failed: ${t.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            }
        }
}