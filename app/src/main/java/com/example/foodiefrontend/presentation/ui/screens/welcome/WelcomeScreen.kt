package com.example.foodiefrontend.presentation.ui.screens.welcome

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomToggleButton
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.Title

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(navController: NavController) {
    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 50.dp, horizontal = 20.dp)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageWithResource(
                    resourceId = R.drawable.logo,
                    modifier = Modifier
                        .height(80.dp)
                        .width(180.dp),
                    contentScale = ContentScale.FillBounds
                )

                ImageWithResource(
                    resourceId = R.drawable.ilustracion_login,
                    modifier = Modifier.size(200.dp)
                )

                Title(
                    title = stringResource(R.string.welcome_title),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(R.string.welcome_text),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CustomToggleButton(
                        firstButton = stringResource(R.string.registry),
                        secondButton = stringResource(R.string.login),
                        onClickFirst = { },
                        onClickSecond = {
                            navController.navigate(AppScreens.LoginScreen.route)
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
        WelcomeScreen(
            navController = rememberNavController()
        )
    }
}