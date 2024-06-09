package com.example.foodiefrontend.presentation.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.Title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Foodie") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageWithResource(resourceId = R.drawable.logo, modifier = Modifier.height(80.dp))

                Title(title = "Ingresa tu usuario")

                Spacer(modifier = Modifier.height(32.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            navController.navigate(AppScreens.HomeScreen.route)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                            .background(color = MaterialTheme.colorScheme.secondary),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Text(text = "Ingresar")
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    LoginScreen(
        navController = rememberNavController()
    )
}