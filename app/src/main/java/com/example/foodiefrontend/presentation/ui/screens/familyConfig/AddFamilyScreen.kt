package com.example.foodiefrontend.presentation.ui.screens.familyConfig

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.CustomToolbar
import com.example.foodiefrontend.presentation.ui.screens.register.components.CustomComboBox
import com.example.foodiefrontend.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFamilyScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var restricciones by remember { mutableStateOf(emptyList<String>()) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomToolbar(navController = navController, title = "")
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Añade un familiar",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Así podremos brindarte recetas más exactas",
                    style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )

            }
            CustomTextField(
                value = nombre,
                placeholder = stringResource(R.string.name),
                onValueChange = { nombre = it }
            )
            CustomTextField(
                value = apellido,
                placeholder = stringResource(R.string.lastName),
                onValueChange = { apellido = it }
            )
            CustomTextField(
                value = edad,
                placeholder = stringResource(R.string.age),
                onValueChange = { edad = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            CustomComboBox(
                selectedItems = restricciones,
                onSelectedItemsChange = { restricciones = it },
                label = "Restricciones alimentarias",
                items = Constants.restricciones,
                isMultiSelect = true
            )

            if (showError) {
                Text(
                    text = stringResource(R.string.complete_all_the_fields),
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                onClick = {
                    showError =
                        nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty()
//                    if (!showError) {
//                        viewModel.registerUser(
//                            nombre,
//                            apellido,
//                            edad.toInt(),
//                            restricciones
//                        ) { response ->
//                            if (response != null) {
//                                navController.navigate(AppScreens.LoginScreen.route)
//                            } else {
//                                showError = true
//                            }
//                        }
//                    }
                },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.add_family),
                containerColor = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Preview
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        AddFamilyScreen(
            navController = rememberNavController()
        )
    }
}
