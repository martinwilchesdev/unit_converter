package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import java.time.format.TextStyle
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("0.0") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var inputExpanded by remember { mutableStateOf(false) }
    var outputExpanded by remember { mutableStateOf(false) }
    var convertionFactorInput = remember { mutableDoubleStateOf(1.0) }
    var convertionFactorOutput = remember { mutableDoubleStateOf(1.0) }

    fun convertUnits() {
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result =
            (inputValueDouble * convertionFactorInput.value * 100.0 / convertionFactorOutput.value).roundToInt() / 100.0
        outputValue = result.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()// El elemento ocupa el espacio total, tanto a lo ancho como a lo alto
            .background(Color.White), // Color de fondo del componente
        verticalArrangement = Arrangement.Center, // Alineacion vertical del componente
        horizontalAlignment = Alignment.CenterHorizontally // Alineacion horizontal del elemento
    ) {
        // Los elementos dentro de una columna se añadiran a la interfaz uno sobre otro
        Text(
            "Unit Converter",
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.headlineLarge
        )
        OutlinedTextField(value = inputValue, onValueChange = {
            inputValue = it
            convertUnits()
        }, label = {
            Text("Enter value")
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

        // Spacer() es un composable utilizado para añadir espaciado entre multiples componentes
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Los elementos dentro de una fila son añadidos a la interfaz uno al lado del otro

            // Box es un elemento de la interfaz, el cual organiza y apila componentes uno sobre otro
            Box {
                // Input Button
                Button(onClick = {
                    inputExpanded = true
                }) {
                    Text(inputUnit)
                    Icon(Icons.Default.ArrowDropDown, "Arrow Dropdown")
                }
                // onDismissRequest se ejecuta cuando el usuario solicita salir del menu
                DropdownMenu(expanded = inputExpanded, onDismissRequest = {
                    inputExpanded = false
                }) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        inputUnit = "Centimeters"
                        convertionFactorInput.value = 0.01
                        inputExpanded = false
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        inputUnit = "Meters"
                        convertionFactorInput.value = 1.0
                        inputExpanded = false
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        inputUnit = "Feet"
                        convertionFactorInput.value = 0.3048
                        inputExpanded = false
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Milimeters") }, onClick = {
                        inputUnit = "Milimeters"
                        convertionFactorInput.value = 0.001
                        inputExpanded = false
                        convertUnits()
                    })
                }
            }

            Icon(
                Icons.Default.ArrowForward,
                "Arrow forward",
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Box {
                // Output button
                Button(onClick = {
                    outputExpanded = true
                }) {
                    Text(outputUnit)
                    Icon(Icons.Default.ArrowDropDown, "Arrow Dropdown")
                }
                // onDismissRequest se ejecuta cuando el usuario solicita salir del menu
                DropdownMenu(expanded = outputExpanded, onDismissRequest = {
                    outputExpanded = false
                }) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        outputUnit = "Centimeters"
                        convertionFactorOutput.value = 0.01
                        outputExpanded = false
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        outputUnit = "Meters"
                        convertionFactorOutput.value = 1.0
                        outputExpanded = false
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        outputUnit = "Feet"
                        convertionFactorOutput.value = 0.3048
                        outputExpanded = false
                        convertUnits()
                    })
                    DropdownMenuItem(text = { Text("Milimeters") }, onClick = {
                        outputUnit = "Milimeters"
                        convertionFactorOutput.value = 0.001
                        outputExpanded = false
                        convertUnits()
                    })
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Result text
        Text("Result: $outputValue $outputUnit", style = MaterialTheme.typography.headlineMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}

