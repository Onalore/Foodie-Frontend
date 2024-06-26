package com.example.foodiefrontend.presentation.ui.screens.recipes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.navigation.AppScreens
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton

@Composable
fun AlertCancelCreateRecipe(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Text(
                text = stringResource(R.string.want_to_cancel_create_recipe),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp),
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.if_u_go_back),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                )
            }
        },
        dismissButton = {
            CustomButton(
                onClick = {
                    setShowDialog(false)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.keep_editing),
                modifier = Modifier
            )
        },
        confirmButton = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)) {
                CustomButton(
                    onClick = {
//                        navController.navigate(AppScreens.RecipesScreen.route)
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.cancel_creation),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    var showDialog by remember { mutableStateOf(true) }


    FoodieFrontendTheme {
        AlertCancelCreateRecipe(
            navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            }
        )
    }
}
