package com.example.foodiefrontend.presentation.ui.screens.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomPasswordField
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.screens.register.components.CustomComboBox
import com.example.foodiefrontend.utils.Constants
import com.example.foodiefrontend.viewmodel.UserViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    var mail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordBis by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var restricciones by remember { mutableStateOf(emptyList<String>()) }
    var showError by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }

    Scaffold(
        content = {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ImageWithResource(
                            resourceId = R.drawable.logo,
                            modifier = Modifier
                                .height(80.dp)
                                .width(180.dp),
                            contentScale = ContentScale.FillBounds
                        )

                        Text(
                            text = "¡Ingresá tus datos para convertirte en Foodie!",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                        CustomTextField(
                            value = mail,
                            placeholder = stringResource(id = R.string.enter_email),
                            onValueChange = { mail = it }
                        )
                        CustomPasswordField(
                            value = password,
                            placeholder = stringResource(R.string.password),
                            onValueChange = { newValue ->
                                password = newValue
                            }
                        )
                        CustomPasswordField(
                            value = passwordBis,
                            placeholder = stringResource(R.string.re_enter_password),
                            onValueChange = { newValue ->
                                passwordBis = newValue
                            }
                        )

                        Text(
                            text = "Datos personales",
                            style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Start),
                            modifier = Modifier.fillMaxWidth()
                        )
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
                                color = androidx.compose.ui.graphics.Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else if (showPasswordError) {
                            Text(
                                text = stringResource(R.string.passwords_dont_match),
                                color = androidx.compose.ui.graphics.Color.Red,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        CustomButton(
                            onClick = {
                                showError =
                                    mail.isEmpty() || password.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || edad.isEmpty()
                                showPasswordError = password != passwordBis
                                if (!showError || !showPasswordError) {
                                    viewModel.registerUser(
                                        mail,
                                        password,
                                        nombre,
                                        apellido,
                                        edad.toInt(),
                                        restricciones
                                    ) { response ->
                                        if (response != null) {
                                            navController.navigate(AppScreens.LoginScreen.route)
                                        } else {
                                            showError = true
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.registry),
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }

        }
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    FoodieFrontendTheme {
        RegisterScreen(navController = rememberNavController())
    }
}
