package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme

@Composable
fun Subtitle(
    title: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            color = color,
            fontWeight = fontWeight
        ),
        textAlign = TextAlign.Start,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.then(modifier)
    )
}