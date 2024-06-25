package com.example.foodiefrontend

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.navigation.AppNavigation
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.bottomNavigationBar.BottomNavigationBar
import com.example.foodiefrontend.service.Config

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Config.load(this)

        setContent {
            FoodieFrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    Log.d("MainActivity", "Current route: $currentRoute")

                    Scaffold(
                        bottomBar = {
                            when {
                                currentRoute?.startsWith(AppScreens.HomeScreen.route) == true || currentRoute?.startsWith(
                                    AppScreens.RecipesScreen.route
                                ) == true || currentRoute?.startsWith(AppScreens.StockScreen.route) == true || currentRoute?.startsWith(
                                    AppScreens.ProfileScreen.route
                                ) == true || currentRoute?.startsWith(AppScreens.FamilyConfigScreen.route) == true -> {
                                    Log.d(
                                        "MainActivity",
                                        "Displaying BottomNavigationBar for route: $currentRoute"
                                    )
                                    BottomNavigationBar(navController = navController)
                                }
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            AppNavigation(navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodieFrontendTheme {
        AppNavigation(navController = rememberNavController())
    }
}