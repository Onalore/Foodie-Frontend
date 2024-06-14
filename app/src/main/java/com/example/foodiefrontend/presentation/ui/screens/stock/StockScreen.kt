package com.example.foodiefrontend.presentation.ui.screens.stock

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.Title


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(navController: NavController, codeEan: String? = "") {
    var ingredient by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(codeEan != null && codeEan.isNotEmpty()) }

    Log.d("Barcode", "Código recibido en stock: $codeEan")

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "¿Escaneaste este producto?",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = codeEan ?: "Producto desconocido",
                        textAlign = TextAlign.Center
                    )
                }
            },
            dismissButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomButton(
                        onClick = {
                            navController.navigate("camera_screen")
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        icon = R.drawable.ic_retry,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    CustomButton(
                        onClick = {
                            showDialog = false
                            //TODO
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        icon = R.drawable.ic_check,
                        modifier = Modifier.weight(1f)
                    )
                }
            },
            confirmButton = {
                Column(modifier = Modifier.fillMaxWidth()) {

                    CustomButton(
                        onClick = {
                            showDialog = false
                            // Acción de confirmar
                        },
                        containerColor = Color(0xFFE8BB66),
                        text = "Ingresar manualmente",
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row {
                Title(
                    title = stringResource(R.string.your_stock),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 30.dp)
                )
                ImageWithResource(
                    resourceId = R.drawable.ic_scan,
                    onClick = {
                        navController.navigate("camera_screen")
                    }
                )
            }

            CustomTextField(
                value = ingredient,
                placeholder = stringResource(R.string.look_for_ingredient),
                onValueChange = { newValue ->
                    ingredient = newValue
                },
                trailingIcon = R.drawable.ic_search,
                modifier = Modifier
            )
        }
    }
    }

@Preview(showBackground = true)
@Composable
fun Preview() {
    FoodieFrontendTheme {
        StockScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScaned() {
    FoodieFrontendTheme {
        StockScreen(navController = rememberNavController(), "123498389")
    }
}