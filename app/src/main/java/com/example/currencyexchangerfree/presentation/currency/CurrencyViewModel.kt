package com.example.currencyexchangerfree.presentation.currency

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangerfree.data.CurrencyApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CurrencyState(
    val result: String = "",
    val loading: Boolean = false
)

class CurrencyViewModel : ViewModel() {

    private val _state = MutableStateFlow(CurrencyState())
    val state = _state.asStateFlow()


    fun convert(amount: Double, from: String, to: String) {
        viewModelScope.launch {

            try {

                Log.d("CurrencyVM", "Starting API call")

                _state.value = _state.value.copy(loading = true)


                val converted = CurrencyApiService.convert(
                    amount,
                    from,
                    to
                )

                Log.d("CurrencyVM", "API success: $converted")
                _state.value = _state.value.copy(
                    loading = false,
                    result = "%.2f %s".format(converted, to)
                )
                Log.d("CurrencyVM", "Final result: ${_state.value.result}")

            } catch (e: Exception) {
                Log.e("CurrencyVM", "convert failed", e)
                e.printStackTrace()
            }
        }
    }
}