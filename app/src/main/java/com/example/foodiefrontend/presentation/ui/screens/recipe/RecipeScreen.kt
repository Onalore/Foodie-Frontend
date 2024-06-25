package com.example.foodiefrontend.presentation.ui.screens.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.data.SampleData.recipe
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.RoundedImage
import com.example.foodiefrontend.presentation.ui.screens.recipe.components.AlertGoingToCook
import com.example.foodiefrontend.viewmodel.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun RecipeScreen(
    navController: NavController,
    recipe: Recipe,
    price: Int? = null
) {
    var showDialog by remember { mutableStateOf(false) }
    val userViewModel: UserViewModel = viewModel()

    LaunchedEffect(Unit) {
        delay(30000L) // 30 segundos de retraso
        showDialog = true
    }

    if(showDialog){
        AlertGoingToCook(
            navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            },
            userViewModel,
            recipe = recipe
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
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
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ImageWithResource(
                            resourceId = R.drawable.ic_clock,
                            modifier = Modifier.size(25.dp)
                        )
                        Text(
                            text = "${recipe.preparation.size} min",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                        )

                    }
                }
            }
        }

        item {
            if (price != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.estimated_cost),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "$ $price",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 28.sp
                            )
                        )
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                ) {
                    // Ingredients section
                    Text(
                        text = "Ingredientes",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    recipe.ingredients.forEach { ingredient ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(13.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ImageWithResource(
                                resourceId = R.drawable.ic_circle,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "${ingredient.description} ${ingredient.quantity} ${ingredient.unit}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp),
                ) {
                    Text(
                        text = "Preparación",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    recipe.preparation.forEachIndexed { index, step ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(50.dp)
                                    )
                                    .padding(
                                        horizontal = 6.dp
                                    )
                            ) {
                                Text(
                                    text = "${index + 1}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White  // Color del texto dentro del box
                                )
                            }
                            Text(
                                text = "$step",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        RecipeScreen(
            rememberNavController(),
            recipe = recipe,
            price = 490
        )
    }
}
