package com.example.foodiefrontend.presentation.ui.screens.home

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.RecipeDescription
import com.example.foodiefrontend.presentation.ui.components.RoundedImage
import com.example.foodiefrontend.presentation.ui.components.Subtitle
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.profile.components.AlertAskDiners
import com.example.foodiefrontend.presentation.ui.screens.recipes.RecipesCardItem
import com.example.foodiefrontend.viewmodel.UserViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    username: String,
    userViewModel: UserViewModel = viewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }
    var withStock by remember { mutableStateOf(true) }
    val familyMembers by userViewModel.familyMembers.observeAsState(emptyList())
    val favoriteRecipes by userViewModel.favoriteRecipes.observeAsState(emptyList())
    val context = LocalContext.current
    val temporaryRecipe by userViewModel.temporaryRecipe.observeAsState(null) // Observe temporaryRecipe

    LaunchedEffect(Unit) {
        userViewModel.getFamilyMembers(context)
        userViewModel.fetchFavoriteRecipes(context)
        userViewModel.fetchTemporaryRecipe(context)
    }

    if (showDialog) {
        AlertAskDiners(
            navController = navController,
            setShowDialog = { param ->
                showDialog = param
            },
            withStock = withStock,
            grupoFamiliar = familyMembers // Pass it here
        )
    }
    Scaffold(
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSurface)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.onTertiary)
                            .padding(horizontal = 15.dp, vertical = 40.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Title(title = "${stringResource(R.string.hi)} $username")

                        Title(
                            title = stringResource(R.string.what_are_u_eating),
                            fontWeight = FontWeight.Normal
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        CustomButton(
                            text = stringResource(R.string.suggest_with_my_ingredients),
                            containerColor = MaterialTheme.colorScheme.primary,
                            icon = R.drawable.stock,
                            modifier = Modifier.height(100.dp),
                            colorIcon = ColorFilter.tint(Color.White),
                            onClick = {
                                showDialog = true
                            }
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        CustomButton(
                            text = stringResource(R.string.suggest_without_my_ingredients),
                            containerColor = MaterialTheme.colorScheme.secondary,
                            icon = R.drawable.dice,
                            modifier = Modifier.height(100.dp),
                            colorIcon = ColorFilter.tint(Color.White),
                            onClick = {
                                showDialog = true
                            }
                        )
                    }

                    if (temporaryRecipe != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 30.dp, horizontal = 15.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Subtitle(
                                title = "Receta en proceso",
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            RecipesCardItem(
                                title = temporaryRecipe!!.name,
                                image = temporaryRecipe!!.imageUrl,
                                scored = false,
                                onClick = {
                                    val recipeJson = Gson().toJson(temporaryRecipe)
                                    val encodedRecipeJson =
                                        URLEncoder.encode(
                                            recipeJson,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                    Log.d(
                                        "Navigation",
                                        "Navigating to RecipeScreen with encoded recipe JSON: $encodedRecipeJson"
                                    )
                                    navController.navigate(
                                        AppScreens.RecipeScreen.createRoute(
                                            encodedRecipeJson
                                        )
                                    )
                                }
                            )
                            Text(
                                text = "Puntuar",
                                modifier = Modifier
                                    .clickable {
                                        Log.d("HomeScreen", "Puntuar text clicked")
                                        val recipeJson = Gson().toJson(temporaryRecipe)
                                        val encodedRecipeJson =
                                            URLEncoder.encode(
                                                recipeJson,
                                                StandardCharsets.UTF_8.toString()
                                            )
                                        Log.d(
                                            "HomeScreen",
                                            "Navigating to RateRecipeScreen with encoded recipe JSON: $encodedRecipeJson"
                                        )
                                        navController.navigate(
                                            AppScreens.RateRecipeScreen.createRoute(
                                                encodedRecipeJson
                                            )
                                        )
                                    }
                                    .padding(8.dp)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                color = Color.White,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                    Subtitle(
                        title = "Tus recetas favoritas",
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .fillMaxWidth()
                    )
                    favoriteRecipes?.let {
                        HorizontalCardList(
                            items = it,
                            navController = navController
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun HorizontalCardList(items: List<Recipe>, navController: NavController) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            HomeCardItem(title = item.name, image = item.imageUrl, liked = item.liked, onClick = {
                val recipeJson = Gson().toJson(item)
                val encodedRecipeJson =
                    URLEncoder.encode(recipeJson, StandardCharsets.UTF_8.toString())
                Log.d(
                    "Navigation",
                    "Navigating to RecipeScreen with encoded recipe JSON: $encodedRecipeJson"
                )
                navController.navigate(AppScreens.RecipeScreen.createRoute(encodedRecipeJson))
            })
        }
    }
}

@Composable
fun HomeCardItem(
    title: String,
    image: String,
    liked: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.clickable(onClick = onClick)) {
        RoundedImage(
            image = image,
            modifier = Modifier
                .padding(end = 10.dp)
                .width(270.dp)
                .height(200.dp)
        )

        RecipeDescription(
            name = title,
            modifier = Modifier
                .padding(start = 10.dp, top = 150.dp)
                .width(250.dp)
                .height(90.dp),
        )
    }
}
