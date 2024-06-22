package com.example.foodiefrontend.presentation.ui.screens.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.profile.components.ProfileOptionItem
import com.example.foodiefrontend.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    context: Context
) {
    val userInfo by userViewModel.userInfo.observeAsState()

    LaunchedEffect(Unit) {
        userViewModel.getUserInfo(context)
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
        userInfo?.let { user ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.onTertiary)
                        .padding(16.dp)
                ) {
                    Image(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                    )
                }
                user.persona.nombre?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        color = Color(0xFF6200EE),
                        modifier = Modifier.padding(top = 28.dp, bottom = 32.dp)
                    )
                }
            }
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


/*@Preview(showBackground = true)
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        ProfileScreen<Any>(
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
        ProfileScreen<Any>(
            navController = rememberNavController(),
            username = "nombre de usuario",
            closeShift = true
        )
    }
}*/


