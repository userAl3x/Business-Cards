package com.example.business_card

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.business_card.ui.theme.Business_CardTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.material3.TriStateCheckbox

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Business_CardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCard(
                        modifier = Modifier.padding(innerPadding)
                    )
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

        //Linea del progess bar
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
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
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Encabezado con nombre e icono
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = cardName,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (showApellidos && cardApellidos.isNotEmpty()) {
                                Text(
                                    text = cardApellidos,
                                    fontSize = 20.sp
                                )
                            }
                        }

                        // Icono seleccionado
                        Icon(
                            imageVector = obtenerIcono(iconoSeleccionado),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Cargo y empresa
                    Column {
                        if (showCargo && cardCargo.isNotEmpty()) {
                            Text(
                                text = cardCargo,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        if (showEmpresa && cardEmpresa.isNotEmpty()) {
                            Text(
                                text = cardEmpresa,
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Información de contacto
                    Column {
                        if (cardEmail.isNotEmpty()) {       //Email
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = cardEmail, fontSize = 12.sp)
                            }
                        }
                        if (cardTelefono.isNotEmpty()) {    //Telefono
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = cardTelefono, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }

        //Spacer separador invisible
        Spacer(modifier = Modifier.height(16.dp))  //Separador

        // Switch para fondos coloridos
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = if (fondoColorido) "Fondos Coloridos" else "Fondos Neutros")
            Switch(
                checked = fondoColorido,
                onCheckedChange = { fondoColorido = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkboxes para mostrar/ocultar información
        Text("Información a incluir:", style = MaterialTheme.typography.titleMedium)

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Checkbox(
                checked = showApellidos,
                onCheckedChange = { showApellidos = it }
            )
            Text("Mostrar Apellidos")
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Checkbox(
                checked = showCargo,
                onCheckedChange = { showCargo = it }
            )
            Text("Mostrar Cargo")
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Checkbox(
                checked = showEmpresa,
                onCheckedChange = { showEmpresa = it }
            )
            Text("Mostrar Empresa")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TextFields para ingresar todos los datos
        Text("Datos personales:", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputName,
            onValueChange = { inputName = it },
            label = { Text("Nombre *") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputApellidos,
            onValueChange = { inputApellidos = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth(),
            enabled = showApellidos
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputCargo,
            onValueChange = { inputCargo = it },
            label = { Text("Cargo") },
            modifier = Modifier.fillMaxWidth(),
            enabled = showCargo
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputEmpresa,
            onValueChange = { inputEmpresa = it },
            label = { Text("Empresa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = showEmpresa
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputEmail,
            onValueChange = { inputEmail = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = inputTelefono,
            onValueChange = { inputTelefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para actualizar la tarjeta
        Button(
            onClick = {
                cardName = inputName.ifEmpty { "Tu Nombre" }
                cardApellidos = inputApellidos
                cardCargo = inputCargo
                cardEmpresa = inputEmpresa
                cardEmail = inputEmail
                cardTelefono = inputTelefono
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Actualizar Tarjeta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TriStateCheckbox para ajustar el borde
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            TriStateCheckbox(
                state = estadoTriState,
                onClick = {
                    estadoTriState = when(estadoTriState) {
                        ToggleableState.Off -> ToggleableState.Indeterminate
                        ToggleableState.Indeterminate -> ToggleableState.On
                        ToggleableState.On -> ToggleableState.Off
                    }
                }
            )
            Text("Borde de tarjeta (Off/Medio/Grueso)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Radio Buttons para seleccionar icono
        Text("Selecciona un icono:", style = MaterialTheme.typography.titleMedium)

        val iconos = listOf("Estrella", "Casa", "Favorito", "Configuración")

        iconos.forEach { icono ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = iconoSeleccionado == icono,
                    onClick = { iconoSeleccionado = icono }
                )
                Icon(
                    imageVector = obtenerIcono(icono),
                    contentDescription = icono,
                    modifier = Modifier.size(20.dp).padding(start = 8.dp)
                )
                Text(text = icono, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selector de imagen de fondo
        Text("Imagen de fondo:", style = MaterialTheme.typography.titleMedium)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            repeat(4) { index ->
                FilterChip(
                    selected = fondoSeleccionado == index,
                    onClick = { fondoSeleccionado = index },
                    label = { Text("Fondo ${index + 1}") }
                )
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

//funcion para los iconos de la tarjeta
fun obtenerIcono(nombre: String): ImageVector {
    return when(nombre) {
        "Estrella" -> Icons.Default.Star
        "Casa" -> Icons.Default.Home
        "Favorito" -> Icons.Default.Favorite
        "Configuración" -> Icons.Default.Settings
        else -> Icons.Default.Star
    }
}

//funcion para los colores de fondo de la tarjeta
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

//Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Business_CardTheme {
        BusinessCard()
    }
}