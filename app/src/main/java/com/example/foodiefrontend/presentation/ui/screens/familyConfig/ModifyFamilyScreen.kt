package com.example.foodiefrontend.presentation.ui.screens.familyConfig

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.foodiefrontend.presentation.ui.screens.familyConfig.components.AlertDeleteFamily
import com.example.foodiefrontend.presentation.ui.screens.register.components.CustomComboBox
import com.example.foodiefrontend.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyFamilyScreen(
    navController: NavController,
    initialNombre: String,
    initialApellido: String,
    initialEdad: Int,
    initialRestricciones: List<String>,
    deleteFamily: Boolean = false
) {
    var nombre by remember { mutableStateOf(initialNombre) }
    var apellido by remember { mutableStateOf(initialApellido) }
    var edad by remember { mutableStateOf(initialEdad.toString()) }
    var restricciones by remember { mutableStateOf(initialRestricciones) }
    var showError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }

    if (deleteFamily) {
        AlertDeleteFamily(navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            },
            name = nombre,
            lastName = apellido
        )
    }

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
                    text = stringResource(R.string.modify_family),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
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
                    showError = nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty()
                    if (!showError) {
                        // Lógica para guardar los cambios
                        /*TODO*/
                        navController.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.modify_family),
                containerColor = MaterialTheme.colorScheme.primary
            )
            CustomButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.delete_family),
                containerColor = Color(0xFFD03333)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewModifyFamilyScreen() {
    FoodieFrontendTheme {
        ModifyFamilyScreen(
            navController = rememberNavController(),
            initialNombre = "Juan",
            initialApellido = "Pérez",
            initialEdad = 30,
            initialRestricciones = listOf("Celiaquía", "Veganismo")
        )
    }
}
@Preview
@Composable
private fun PreviewDeleteFamilyScreen() {
    FoodieFrontendTheme {
        ModifyFamilyScreen(
            navController = rememberNavController(),
            initialNombre = "Juan",
            initialApellido = "Pérez",
            initialEdad = 30,
            initialRestricciones = listOf("Celiaquía", "Veganismo"),
            deleteFamily = true
        )
    }
}
