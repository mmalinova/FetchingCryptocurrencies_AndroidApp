package com.softuni.mobileexam.viewmodel

import androidx.lifecycle.ViewModel
import com.softuni.mobileexam.data.entity.CurrencyDetails
import com.softuni.mobileexam.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrencyViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val currenciesListStateFlow = MutableStateFlow<List<CurrencyDetails>>(arrayListOf())
    private val selectedCurrencyStateFlow = MutableStateFlow<CurrencyDetails?>(null)
    val currenciesList: StateFlow<List<CurrencyDetails>> = currenciesListStateFlow.asStateFlow()
    val selectedCurrency: StateFlow<CurrencyDetails?> = selectedCurrencyStateFlow.asStateFlow()

    suspend fun getCurrencies() {
        val currencies = currencyRepository.getCurrencies()
        currenciesListStateFlow.value = currencies
    }

    suspend fun getCurrencyByName(name: String) {
        val currency = currencyRepository.getCurrencyByName(name)
        selectedCurrencyStateFlow.value = currency ?: return
    }

    suspend fun updateFavourites(currency: CurrencyDetails) {
        currencyRepository.updateCurrency(currency)
        selectedCurrencyStateFlow.value =
            selectedCurrencyStateFlow.value?.copy(favourite = currency.favourite)
    }
}