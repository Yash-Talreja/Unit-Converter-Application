package com.example.unitconverter.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun UnitConverterUi() {
    var input by remember {
        mutableStateOf("")
    }
    var output by remember {
        mutableStateOf("")
    }

    var inputunit by remember {
        mutableStateOf("Meters")
    }
    var outputunit by remember {
        mutableStateOf("Meters")
    }

    var isinputExpanded by remember {
        mutableStateOf(false)
    }
    var isOutputExpanded by remember {
        mutableStateOf(false)
    }

    var inputConversionFactor by remember {
        mutableStateOf(0.1)
    }

    var OutputConversionFactor by remember {
        mutableStateOf(0.1)
    }

    fun ConvertUnits() {
        val inputValue = input.toDoubleOrNull() ?: 0.0
        val result =
            ((inputValue * inputConversionFactor / OutputConversionFactor) * 100).roundToInt() / 100
        output = result.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) { Text(text = "Unit Converter", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = Color.Blue) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {



        OutlinedTextField(value = input,
            onValueChange = {
                input = it
                ConvertUnits()
            }, label = { Text(text = "Enter value") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(66.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            DropDownBtn(
                label = inputunit,
                expanded = isinputExpanded,
                onExpandedChange = { isinputExpanded = it },
                onOptionSelect = { Unit, factor ->
                    inputunit = Unit
                    inputConversionFactor = factor
                    ConvertUnits()

                }

            )
            Spacer(modifier = Modifier.height(56.dp))

            DropDownBtn(
                label = outputunit,
                expanded = isOutputExpanded,
                onExpandedChange = { isOutputExpanded = it },
                onOptionSelect = { Unit, factor ->
                    outputunit = Unit
                    OutputConversionFactor = factor
                    ConvertUnits()
                }
            )

        }

        Spacer(modifier = Modifier.height(66.dp))

        Text(text = "Result: $output  $outputunit", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable

fun DropDownBtn(
    label: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelect: (String, Double) -> Unit
) {
    Box()
    {
        Button(
            onClick = { onExpandedChange(!expanded) },
            modifier = Modifier.wrapContentSize()
        ) {

            Text(text = label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown, contentDescription = "",
                modifier = Modifier.rotate(if (expanded) 180f else 0f)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.TopStart)



        ) {
            listOf(
                "Centimeters" to 0.01,
                "Meters" to 1.0,
                "Feet" to 0.3048,
                "Millimeters" to 0.001,
                "Kilometers" to 1000.0,
                "Inch" to 0.0254

            ).forEach { (unit, factor) ->
                DropdownMenuItem(
                    text = { Text(text = unit) },
                    onClick = {
                        onExpandedChange(false)
                        onOptionSelect(unit, factor)
                    }
                )
            }
        }
    }
}