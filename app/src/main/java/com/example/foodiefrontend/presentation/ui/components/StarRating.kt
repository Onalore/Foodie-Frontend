package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.foodiefrontend.R

@Composable
fun StarRating(
    initialRating: Int = 0,
    onRatingChanged: (Int) -> Unit
) {
    val selectedStars = remember { mutableStateOf(initialRating) }

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
                }
            )
        }
    }
}

@Composable
private fun Star(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val starIcon = if (isSelected) R.drawable.ic_star_filled else R.drawable.ic_star_outlined

    ImageWithResource(
        resourceId = starIcon,
        modifier = Modifier
            .clickable(onClick = onClick),
        colorFilter = ColorFilter.tint(Color(0xFFE8BB66))
    )
}