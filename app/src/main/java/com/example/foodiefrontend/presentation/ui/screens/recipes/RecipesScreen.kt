@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodiefrontend.presentation.ui.screens.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.RecipeDescription
import com.example.foodiefrontend.presentation.ui.components.RoundedImage
import com.example.foodiefrontend.presentation.ui.components.Subtitle
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.home.HorizontalCardList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(navController: NavController) {
    var recipe by remember { mutableStateOf("") }

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
                    title = stringResource(id = R.string.recipes),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 30.dp)
                )
                ImageWithResource(resourceId = R.drawable.ic_filter)
            }

            CustomTextField(
                value = recipe,
                placeholder = stringResource(R.string.look_for_recipe),
                onValueChange = { newValue ->
                    recipe = newValue
                },
                trailingIcon = R.drawable.ic_search,
                modifier = Modifier
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            val categories = listOf(
                "Todas" to { },
                "Favoritas" to { },
                "Vegetarianas" to { },
                "Carnivoras" to { },
                "Sin TACC" to { }
            )
            val recipes = listOf(
                Recipe("Bombas de papa", 3, R.drawable.bombas_de_papa, true),
                Recipe("Canelones", 2, R.drawable.bombas_de_papa),
                Recipe("Vegetarianas", 0, R.drawable.bombas_de_papa, true),
                Recipe("Carnivoras", 4, R.drawable.bombas_de_papa),
                Recipe("Sin TACC", 3, R.drawable.bombas_de_papa)
            )


            HorizontalButtonCategories(items = categories)

            VerticalRecipes(items = recipes)
        }
    }
}

@Composable
fun RecipesCardItem(
    title: String,
    image: Int,
    liked: Boolean = false,
    initialRating: Int,
    modifier: Modifier = Modifier
) {
    Box {
        RoundedImage(
            image = image,
            modifier = Modifier
                .padding(end = 10.dp)
                .width(200.dp)
                .height(150.dp)
        )

        RecipeDescription(
            modifier = Modifier
                .padding(start = 100.dp, top = 10.dp)
                .width(270.dp)
                .height(120.dp),
            punctuation = true,
            initialRating = initialRating
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
    var selectedButtonIndex by remember { mutableStateOf(0) }
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEachIndexed { index, item ->
            item {
                RecipesCardItem(
                    title = item.title,
                    initialRating = item.initialRating,
                    image = item.image,
                    liked = item.liked
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesContentPreview() {
    RecipesScreen(navController = rememberNavController())
}