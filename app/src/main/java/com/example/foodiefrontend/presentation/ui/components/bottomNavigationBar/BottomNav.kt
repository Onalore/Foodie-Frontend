package com.example.foodiefrontend.presentation.ui.components.bottomNavigationBar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R


@Composable
fun BottomNavigationBar(
    navController: NavController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("home_screen", painterResource(id = R.drawable.home),
            stringResource(R.string.home)),
        BottomNavItem("recipes_screen", painterResource(id = R.drawable.recipes),
            stringResource(R.string.recipes)),
        BottomNavItem("stock_screen", painterResource(id = R.drawable.stock),
            stringResource(R.string.stock)),
        BottomNavItem("profile_screen", painterResource(id = R.drawable.profile),
            stringResource(R.string.profile))
    )

    BottomNavigation (
        backgroundColor = Color(0xFFFFFFFF)
    ) {
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
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .height(25.dp)
                            .padding(bottom = 5.dp)
                            .align(Alignment.CenterVertically)
                    )
                },
                label = {
                    Text(
                        item.label,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                },
                modifier = Modifier.height(80.dp)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BottomNavigationBar(
        navController = rememberNavController()
    )
}