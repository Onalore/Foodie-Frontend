package com.example.foodiefrontend.presentation.ui.screens.profile.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.screens.register.components.CustomComboBox
import com.example.foodiefrontend.utils.Constants
import com.example.foodiefrontend.viewmodel.SuggestedRecipesViewModel

@Composable
fun AlertAskDiners(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
    withStock: Boolean,
    grupoFamiliar: List<Persona>,
    suggestedRecipesViewModel: SuggestedRecipesViewModel = viewModel()
) {
    var comensales by remember { mutableStateOf(emptyList<Persona>()) }
    var comida by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageWithResource(
                    resourceId = R.drawable.family,
                    modifier = Modifier
                        .width(100.dp)
                        .padding(vertical = 30.dp)
                )
                Text(
                    text = stringResource(R.string.who_eat_today),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.alert_diners_text),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                CustomComboBox(
                    selectedPerson = comensales,
                    onSelectedPersonChange = { comensales = it },
                    label = stringResource(R.string.select_diners),
                    items = grupoFamiliar
                )
                CustomComboBox(
                    selectedItem = comida.toString(),
                    onSelectedItemChange = { comida = it },
                    label = stringResource(R.string.select_meal),
                    items = Constants.comidas
                )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        dismissButton = {
            CustomButton(
                onClick = {
                    Log.d("AlertAskDiners", "Confirm button clicked")
                    if (comida.isEmpty()) {
                        errorMessage = "Por favor, selecciona una comida."
                    } else {
                        setShowDialog(false)
                        errorMessage = ""
                        if (withStock) {
                            suggestedRecipesViewModel.fetchRecipes(context, comensales, comida)
                            navController.navigate(
                                AppScreens.SuggestedRecipesScreen.createRoute(
                                    comensales,
                                    comida
                                )
                            )
                        } else {
                            suggestedRecipesViewModel.fetchRandomRecipes(
                                context,
                                comensales,
                                comida
                            )
                            navController.navigate(
                                AppScreens.RandomRecipesScreen.createRoute(
                                    comensales,
                                    comida
                                )
                            )
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                text = "Generar receta",
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        },
        confirmButton = {
            CustomButton(
                onClick = {
                    setShowDialog(false)
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.btn_cancel),
                modifier = Modifier
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var showDialog by remember { mutableStateOf(true) }

    FoodieFrontendTheme {
        AlertAskDiners(
            navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            },
            withStock = true,
            grupoFamiliar = listOf( // Sample data for preview
                Persona("John", "Doe", 35),
                Persona("Jane", "Doe", 33)
            )
        )
    }
}
