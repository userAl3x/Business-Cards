package com.example.business_card

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.business_card.ui.theme.Business_CardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Business_CardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        BusinessCard()
                }
            }
        }
    }
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    // Estados para TextField
    var inputName by remember { mutableStateOf("") }
    var inputApellidos by remember { mutableStateOf("") }
    var inputCargo by remember { mutableStateOf("") }
    var inputEmpresa by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputTelefono by remember { mutableStateOf("") }

    // Datos de la tarjeta (lo que se muestra en la Card)
    var cardName by remember { mutableStateOf("Tu Nombre") }
    var cardApellidos by remember { mutableStateOf("") }
    var cardCargo by remember { mutableStateOf("") }
    var cardEmpresa by remember { mutableStateOf("") }
    var cardEmail by remember { mutableStateOf("") }
    var cardTelefono by remember { mutableStateOf("") }

    // Estados de los Checkbox
    var showApellidos by remember { mutableStateOf(true) }
    var showCargo by remember { mutableStateOf(true) }
    var showEmpresa by remember { mutableStateOf(true) }

    // Switch para color de fondo de la tarjeta
    var fondoColorido by remember { mutableStateOf(false) }

    // TriState para estilo de tarjeta (borde)
    var estadoTriState by remember { mutableStateOf(ToggleableState.Indeterminate) }

    // Radio Buttons para selección de icono
    var iconoSeleccionado by remember { mutableStateOf("Estrella") }

    // Selector de fondo (0-3)
    var fondoSeleccionado by remember { mutableStateOf(0) }

    // Progress Indicator
    val progreso = calcularProgreso(
        inputName, inputApellidos, inputCargo, inputEmpresa, inputEmail, inputTelefono,
        showApellidos, showCargo, showEmpresa
    )

    //Añadimos el column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Progress bar Indicator
        Text("Progreso de creación: ${(progreso * 100).toInt()}%",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp))

        //
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        // Tarjeta
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(vertical = 16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = obtenerColorFondo(fondoSeleccionado, fondoColorido
                ),
                contentColor = Color.Black
            ),
            border = BorderStroke(
                width = when(estadoTriState) {
                    ToggleableState.Off -> 0.dp
                    ToggleableState.Indeterminate -> 2.dp
                    ToggleableState.On -> 5.dp
                },
                color = Color.Black
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            //Box
            Box(modifier = Modifier.fillMaxSize()) {

            }
        }
    }
}

//funcion para calcular el Progreso de la ProgressBar
fun calcularProgreso(
    nombre: String,
    apellidos: String,
    cargo: String,
    empresa: String,
    email: String,
    telefono: String,
    showApellidos: Boolean,
    showCargo: Boolean,
    showEmpresa: Boolean

): Float {
    var completados = 0f
    val total = 6f

    if (nombre.isNotEmpty()) {
        completados++
    }
    if (apellidos.isNotEmpty() && showApellidos) {
        completados++
    }
    if (cargo.isNotEmpty() && showCargo) {
        completados++
    }
    if (empresa.isNotEmpty() && showEmpresa) {
        completados++
    }
    if (email.isNotEmpty()) {
        completados++
    }
    if (telefono.isNotEmpty()) {
        completados++
    }

    return completados / total
}

fun obtenerColorFondo(fondo: Int, colorido: Boolean): Color {
    if (!colorido) {
        // Fondos neutros (grises claros)
        return when(fondo) {
            0 -> Color(0xFFFFFFFF)      // Blanco
            1 -> Color(0xFFEEEEEE)      // Gris
            2 -> Color(0xFFE0E0E0)      // Gris medio-claro
            3 -> Color(0xFF757575)      // Gris medio-oscuro
            else -> Color(0xFFFFFFFF)   // Blanco
        }
    }

    // Fondos coloridos pero suaves
    return when(fondo) {
        0 -> Color(0xFFE3F2FD)      // Azul claro
        1 -> Color(0xFFFFF3E0)      // Naranja claro
        2 -> Color(0xFFF1F8E9)      // Verde claro
        3 -> Color(0xFFFCE4EC)      // Rosa claro
        else -> Color(0xFFE3F2FD)   //Azul claro
    }
}