package com.example.business_card

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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