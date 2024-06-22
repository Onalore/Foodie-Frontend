package com.example.foodiefrontend.presentation.ui.screens.stock

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.screens.stock.components.IngredientQuantity

@Composable
fun IngredientCard(
    ingredient: Ingredient,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
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
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ingredient.id?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle1,
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
                    onIncrement = onIncrement
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
