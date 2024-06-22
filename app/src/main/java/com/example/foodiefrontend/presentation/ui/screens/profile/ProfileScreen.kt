package com.example.foodiefrontend.presentation.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.profile.components.AlertCloseShift
import com.example.foodiefrontend.presentation.ui.screens.profile.components.ProfileOptionItem

@Composable
fun ProfileScreen(
    navController: NavController,
    username: String = "",
    closeShift: Boolean = false
) {
    var showDialog by remember { mutableStateOf(true) }

    if (closeShift) {
        AlertCloseShift(navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Title(
            title = "Tu perfil",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Profile Picture and Name
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp) // Tamaño del fondo redondo
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.onTertiary) // Color del fondo
                    .padding(16.dp) // Espacio entre la imagen y el fondo
            ) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                )
            }
            Text(
                text = username,
                fontSize = 20.sp,
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(top = 28.dp, bottom = 32.dp)
            )
        }

        // Profile Options List
        ProfileOptionItem(
            text = stringResource(R.string.restrictions),
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            onClick = {}
        )

        ProfileOptionItem(
            text = stringResource(R.string.family_config),
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            onClick = {
                navController.navigate(AppScreens.FamilyConfigScreen.route)
            }
        )
        ProfileOptionItem(
            text = stringResource(R.string.logout),
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            onClick = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        ProfileScreen(
            navController = rememberNavController(),
            username = "nombre de usuario",
            closeShift = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCloseShift() {
    FoodieFrontendTheme {
        ProfileScreen(
            navController = rememberNavController(),
            username = "nombre de usuario",
            closeShift = true
        )
    }
}


