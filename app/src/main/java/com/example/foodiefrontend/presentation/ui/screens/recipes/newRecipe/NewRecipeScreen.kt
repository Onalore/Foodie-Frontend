package com.example.foodiefrontend.presentation.ui.screens.recipes.newRecipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.CustomToolbar
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.recipes.components.AlertCancelCreateRecipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRecipeScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var ingredientes by remember { mutableStateOf("") }
    var preparacion by remember { mutableStateOf("") }

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
                onClick = { /*TODO*/ },
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

    FoodieFrontendTheme {
        NewRecipeScreen(
            navController = navController
        )
    }
}