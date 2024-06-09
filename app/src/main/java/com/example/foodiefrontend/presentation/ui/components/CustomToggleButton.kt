package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.screens.welcome.WelcomeScreen

@Composable
fun CustomToggleButton(
    firstButton: String,
    secondButton: String,
    onClickFirst: () -> Unit,
    onClickSecond: () -> Unit
) {

    Card(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onClickFirst() },
                modifier = Modifier
                    .weight(1f)
                    .height(65.dp)
                    .padding(2.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary,
                    containerColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = firstButton)
            }
            Button(
                onClick = { onClickSecond() },
                modifier = Modifier
                    .weight(1f)
                    .height(65.dp)
                    .padding(2.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.tertiary,
                    containerColor = MaterialTheme.colorScheme.onTertiary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = secondButton)
            }
        }

    }
}


@Preview
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        CustomToggleButton(
            firstButton = "Registrarme",
            secondButton = "Loguearme",
            onClickFirst = {},
            onClickSecond = {}
        )
    }
}