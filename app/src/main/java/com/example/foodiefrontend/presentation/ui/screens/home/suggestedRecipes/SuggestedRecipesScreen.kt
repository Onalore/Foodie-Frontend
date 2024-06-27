// Import necessary packages
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
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
fun SuggestedRecipesScreen(
    navController: NavController,
    comensales: List<Persona>,
    comida: String
) {
    val context = LocalContext.current
    val viewModel: SuggestedRecipesViewModel = viewModel()
    val recipes by viewModel.recipes.observeAsState(initial = emptyList())
    val error by viewModel.error.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = true)

    // Mostrar animación de carga hasta que las recetas se carguen
    LaunchedEffect(Unit) {
        if (recipes.isEmpty()) {
            viewModel.fetchRecipes(context, comensales, comida)
        }
    }

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
            if (isLoading) {
                Title(
                    title = "Cargando recetas personalizadas para vos",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 40.dp)
                )

            } else {
                Title(title = stringResource(R.string.suggests_for_you), textAlign = TextAlign.Start)
            }

            if (isLoading) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CookingAnimation()
                }
            } else {
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
                            navController.navigate(
                                AppScreens.RecipeScreen.createRoute(
                                    encodedRecipeJson
                                )
                            )
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
                            viewModel.fetchRecipes(
                                context,
                                comensales,
                                comida
                            ) // Se obtienen nuevas recetas al hacer clic
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        text = stringResource(R.string.more_options),
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun CookingAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cooking_pad))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.size(200.dp)
        )
}

@Preview(showBackground = true)
@Composable
fun PreviewSuggestedRecipes() {
    val navController = rememberNavController()
    val comensales = listOf<Persona>()
    val comida = "Desayuno"

    FoodieFrontendTheme {
        SuggestedRecipesScreen(
            navController = navController,
            comensales = comensales,
            comida = comida
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimation() {

    FoodieFrontendTheme {
        CookingAnimation()
    }
}
