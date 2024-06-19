package com.example.foodiefrontend.presentation.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.foodiefrontend.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val loginResult by viewModel.loginResult.observeAsState()

    LaunchedEffect(loginResult) {
        if (loginResult != null) {
            navController.navigate(AppScreens.HomeScreen.route)
        } else if (loginResult == null && showError) {
            // Show error message
        }
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageWithResource(
                    resourceId = R.drawable.logo,
                    modifier = Modifier.width(220.dp),
                    contentScale = ContentScale.FillWidth
                )

                Text(
                    text = stringResource(R.string.glad_you_come_back),
                    style = MaterialTheme.typography.bodyMedium
                )

                CustomTextField(
                    value = emailValue,
                    placeholder = stringResource(R.string.enter_email),
                    onValueChange = { newValue ->
                        emailValue = newValue
                    }
                )

                CustomPasswordField(
                    value = passwordValue,
                    placeholder = stringResource(R.string.password),
                    onValueChange = { newValue ->
                        passwordValue = newValue
                    }
                )

                Text(
                    text = stringResource(R.string.recover_password),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                if (showError) {
                    Text(
                        text = "Por favor, ingrese un correo electrónico y una contraseña válidos",
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }

                CustomButton(
                    onClick = {
                        showError = emailValue.isEmpty() || passwordValue.isEmpty()
                        if (!showError) {
                            viewModel.loginUser(emailValue, passwordValue)
                        }
                    },
                    text = stringResource(R.string.login),
                    containerColor = MaterialTheme.colorScheme.primary
                )

                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                CustomButton(
                    text = stringResource(R.string.enter_with_google),
                    icon = R.drawable.google_logo,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                    onClick = {}
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.dont_have_account)
                    )

                    Text(
                        text = stringResource(R.string.registry_now),
                        color = Color(0xFF60A0F1),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate(AppScreens.RegisterScreen.route)
                        }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        LoginScreen(
            navController = rememberNavController()
        )
    }
}
