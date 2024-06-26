package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    text: String = "",
    containerColor: Color,
    icon: Int? = null,
    iconHeight: Dp = 50.dp,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier,
    colorIcon: ColorFilter? = null,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(60.dp)

    Surface(
        elevation = 4.dp,
        shape = shape,
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = {
                onClick()
            },
            modifier = Modifier
                .then(modifier)
                .fillMaxWidth()
                .height(60.dp)
                .border(
                    width = 2.dp,
                    color = if (containerColor == Color.Transparent) contentColor else containerColor,
                    shape = RoundedCornerShape(60.dp)
                ),
            colors = ButtonDefaults.buttonColors(
                contentColor = contentColor,
                containerColor = containerColor,
            ),
        ) {
            if (icon != null) {
                Spacer(modifier = Modifier.width(15.dp))
                ImageWithResource(
                    resourceId = icon,
                    modifier = Modifier
                        .height(iconHeight)
                        .padding(end = 20.dp),
                    colorFilter = colorIcon
                )
            }
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}