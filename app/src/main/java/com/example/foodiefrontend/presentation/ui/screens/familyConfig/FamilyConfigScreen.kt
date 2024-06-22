package com.example.foodiefrontend.presentation.ui.screens.familyConfig

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomToolbar
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.profile.components.ProfileOptionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyConfigScreen(
    navController: NavController,
    list: List<Persona>
) {
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
            list.forEach { persona ->
                ProfileOptionItem(
                    text = "${persona.nombre} ${persona.apellido}",
                    icon = Icons.Outlined.Edit,
                    onClick = { }
                )
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        FamilyConfigScreen(
            navController = rememberNavController(),
            list = SampleData.listOfPersona
        )
    }
}