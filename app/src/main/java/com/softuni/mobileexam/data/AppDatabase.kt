package com.softuni.mobileexam.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softuni.mobileexam.data.dao.CurrencyDao
import com.softuni.mobileexam.data.entity.CurrencyDetails

@Database(entities = [CurrencyDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}