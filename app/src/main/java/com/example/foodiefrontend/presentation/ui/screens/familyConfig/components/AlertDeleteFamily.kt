package com.example.foodiefrontend.presentation.ui.screens.familyConfig.components

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
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource

@Composable
fun AlertDeleteFamily(
    navController: NavController,
    name: String,
    lastName: String,
    setShowDialog: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Text(
                text = "¿Deseas eliminar a $name $lastName?",
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
                    text = "No podrás deshacer esta acción",
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
                    /*TODO*/
                },
                containerColor = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.yes_close_shift),
                modifier = Modifier
            )
        },
        confirmButton = {
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
                CustomButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    containerColor = Color(0xFFD03333),
                    text = stringResource(R.string.btn_cancel),
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
        AlertDeleteFamily(
            navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            },
            name = "Francesca",
            lastName = "Lucciano"
        )
    }
}
