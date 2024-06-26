package com.example.foodiefrontend.presentation.ui.screens.home.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.StarRating
import com.example.foodiefrontend.viewmodel.UserViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AlertScore(
    navController: NavController,
    recipe: Recipe,
    setShowDialog: (Boolean) -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    var puntuacion by remember { mutableStateOf(0) }
    var isFavorite by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val decodedRecipeName = URLDecoder.decode(recipe.name, StandardCharsets.UTF_8.toString())

    Log.d("AlertScore", "Displaying AlertScore for recipe: $recipe")

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = decodedRecipeName,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "¿Qué te pareció?",
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StarRating(
                    initialRating = puntuacion,
                    onRatingChanged = { rating ->
                        puntuacion = rating
                    },
                    widthStar = 40.dp,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Añadir a favoritos",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            isFavorite = !isFavorite
                            Log.d("AlertScore", "Favorite toggled: $isFavorite")
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    ImageWithResource(
                        resourceId = if (isFavorite)
                            R.drawable.ic_heart_filled
                        else
                            R.drawable.ic_heart_outline,
                        modifier = Modifier.size(20.dp),
                        onClick = {
                            isFavorite = !isFavorite
                            Log.d("AlertScore", "Favorite toggled: $isFavorite")
                        }
                    )
                }
            }
        },
        dismissButton = {
            CustomButton(
                onClick = {
                    userViewModel.rateRecipe(context, puntuacion, isFavorite) {
                        navController.popBackStack()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.send_score),
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        },
        confirmButton = {
            CustomButton(
                onClick = {
                    Log.d("AlertScore", "Recipe doesn't happen button clicked")
                    userViewModel.deleteTemporaryRecipe(context)
                    navController.navigate(AppScreens.HomeScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.recipe_doesnt_happen),
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}
