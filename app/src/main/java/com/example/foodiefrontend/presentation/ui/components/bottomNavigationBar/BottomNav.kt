package com.example.foodiefrontend.presentation.ui.components.bottomNavigationBar

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.screens.recipes.RecipesScreen
import com.example.foodiefrontend.viewmodel.UserViewModel

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Log.d("BottomNavigationBar", "Current route: $currentRoute")

    val items = listOf(
        BottomNavItem(
            AppScreens.HomeScreen.route, painterResource(id = R.drawable.home),
            stringResource(R.string.home)),
        BottomNavItem(
            AppScreens.RecipesScreen.route, painterResource(id = R.drawable.recipes),
            stringResource(R.string.recipes)),
        BottomNavItem(
            AppScreens.StockScreen.route, painterResource(id = R.drawable.stock),
            stringResource(R.string.stock)),
        BottomNavItem(
            AppScreens.ProfileScreen.route, painterResource(id = R.drawable.profile),
            stringResource(R.string.profile))
    )

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        items.forEach { item ->
            val isSelected = currentRoute?.startsWith(item.route) == true
            Log.d("BottomNavigationBar", "Item route: ${item.route}, is selected: $isSelected")
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    Log.d("BottomNavigationBar", "Navigating to: ${item.route}")

                    if (item.route == AppScreens.HomeScreen.route) {
                        navController.navigate(AppScreens.HomeScreen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = null,
                        modifier = Modifier.height(20.dp)
                    )
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecipesContentPreview() {
    FoodieFrontendTheme {
        val navController = rememberNavController()
        BottomNavigationBar(navController = navController)
    }
}
