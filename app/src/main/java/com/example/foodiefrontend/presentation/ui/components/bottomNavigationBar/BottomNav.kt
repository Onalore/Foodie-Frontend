package com.example.foodiefrontend.presentation.ui.components.bottomNavigationBar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens


@Composable
fun BottomNavigationBar(
    navController: NavController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
        backgroundColor = Color(0xFFFFFFFF)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}
