package com.example.foodiefrontend.presentation.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.screens.register.components.CustomComboBox
import com.example.foodiefrontend.utils.Constants

@Composable
fun AlertAskDiners(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    var comensales by remember { mutableStateOf(emptyList<String>()) }
    var comida by remember { mutableStateOf("") }


    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageWithResource(
                    resourceId = R.drawable.family,
                    modifier = Modifier
                        .width(100.dp)
                        .padding(vertical = 30.dp)
                )
                Text(
                    text = "¿Quienes comen hoy?",
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
                    text = "Se preparará una receta especialmente para la cantidad de comensales y sus restricciones alimentarias",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                CustomComboBox(
                    selectedItems = comensales,
                    onSelectedItemsChange = { comensales = it },
                    label = "Seleccionar comensales",
                    items = createNameList(SampleData.listOfPersona),
                    isMultiSelect = true
                )
                CustomComboBox(
                    selectedItem = comida.toString(),
                    onSelectedItemChange = { comida = it },
                    label = "Seleccionar comida",
                    items = Constants.comidas
                )
            }
        },
        dismissButton = {
            CustomButton(
                onClick = {
                    setShowDialog(false)
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                text = "Generar receta",
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        },
        confirmButton = {
            CustomButton(
                onClick = {
                    /*TODO*/
                },
                containerColor = MaterialTheme.colorScheme.primary,
                text = stringResource(R.string.btn_cancel),
                modifier = Modifier
            )
        }
    )
}

private fun createNameList(personas: List<Persona>): List<String> {
    return personas.map { "${it.nombre} ${it.apellido}" }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    var showDialog by remember { mutableStateOf(true) }

    FoodieFrontendTheme {
        AlertAskDiners(
            navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            }
        )
    }
}
