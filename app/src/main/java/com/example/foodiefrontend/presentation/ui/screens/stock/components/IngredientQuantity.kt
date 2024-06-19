package com.example.foodiefrontend.presentation.ui.screens.stock.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodiefrontend.R

@Composable
fun IngredientQuantity(
    quantity: String,
    unit: String,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(30.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onDecrement) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_remove),
                    contentDescription = "Decrease quantity",
                    modifier = Modifier.size(10.dp)
                )
            }
            Text(text = "$quantity $unit")
            IconButton(onClick = onIncrement) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Increase quantity",
                    modifier = Modifier.size(10.dp)
                )
            }
        }
    }
}