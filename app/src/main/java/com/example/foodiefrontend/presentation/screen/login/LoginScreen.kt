package com.example.foodiefrontend.presentation.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.ui.component.ImageWithResource
import com.example.foodiefrontend.presentation.ui.component.Title

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
                // Title
                ImageWithResource(resourceId = R.drawable.logo, modifier = Modifier.height(80.dp))
                ImageWithResource(resourceId = R.drawable.ilustracion_login, modifier = Modifier.height(200.dp))
                Title(title = "Descubre un mundo de recetas")

                // Description
                Text(
                    text = "Explora infinitas recetas, basadas en tus preferencias y con los ingredientes que tienes",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                }
            }
        }
    )
}

//@Preview
//@Composable
//private fun Preview() {
//    LoginScreen()
//}