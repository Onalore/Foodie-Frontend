package com.example.foodiefrontend.presentation.ui.screens.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Tu stock", style = MaterialTheme.typography.headlineLarge)
        TextField(
            value = "",
            onValueChange = {},
            placeholder = {
                Text(text = "Buscar ingrediente")
            }
        )
        Text(
            text = "matriz de stock, 3 columnas por fila"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    StockScreen(navController = rememberNavController())
}