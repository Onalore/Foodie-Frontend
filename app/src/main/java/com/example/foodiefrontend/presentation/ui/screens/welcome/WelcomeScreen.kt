package com.example.foodiefrontend.presentation.ui.screens.welcome

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.foodiefrontend.R.drawable
import com.example.foodiefrontend.R.string
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
                    resourceId = drawable.logo,
                    modifier = Modifier
                        .height(80.dp)
                        .width(180.dp),
                    contentScale = ContentScale.FillBounds
                )

                ImageWithResource(
                    resourceId = drawable.ilustracion_login,
                    modifier = Modifier.size(100.dp)
                )

                Title(
                    title = stringResource(string.welcome_title),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(string.welcome_text),
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
                        firstButton = stringResource(string.registry),
                        secondButton = stringResource(string.login),
                        onClickFirst = { navController.navigate(AppScreens.RegisterScreen.route)},
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