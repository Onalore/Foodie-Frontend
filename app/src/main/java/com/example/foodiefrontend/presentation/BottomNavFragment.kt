package com.example.foodiefrontend.presentation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

class BottomNavFragment : Fragment() {

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // List of navigation items (you can replace this with your data source)
        val items = listOf(
            BottomNavItem("home", Icons.Default.Home, "Home"),
            BottomNavItem("search", Icons.Default.Search, "Search"),
            BottomNavItem("profile", Icons.Default.Person, "Profile")
        )

        BottomNavigation {
            items.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            // Pop up to start destination and avoid stacking screens
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(item.icon, contentDescription = null) },
                    label = { Text(item.label) }
                )
            }
        }
    }


}