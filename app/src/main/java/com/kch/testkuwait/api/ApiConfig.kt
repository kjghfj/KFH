package com.crosslynx.customertrackerapp.api

import android.content.Context
import com.google.gson.GsonBuilder
import com.kch.testkuwait.ApiConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
class ApiConfig {
    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                // You can customize how you log the message here.
                // For simplicity, we'll just print it to the console.
                // You might want to use a more advanced logging system in your actual app.
                println("API Log: $message")
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY // Log both requests and responses
        }
        // Custom Interceptor to add Authorization header
        private val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequestBuilder: Request.Builder = originalRequest.newBuilder()
                .addHeader("apikey", "PH4PwKQPjPA2zMLDUITfZHTXzXWFBcdX")
            val newRequest: Request = newRequestBuilder.build()
            chain.proceed(newRequest)
        }

        private val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor) // Add the authInterceptor
            .addInterceptor(loggingInterceptor)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiConstant.baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        fun init(context: Context) {
            /*mPreferences = PreferenceManager(context)*/
        }

        fun getApiService(): ApiServices = retrofit.create(ApiServices::class.java)
    }
}
