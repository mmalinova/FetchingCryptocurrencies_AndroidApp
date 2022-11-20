package com.softuni.mobileexam.model

data class CurrencyDetailsResponse(
    var image: String?,
    var name: String?,
    var symbol: String?,
    var current_price: Double?,
    var market_cap: Long?,
    var high_24h: Double?,
    var price_change_percentage_24h: Double?,
    var market_cap_change_percentage_24h: Double?,
    var low_24h: Double?,
)