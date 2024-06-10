package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme

@Composable
fun Title(
    title: String,
    color: Color = MaterialTheme.colorScheme.tertiary,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    FoodieFrontendTheme {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(color = color, fontWeight = fontWeight),
            textAlign = textAlign,
            modifier = modifier
        )
    }
}