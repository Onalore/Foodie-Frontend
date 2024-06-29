package com.example.foodiefrontend.presentation.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.data.FilterCriteria
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.StarRating
import com.example.foodiefrontend.presentation.ui.screens.register.components.CustomComboBox
import com.example.foodiefrontend.utils.Constants

@Composable
fun AlertModifyRestriction(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
) {
    var restricciones by remember { mutableStateOf(emptyList<String>()) }

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Modificar restricciones",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                CustomComboBox(
                    selectedItems = restricciones,
                    label = "Restricciones alimentarias",
                    items = Constants.restricciones,
                    onSelectedItemsChange = { restricciones = it },
                    isMultiSelect = true
                )
            }
        },
        confirmButton = {
            CustomButton(
                onClick = { setShowDialog(false) },
                containerColor = MaterialTheme.colorScheme.primary,
                text = "Cancelar",
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        },
        dismissButton = {
            CustomButton(
                onClick = {
                    setShowDialog(false)
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                text = "Confirmar"
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var showDialog by remember { mutableStateOf(true) }

    FoodieFrontendTheme {
        AlertModifyRestriction(
            navController = rememberNavController(),
            setShowDialog = { param ->
                showDialog = param
            },
        )
    }
}
