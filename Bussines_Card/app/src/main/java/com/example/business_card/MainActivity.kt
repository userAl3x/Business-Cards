package com.example.business_card

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
}