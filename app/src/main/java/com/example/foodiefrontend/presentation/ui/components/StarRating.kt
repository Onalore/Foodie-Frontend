package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.screens.profile.components.AlertAskDiners

@Composable
fun StarRating(
    initialRating: Int = 0,
    onRatingChanged: (Int) -> Unit,
    widthStar: Dp = 20.dp
) {
    val selectedStars = remember { mutableIntStateOf(initialRating) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        repeat(5) { index ->
            val isSelected = index < selectedStars.value
            Star(
                isSelected = isSelected,
                onClick = {
                    selectedStars.value = index + 1
                    onRatingChanged(index + 1)
                },
                width = widthStar
            )
        }
    }
}

@Composable
private fun Star(
    isSelected: Boolean,
    onClick: () -> Unit,
    width: Dp
) {
    val starIcon = if (isSelected)
        R.drawable.ic_star_filled
    else
        R.drawable.ic_star_outlined

    ImageWithResource(
        resourceId = starIcon,
        onClick = { onClick() },
        colorFilter = ColorFilter.tint(Color(0xFFE8BB66))
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var initialRating by remember { mutableIntStateOf(0) }

    FoodieFrontendTheme {
        StarRating(
            initialRating = initialRating,
            onRatingChanged = { }
        )
    }
}
