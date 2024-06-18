package com.example.foodiefrontend.presentation.ui.screens.stock.components

import android.app.Dialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.ui.components.CustomButton

@Composable
fun AlertIngredientScanned(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
    codeEan: String,
    ) {
    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Text(
                text = stringResource(R.string.is_this_product),
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
                        setShowDialog(false)
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
                        setShowDialog(false)
                    },
                    containerColor = Color(0xFFE8BB66),
                    text = stringResource(R.string.enter_manually),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}