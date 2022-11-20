package com.softuni.mobileexam.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.snackbar.Snackbar
import com.softuni.mobileexam.adapter.CurrencyAdapter
import com.softuni.mobileexam.data.AppDatabase
import com.softuni.mobileexam.databinding.ActivityMainBinding
import com.softuni.mobileexam.factory.CurrencyViewModelFactory
import com.softuni.mobileexam.repository.CurrencyRepository
import com.softuni.mobileexam.service.CurrencyService
import com.softuni.mobileexam.utils.Network
import com.softuni.mobileexam.viewmodel.CurrencyViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var currencyService: CurrencyService
    private lateinit var currencyRepository: CurrencyRepository
    lateinit var currencyViewModel: CurrencyViewModel
    private lateinit var db: RoomDatabase

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        observeData()

        if (!Network.isConnected(this)) {
            Snackbar.make(
                binding.root,
                "There is no internet connection, information could be outdated!",
                Snackbar.LENGTH_LONG
            ).show()
        }

        GlobalScope.launch {
            currencyViewModel.getCurrencies()
        }
    }

    private fun init() {
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "currencies_database"
        ).build()

        val currencyDao = (db as AppDatabase).currencyDao()
        currencyService = retrofit.create(CurrencyService::class.java)
        currencyRepository = CurrencyRepository(this, currencyService, currencyDao)
        val currencyViewModelFactory = CurrencyViewModelFactory(currencyRepository)
        currencyViewModel =
            ViewModelProvider(this, currencyViewModelFactory)[CurrencyViewModel::class.java]
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun observeData() {
        GlobalScope.launch {
            currencyViewModel.currenciesList.collect {
                runOnUiThread {
                    val currencies = it
                    val sortedCurrencies = currencies.sortedBy { it.marketCap }.sortedByDescending { it.favourite }
                    val adapter = CurrencyAdapter(sortedCurrencies)
                    binding.currenciesList.adapter = adapter
                    binding.tvCurrenciesCount.text = "Currencies: ${it.size}"
                }
            }
        }
    }
}