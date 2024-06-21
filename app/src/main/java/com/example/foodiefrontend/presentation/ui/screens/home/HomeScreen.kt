@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodiefrontend.presentation.ui.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.RecipeDescription
import com.example.foodiefrontend.presentation.ui.components.RoundedImage
import com.example.foodiefrontend.presentation.ui.components.Subtitle
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.components.bottomNavigationBar.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    username: String
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Utilizar tu BottomNavigationBar
        },
        content = { paddingValues -> // Usar paddingValues para evitar superposiciones
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSurface)
                    .padding(paddingValues), // Ajustar el contenido para evitar superposiciones con la barra de navegación
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
                                navController.navigate(AppScreens.SuggestedRecipesScreen.route)
                            }
                        )

                        Spacer(modifier = Modifier.height(25.dp))

                        CustomButton(
                            text = stringResource(R.string.suggest_without_my_ingredients),
                            containerColor = MaterialTheme.colorScheme.secondary,
                            icon = R.drawable.dice,
                            modifier = Modifier.height(100.dp),
                            colorIcon = ColorFilter.tint(Color.White),
                            onClick = { }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 40.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Subtitle(
                            title = "Tus recetas favoritas",
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    }

                    //Está de ejemplo, se debe borrar una vez que se envíe la info verdadera
                    HorizontalCardList(items = SampleData.recipes)
                }
            }
        }
    )
}

@Composable
fun HorizontalCardList(items: List<Recipe>) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            HomeCardItem(title = item.name, image = item.imageUrl, liked = item.liked)
        }
    }
}

@Composable
fun HomeCardItem(
    title: String,
    image: String,
    liked: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box {
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

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    HomeScreen(
        navController = rememberNavController(),
        username = "Usuario"
    )
}