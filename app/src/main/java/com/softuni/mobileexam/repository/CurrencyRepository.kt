package com.softuni.mobileexam.repository

import android.content.Context
import android.util.Log
import com.softuni.mobileexam.data.dao.CurrencyDao
import com.softuni.mobileexam.data.entity.CurrencyDetails
import com.softuni.mobileexam.model.CurrencyDetailsResponse
import com.softuni.mobileexam.service.CurrencyService
import com.softuni.mobileexam.utils.Network

class CurrencyRepository constructor(
    private val context: Context,
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao
) {
    suspend fun getCurrencies(): List<CurrencyDetails> {
        return try {
            if (Network.isConnected(context)) {
                val currencies = currencyService.getCurrencies().execute().body()
                val roomCurrencies = currencies?.map { mapResponseToDbModel(it) }
                currencyDao.insertAll(roomCurrencies ?: return arrayListOf())
            }
            currencyDao.getCurrencies()
        } catch (e: Exception) {
            e.message?.let { Log.i("ERROR", it) }
            arrayListOf()
        }
    }

    suspend fun getCurrencyByName(name: String): CurrencyDetails? {
        return try {
            currencyDao.getCurrencyByName(name)
        } catch (e: Exception) {
            e.message?.let { Log.i("ERROR", it) }
            null
        }
    }

    suspend fun updateCurrency(currency: CurrencyDetails) {
        currencyDao.update(currency)
    }

    private fun mapResponseToDbModel(response: CurrencyDetailsResponse): CurrencyDetails {
        return CurrencyDetails(
            name = response.name ?: "",
            symbol = response.symbol ?: "",
            image = response.image ?: "",
            price = response.current_price ?: -1.0,
            marketCap = response.market_cap ?: -1,
            high24h = response.high_24h ?: -1.0,
            priceChangePercentage24h = response.price_change_percentage_24h ?: -1.0,
            marketCapChangePercentage24h = response.market_cap_change_percentage_24h ?: -1.0,
            low24h = response.low_24h ?: -1.0,
            favourite = false
        )
    }
}