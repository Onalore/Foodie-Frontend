package com.example.foodiefrontend.presentation.ui.screens.familyConfig

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomToolbar
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.profile.components.ProfileOptionItem
import com.example.foodiefrontend.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyConfigScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val context = LocalContext.current

    // Fetch family members when the screen is loaded
    LaunchedEffect(Unit) {
        userViewModel.getFamilyMembers(context)
    }

    val familyMembers = userViewModel.familyMembers.observeAsState(emptyList()).value

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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Title(
                    title = "Tus familiares",
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 30.dp)
                )
                ImageWithResource(
                    resourceId = R.drawable.ic_plus,
                    onClick = {
                        navController.navigate(AppScreens.AddFamilyScreen.route)
                    },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }
            familyMembers.forEach { persona ->
                ProfileOptionItem(
                    text = "${persona.nombre} ${persona.apellido}",
                    icon = Icons.Outlined.Edit,
                    onClick = {
                        val restricciones = persona.restricciones.joinToString(",")
                        val route = AppScreens.ModifyFamilyScreen.createRoute(
                            persona.nombre ?: "",  // Proporciona un valor por defecto si es nulo
                            persona.apellido ?: "",  // Proporciona un valor por defecto si es nulo
                            persona.edad ?: 0,  // Proporciona un valor por defecto si es nulo
                            restricciones
                        )
                        Log.d("FamilyConfigScreen", "Navigating to: $route")
                        navController.navigate(route)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFamilyConfigScreen() {
    FoodieFrontendTheme {
        FamilyConfigScreen(
            navController = rememberNavController()
        )
    }
}