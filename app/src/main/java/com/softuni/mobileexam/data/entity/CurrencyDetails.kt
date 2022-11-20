package com.softuni.mobileexam.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyDetails(
    @PrimaryKey
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "symbol") var symbol: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "current_price") var price: Double,
    @ColumnInfo(name = "market_cap") var marketCap: Long,
    @ColumnInfo(name = "high_24h") var high24h: Double,
    @ColumnInfo(name = "price_change_percentage_24h") var priceChangePercentage24h: Double,
    @ColumnInfo(name = "market_cap_change_percentage_24h") var marketCapChangePercentage24h: Double,
    @ColumnInfo(name = "low_24h") var low24h: Double,
    @ColumnInfo(name = "favourite") var favourite: Boolean
)