package com.example.currencyexchangerfree.data

import android.util.Log
import com.example.currencyexchangerfree.domain.RateResponse
import com.example.currencyexchangerfree.domain.SingleRateResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object CurrencyApiService {

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    coerceInputValues = true
                }
            )
        }
    }

    suspend fun getRate(
        base: String,
        quote: String
    ): SingleRateResponse {
        return client.get("https://api.frankfurter.dev/v2/rate/${base.uppercase()}/${quote.uppercase()}")
            .body()
    }

    suspend fun convert(
        amount: Double,
        base: String,
        quote: String
    ): Double {
        Log.d("ApiService", "Calling Frankfurter API")

        val response = getRate(base, quote)

        Log.d("ApiService", "Rate response: $response")

        return amount * response.rate
    }

//    suspend fun latestRates(
//        base: String,
//        quotes: String
//    ): RateResponse {
//        return client.get("https://api.frankfurter.dev/v2/rates") {
//            parameter("base", base)
//            parameter("quotes", quotes)
//        }.body()
//    }
}