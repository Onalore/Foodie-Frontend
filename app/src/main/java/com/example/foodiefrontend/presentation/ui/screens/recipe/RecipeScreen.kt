package com.example.foodiefrontend.presentation.ui.screens.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.data.SampleData.recipe
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.RoundedImage

@Composable
fun RecipeScreen(navController: NavController, recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        // Image and title section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            RoundedImage(
                image = recipe.imageUrl,
                modifier = Modifier,
                modifierBox = Modifier
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                )
                Text(
                    text = "${recipe.preparation.size} min",
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ingredients section
        Text(
            text = "Ingredientes",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        recipe.ingredients.forEach { ingredient ->
            Text(
                text = "• ${ingredient.description} ${ingredient.quantity} ${ingredient.unit}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Preparation section
        Text(
            text = "Preparación",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        recipe.preparation.forEachIndexed { index, step ->
            Text(
                text = "${index + 1}. $step",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        RecipeScreen(rememberNavController(), recipe = recipe)
    }
}
