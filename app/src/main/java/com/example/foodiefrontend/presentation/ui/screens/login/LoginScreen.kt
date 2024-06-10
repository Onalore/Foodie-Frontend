package com.example.foodiefrontend.presentation.ui.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController) {
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    Scaffold(
        content = { paddingValues ->
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
                    onValueChange = {newValue ->
                        emailValue = newValue
                    }
                )

                CustomTextField(
                    value = passwordValue,
                    placeholder = stringResource(R.string.password),
                    onValueChange = {newValue ->
                        passwordValue = newValue
                    }
                )

                Text(
                    text = stringResource(R.string.recover_password),
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )

                CustomButton(
                    text = stringResource(R.string.login),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    navController.navigate(AppScreens.HomeScreen.route)
                }

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
                        fontWeight = FontWeight.Bold
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