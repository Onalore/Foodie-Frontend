package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme

@Composable
fun SelectableHeartIcon(
    selected: Boolean = false,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(60.dp)
            .height(60.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onSelectedChange(!selected) }
    ) {
        ImageWithResource(
            resourceId = if (selected)
                R.drawable.ic_heart_filled
            else
                R.drawable.ic_heart_outline
        )
    }
}


@Preview
@Composable
private fun Preview() {
    var liked by remember { mutableStateOf(false) }

    FoodieFrontendTheme {
        SelectableHeartIcon(
            selected = liked,
            onSelectedChange = { liked = it }
        )
    }
}