package com.example.currencyexchangerfree.domain

import kotlinx.serialization.Serializable

@Serializable
data class RateResponse(
    val amount: Double = 1.0,
    val base: String = "",
    val date: String = "",
    val rates: Map<String, Double> = emptyMap()
)

@Serializable
data class SingleRateResponse(
    val base: String,
    val quote: String,
    val date: String,
    val rate: Double
)