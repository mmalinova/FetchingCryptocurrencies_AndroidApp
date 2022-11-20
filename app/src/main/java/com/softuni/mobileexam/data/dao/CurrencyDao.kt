package com.softuni.mobileexam.data.dao

import androidx.room.*
import com.softuni.mobileexam.data.entity.CurrencyDetails

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currencies")
    suspend fun getCurrencies(): List<CurrencyDetails>

    @Query("SELECT * FROM currencies WHERE name=:name")
    suspend fun getCurrencyByName(name: String): CurrencyDetails

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(currencies: List<CurrencyDetails>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(currency: CurrencyDetails)
}