package com.softuni.mobileexam.service

import com.softuni.mobileexam.model.CurrencyDetailsResponse
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyService {

    @GET("coins/markets?vs_currency=usd")
    fun getCurrencies(): Call<List<CurrencyDetailsResponse>>
}