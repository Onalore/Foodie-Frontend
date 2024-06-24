@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodiefrontend.presentation.ui.screens.recipes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.RecipeDescription
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
                    text = recipe.timeOfPrep.toString() + " min",
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
                text = "• $ingredient",
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


@Composable
fun RecipesCardItem(
    title: String,
    image: String,
    onClick: () -> Unit,
    initialRating: Int? = null,
    modifier: Modifier = Modifier,
    scored: Boolean = true
) {
    Box(modifier = Modifier.clickable {
        Log.d("RecipesCardItem", "Clicked on: $title")
        onClick()
    }) {
        RoundedImage(
            image = image,
            modifier = Modifier
                .padding(end = 10.dp)
                .width(200.dp)
                .height(150.dp)
        )

        RecipeDescription(
            name = title,
            modifier = Modifier
                .padding(start = 100.dp, top = 10.dp)
                .width(270.dp)
                .height(120.dp),
            punctuation = false,
            initialRating = initialRating ?: 0,
            scored = scored
        )
    }
}

@Composable
fun HorizontalButtonCategories(items: List<Pair<String, () -> Unit>>) {
    var selectedButtonIndex by remember { mutableStateOf(0) }
    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEachIndexed { index, item ->
            item {
                CustomButton(
                    text = item.first,
                    onClick = {
                        selectedButtonIndex = index
                        item.second()
                    },
                    containerColor = if (selectedButtonIndex == index) MaterialTheme.colorScheme.primary else Color.Transparent,
                    contentColor = if (selectedButtonIndex == index) Color.White else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .widthIn(155.dp)
                        .height(45.dp)
                )
            }
        }
    }
}

@Composable
fun VerticalRecipes(items: List<Recipe>) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { item ->
            RecipesCardItem(
                title = item.name,
                initialRating = item.rating,
                image = item.imageUrl,
                onClick = {
                    // Implementar acción al hacer clic
                }
            )
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun RecipesContentPreview() {
    FoodieFrontendTheme {
        AppScreens.RecipesScreen(navController = rememberNavController())
    }
}*/
