package com.crosslynx.customertrackerapp.api

import com.kch.testkuwait.Response
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    // for constraint type
    @Headers("Content-Type: application/json")
    @GET("latest?")
    fun getCurrencyConversionList(@Query("symbols") symbols: String, @Query("base") base:String): Call<Response>
}
