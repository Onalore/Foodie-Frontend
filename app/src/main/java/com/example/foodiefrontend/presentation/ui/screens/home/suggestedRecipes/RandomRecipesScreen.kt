package com.example.foodiefrontend.presentation.ui.screens.home.suggestedRecipes

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomToolbar
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.recipes.RecipesCardItem
import com.example.foodiefrontend.viewmodel.SuggestedRecipesViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomRecipesScreen(
    navController: NavController,
    comensales: List<Persona>,
    comida: String
) {
    val context = LocalContext.current
    val viewModel: SuggestedRecipesViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.fetchRandomRecipes(context, comensales, comida)
    }

    val recipes by viewModel.recipes.observeAsState(initial = emptyList())
    val error by viewModel.error.observeAsState()

    Scaffold(
        topBar = {
            CustomToolbar(navController = navController, title = "")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(title = stringResource(R.string.suggests_for_you), textAlign = TextAlign.Start)

            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            recipes.forEach { item ->
                RecipesCardItem(
                    title = item.name,
                    image = item.imageUrl,
                    onClick = {
                        val recipeJson = Gson().toJson(item)
                        val encodedRecipeJson =
                            URLEncoder.encode(recipeJson, StandardCharsets.UTF_8.toString())
                        Log.d(
                            "Navigation",
                            "Navigating to RecipeScreen with encoded recipe JSON: $encodedRecipeJson"
                        )
                        navController.navigate(AppScreens.RecipeScreen.createRoute(encodedRecipeJson))
                    }
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                CustomButton(
                    onClick = {
                        //TODO
                        navController.navigate(AppScreens.RandomRecipesScreen.route)
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.more_options),
                )
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRandomRecipes() {
    val navController = rememberNavController()
    val comensales = listOf<Persona>()
    val comida = "Desayuno"
    FoodieFrontendTheme {
        RandomRecipesScreen(navController = navController, comensales = comensales, comida = comida)
    }
}
