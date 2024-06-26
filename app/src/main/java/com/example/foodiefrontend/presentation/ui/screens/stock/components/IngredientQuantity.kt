package com.example.foodiefrontend.presentation.ui.screens.stock.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodiefrontend.R

@Composable
fun IngredientQuantity(
    quantity: String? = 0.toString(),
    unit: String?,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    available: Boolean = true
) {
    Card(
        modifier = Modifier
            .height(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (available)
                MaterialTheme.colorScheme.onSurface
            else
                Color(0xFFD8D8D8),
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (available) onDecrement() else {}
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = "Decrease quantity",
                    modifier = Modifier.size(10.dp)
                )
            }
            Text(text = "$quantity ${unit ?: ""}")
            IconButton(
                onClick = {
                    if (available) onIncrement() else {}
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Increase quantity",
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}
