package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImageWithResource(
    resourceId: Int,
    contentDescription: String = "",
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val painter: Painter = painterResource(id = resourceId)
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

