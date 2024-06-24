package com.example.foodiefrontend.presentation.ui.screens.profile.components

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
fun AlertLogout(
    setShowDialog: (Boolean) -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ImageWithResource(
                    resourceId = R.drawable.cry,
                    modifier = Modifier
                        .width(100.dp)
                        .padding(vertical = 30.dp)
                )
                Text(
                    text = stringResource(R.string.want_to_close_shift),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        },
        text = {
            Text(
                text = stringResource(R.string.come_back),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
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
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.btn_cancel),
                    contentColor = MaterialTheme.colorScheme.onSurface
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
        AlertLogout(
            setShowDialog = { param ->
                showDialog = param
            }
        )
    }
}
