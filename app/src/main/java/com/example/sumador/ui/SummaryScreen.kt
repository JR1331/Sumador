/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.sumador.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cupcake.R
import com.example.sumador.data.OrderUiState


/**
 * This composable expects [orderUiState] that represents the order state, [onCancelButtonClicked]
 * lambda that triggers canceling the order and passes the final order to [onSendButtonClicked]
 * lambda
 */
@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    number1: Int,
    number2: Int,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    val items = listOf(
        Pair("Number 1", number1),
        Pair("Number 2", number2),
        Pair("Sum", (number1 + number2).toString()) // Agrega esta línea para mostrar la suma
    )

    val numberPairs = rememberSaveable { mutableListOf<Pair<String, String>>() }

    // Añade las parejas de números a la lista
    numberPairs.add(Pair("Number 1", number1.toString()))
    numberPairs.add(Pair("Number 2", number2.toString()))

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        items.forEach { item ->
            item {
                Text(item.first.uppercase())
                Text(text = item.second.toString(), fontWeight = FontWeight.Bold)
                Divider(thickness = dimensionResource(R.dimen.thickness_divider))
            }
        }

        // Muestra las parejas de números en el historial
        numberPairs.forEach { pair ->
            item {
                Row {
                    Text("${pair.first}: ${pair.second}")
                }

            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCancelButtonClicked
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    }
}


