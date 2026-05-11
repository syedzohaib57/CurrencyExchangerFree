package com.example.currencyexchangerfree.presentation.currency

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: CurrencyViewModel = viewModel()
) {

    val currencies = listOf(
        "USD",
        "PKR",
        "EUR",
        "GBP",
        "INR",
        "JPY",
        "AED",
        "SAR",
        "CAD",
        "AUD"
    )

    var amount by remember {
        mutableStateOf("")
    }

    var fromCurrency by remember {
        mutableStateOf("USD")
    }

    var toCurrency by remember {
        mutableStateOf("PKR")
    }

    var fromExpanded by remember {
        mutableStateOf(false)
    }

    var toExpanded by remember {
        mutableStateOf(false)
    }

    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Enter Amount")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // FROM DROPDOWN
            ExposedDropdownMenuBox(
                expanded = fromExpanded,
                onExpandedChange = {
                    fromExpanded = !fromExpanded
                },
                modifier = Modifier.weight(1f)
            ) {

                OutlinedTextField(
                    value = fromCurrency,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("From")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = fromExpanded
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = fromExpanded,
                    onDismissRequest = {
                        fromExpanded = false
                    }
                ) {

                    currencies.forEach { currency ->

                        DropdownMenuItem(
                            text = {
                                Text(currency)
                            },
                            onClick = {
                                fromCurrency = currency
                                fromExpanded = false
                            }
                        )
                    }
                }
            }

            // TO DROPDOWN
            ExposedDropdownMenuBox(
                expanded = toExpanded,
                onExpandedChange = {
                    toExpanded = !toExpanded
                },
                modifier = Modifier.weight(1f)
            ) {

                OutlinedTextField(
                    value = toCurrency,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("To")
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = toExpanded
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = toExpanded,
                    onDismissRequest = {
                        toExpanded = false
                    }
                ) {

                    currencies.forEach { currency ->

                        DropdownMenuItem(
                            text = {
                                Text(currency)
                            },
                            onClick = {
                                toCurrency = currency
                                toExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val amountDouble = amount.toDoubleOrNull() ?: 0.0

                viewModel.convert(
                    amount = amountDouble,
                    from = fromCurrency,
                    to = toCurrency
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Convert Currency")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (state.loading) {
            CircularProgressIndicator()
        } else if (state.result.isNotEmpty()) {
            Text(
                text = state.result,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}