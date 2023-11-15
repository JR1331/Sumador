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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.cupcake.R


@Composable
fun StartOrderScreen(
    //quantityOptions: List<Pair<Int, Int>>,//El primer recurso es una cadena y el segundo el numero de cupcakes que hay asociados a ese string
    onNextButtonClicked: (Int,Int) -> Unit,//Accion a realiar al pulsar el boton siguiente, requiere de un int que es el numero de cupcakes
    modifier: Modifier = Modifier
){
    var number1 by rememberSaveable { mutableStateOf("") }
    var number2 by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 40.dp)
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.sumador),
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 40.dp)
                    .align(alignment = Alignment.Start)
            )
            EditNumberField(
                label = R.string.sum1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                value = number1,
                onValueChanged = { number1 = it },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
            )
            EditNumberField(
                label = R.string.sum2,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = number2,
                onValueChanged = { number2 = it },
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(150.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    // Deja el bloque onClick vacío para que no realice ninguna acción
                }
            ) {
                Text(stringResource(R.string.next))
            }
        }
        Row(modifier = Modifier.weight(1f, false)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.padding_medium)
                )
            ) {
                    SelectNextButton(

                        labelResourceId = R.string.next,

                        onClick = {
                            val intNumber1 = number1.toIntOrNull() ?: 0
                            val intNumber2 = number2.toIntOrNull() ?: 0
                            onNextButtonClicked(intNumber1,intNumber2) }//Ejecuta la accion proporcionada cuando se hace click
                    )
                }
            }
        }
    }


/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun SelectNextButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
}