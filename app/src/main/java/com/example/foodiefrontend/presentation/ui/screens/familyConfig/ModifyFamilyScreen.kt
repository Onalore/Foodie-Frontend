package com.example.foodiefrontend.presentation.ui.screens.familyConfig

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.CustomToolbar
import com.example.foodiefrontend.presentation.ui.screens.familyConfig.components.AlertDeleteFamily
import com.example.foodiefrontend.presentation.ui.screens.register.components.CustomComboBox
import com.example.foodiefrontend.utils.Constants
import com.example.foodiefrontend.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModifyFamilyScreen(
    navController: NavController,
    initialNombre: String,
    initialApellido: String,
    initialEdad: Int,
    initialRestricciones: List<String>,
    deleteFamily: Boolean = false,
    userViewModel: UserViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf(initialNombre) }
    var apellido by remember { mutableStateOf(initialApellido) }
    var edad by remember { mutableStateOf(initialEdad.toString()) }
    var restricciones by remember { mutableStateOf(initialRestricciones) }
    var showError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(true) }

    val context = LocalContext.current

    Log.d(
        "ModifyFamilyScreen",
        "Received parameters: nombre=$nombre, apellido=$apellido, edad=$edad, restricciones=$restricciones"
    )

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
                onValueChange = { /* Do nothing */ },
                enabled = false // Make the field non-editable
            )
            CustomTextField(
                value = apellido,
                placeholder = stringResource(R.string.lastName),
                onValueChange = { /* Do nothing */ },
                enabled = false // Make the field non-editable
            )
            CustomTextField(
                value = edad,
                placeholder = stringResource(R.string.age),
                onValueChange = { edad = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = true
            )
            CustomComboBox(
                selectedItems = restricciones,
                label = "Restricciones alimentarias",
                items = Constants.restricciones,
                onSelectedItemsChange = {
                    restricciones = it.filter { it.isNotBlank() }
                },
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
                        val cleanedRestricciones = restricciones.filter { it.isNotBlank() }
                        val persona = Persona(
                            nombre = nombre,
                            apellido = apellido,
                            edad = edad.toInt(),
                            restricciones = cleanedRestricciones
                        )
                        Log.d("ModifyFamilyScreen", "Updating persona: $persona")
                        userViewModel.updateFamilyMember(context, persona) { success ->
                            if (success) {
                                Log.d("ModifyFamilyScreen", "Update successful")
                                navController.navigateUp()
                            } else {
                                Log.e("ModifyFamilyScreen", "Update failed")
                                showError = true
                            }
                        }
                    } else {
                        Log.e(
                            "ModifyFamilyScreen",
                            "Validation failed: nombre, apellido, or edad is empty"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.modify_family),
                containerColor = MaterialTheme.colorScheme.primary
            )
            CustomButton(
                onClick = {
                    showError = nombre.isEmpty() || apellido.isEmpty()
                    if (!showError) {
                        val persona = Persona(
                            nombre = nombre,
                            apellido = apellido,
                            edad = edad.toIntOrNull() ?: 0,
                            restricciones = restricciones
                        )
                        Log.d("ModifyFamilyScreen", "Deleting persona: $persona")
                        userViewModel.deleteFamilyMember(context, persona) { success ->
                            if (success) {
                                Log.d("ModifyFamilyScreen", "Delete successful")
                                navController.navigateUp()
                            } else {
                                Log.e("ModifyFamilyScreen", "Delete failed")
                                showError = true
                            }
                        }
                    } else {
                        Log.e(
                            "ModifyFamilyScreen",
                            "Validation failed: nombre or apellido is empty"
                        )
                    }
                },
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
