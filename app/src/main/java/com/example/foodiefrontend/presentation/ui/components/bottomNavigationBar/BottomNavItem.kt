package com.example.foodiefrontend.presentation.ui.components.bottomNavigationBar

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp

data class BottomNavItem(
    val route: String,
    val icon: Painter,
    val label: String
)
