package com.softuni.mobileexam.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.softuni.mobileexam.repository.CurrencyRepository
import com.softuni.mobileexam.viewmodel.CurrencyViewModel

class CurrencyViewModelFactory(
    private val currencyRepository: CurrencyRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(currencyRepository) as T
    }
}