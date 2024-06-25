package com.example.foodiefrontend.presentation.ui.screens.recipe.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource

@Composable
fun AlertGoingToCook(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit
) {

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageWithResource(
                    resourceId = R.drawable.cooking_food_fried_svgrepo_com,
                    modifier = Modifier
                        .width(150.dp)
                        .padding(vertical = 20.dp)
                )
                Text(
                    text = "¿Vas a preparar la receta?",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Se descontarán los ingredientes de tu stock",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        dismissButton = {
            CustomButton(
                onClick = {
                    setShowDialog(false)
                    /*TODO*/
                },
                containerColor = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.lets_cook),
                modifier = Modifier
            )
        },
        confirmButton = {
            CustomButton(
                onClick = {
                    navController.navigateUp()
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.keep_looking),
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    var showDialog by remember { mutableStateOf(true) }

    FoodieFrontendTheme {
        AlertGoingToCook(
            navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            }
        )
    }
}
