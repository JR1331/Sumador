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

import androidx.lifecycle.ViewModel
import com.example.sumador.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Price for a single cupcake */
private const val PRICE_PER_CUPCAKE = 2.00

/** Additional cost for same day pickup of an order */
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

/**
 * [OrderViewModel] holds information about a cupcake order in terms of quantity, flavor, and
 * pickup date. It also knows how to calculate the total price based on these order details.
 */
class OrderViewModel : ViewModel() {

    /**
     * Cupcake state for this order
     */
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))//Crea un flujo mutable, inicializa un objeto con las opciones de recogida
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()//Version publica del estado del usuario

    /**
     * Set the quantity [numberCupcakes] of cupcakes for this order's state and update the price
     */
    fun setQuantity(numberCupcakes: Int) {//Funcion que establece el numero de cupcakes de la orden y actualiza el estado de la _uiState
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes,
                price = calculatePrice(quantity = numberCupcakes)
            )
        }
    }
    /**
     * Reset the order state
     */
    fun resetOrder() {//Restablece el estado de la orden
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    /**
     * Returns the calculated price based on the order details.
     */
    private fun calculatePrice(//Calcula el precio de la orden basado en la cantidad y la fecha
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date
    ): String {
        var calculatedPrice = quantity * PRICE_PER_CUPCAKE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (pickupOptions()[0] == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        return formattedPrice
    }

    private fun calculateTotal(
        number1: Int = _uiState.value.number1,
        number2: Int = _uiState.value.number2
    ):String{
        var numeroTotal = number1 + number2

        val formattedTotal = numeroTotal.toString()
        return formattedTotal
    }

    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */
    private fun pickupOptions(): List<String> {//Genera opciones de recogida para los cuatro dias siguientes a la fecha del dia
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 3 dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}
