package com.example.foodiefrontend.presentation.ui.screens.stock

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.screens.stock.components.IngredientQuantity

@Composable
fun IngredientCard(
    ingredient: Ingredient,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modify: Boolean = true,
    alertaEscasez: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .width(160.dp)
            .padding(8.dp),
        backgroundColor = Color(0xFFEDEDED)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            val imageUrl = ingredient.imageUrl ?: "https://via.placeholder.com/150"
            val painter = rememberAsyncImagePainter(model = imageUrl)

            var showTooltip by remember { mutableStateOf(false) }

            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    Log.d("IngredientCard", "Loading image: $imageUrl")
                }

                is AsyncImagePainter.State.Success -> {
                    Log.d("IngredientCard", "Successfully loaded image: $imageUrl")
                }

                is AsyncImagePainter.State.Error -> {
                    Log.e("IngredientCard", "Error loading image: $imageUrl")
                }

                else -> {
                    Log.d("IngredientCard", "State: ${painter.state}")
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (alertaEscasez) {
                    ImageWithResource(
                        resourceId = R.drawable.ic_alert,
                        onClick = {  showTooltip = true },
                        modifier = Modifier
                            .height(20.dp)
                            .align(Alignment.TopStart)
                    )
                }

                if (showTooltip) {
                    Popup(
                        alignment = Alignment.CenterStart,
                        onDismissRequest = { showTooltip = false }
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.tertiary,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Alerta de escasez",
                                color = Color.White
                            )
                        }
                    }
                }

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            ingredient.id?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            ingredient.quantity?.let {
                IngredientQuantity(
                    quantity = it,
                    unit = ingredient.unit,
                    onDecrement = onDecrement,
                    onIncrement = onIncrement,
                    modify = modify
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewIngredientCard() {
    FoodieFrontendTheme {
        IngredientCard(
            ingredient = SampleData.sampleIngredient,
            onIncrement = { /* Implement increment action */ },
            onDecrement = { /* Implement decrement action */ }
        )
    }
}

@Preview
@Composable
fun PreviewIngredientCard2() {
    FoodieFrontendTheme {
        IngredientCard(
            ingredient = SampleData.sampleIngredient,
            onIncrement = { /* Implement increment action */ },
            onDecrement = { /* Implement decrement action */ },
            modify = false,
            alertaEscasez = true
        )
    }
}
