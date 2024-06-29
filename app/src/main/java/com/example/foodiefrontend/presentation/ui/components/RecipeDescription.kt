package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.example.foodiefrontend.R

@Composable
fun RecipeDescription(
    name: String,
    punctuation: Boolean = false,
    initialRating: Int? = 0,
    modifier: Modifier,
    scored: Boolean = true,
    onClickScore: () -> Unit = {}
) {

    var isSelected by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.8f),
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 15.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Subtitle(
                        title = name,
                    )
                }
                if (punctuation && initialRating != null) {
                    StarRating(
                        initialRating = initialRating,
                        onRatingChanged = { }
                    )
                } else if (!scored){
                    Divider(
                        color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable(onClick = { onClickScore() })
                            .weight(0.5f)
                    ) {
                        ImageWithResource(
                            resourceId = R.drawable.ic_star_outlined,
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary),
                            modifier = Modifier.height(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Puntuar",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                }
            }

            if (punctuation) {
                SelectableHeartIcon(
                    selected = isSelected,
                    onSelectedChange = { isSelected = it }
                )
            }
        }
    }
}