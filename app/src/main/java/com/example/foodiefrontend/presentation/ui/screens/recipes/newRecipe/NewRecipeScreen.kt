package com.example.foodiefrontend.presentation.ui.screens.recipes.newRecipe

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.Recipe
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.CustomToolbar
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.recipes.components.AlertCancelCreateRecipe
import com.example.foodiefrontend.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecipeScreen(navController: NavController, userViewModel: UserViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var ingredientes by remember { mutableStateOf("") }
    var preparacion by remember { mutableStateOf("") }
    val context = LocalContext.current

    if (showDialog) {
        AlertCancelCreateRecipe(
            navController = navController,
            setShowDialog = { param ->
                showDialog = param
            }
        )
    }

    Scaffold(
        topBar = {
            CustomToolbar(
                navController = navController,
                title = "",
                onClick = { showDialog = true }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(
                title = stringResource(R.string.create_recipe),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                value = nombre,
                placeholder = stringResource(id = R.string.name),
                onValueChange = { newValue ->
                    nombre = newValue
                },
            )
            CustomTextField(
                value = ingredientes,
                placeholder = stringResource(id = R.string.ingredients),
                onValueChange = { newValue ->
                    ingredientes = newValue
                },
                singleLine = false,
                height = 120.dp
            )
            CustomTextField(
                value = preparacion,
                placeholder = stringResource(R.string.preparation),
                onValueChange = { newValue ->
                    preparacion = newValue
                },
                singleLine = false,
                height = 180.dp
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                onClick = {
                    if (nombre.isNotBlank() && ingredientes.isNotBlank() && preparacion.isNotBlank()) {
                        val ingredientesList = ingredientes.split("\n").mapNotNull {
                            val parts = it.split(",").map { part -> part.trim() }
                            if (parts.size == 3) {
                                Ingredient(
                                    description = parts[0],
                                    quantity = parts[1],
                                    unit = parts[2]
                                )
                            } else {
                                Log.e("NewRecipeScreen", "Ingredient format error: $it")
                                null
                            }
                        }

                        Log.d("NewRecipeScreen", "Ingredientes list: $ingredientesList")

                        val receta = Recipe(
                            name = nombre,
                            ingredients = ingredientesList,
                            preparation = preparacion.split("\n"),
                        )
                        Log.d("NewRecipeScreen", "Receta creada: $receta")
                        userViewModel.createRecipe(context, receta) { success ->
                            if (success) {
                                Toast.makeText(
                                    context,
                                    "Receta creada exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error al crear la receta",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Por favor completa todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.add_recipe)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    FoodieFrontendTheme {
        NewRecipeScreen(
            navController = navController,
            userViewModel = userViewModel
        )
    }
}
