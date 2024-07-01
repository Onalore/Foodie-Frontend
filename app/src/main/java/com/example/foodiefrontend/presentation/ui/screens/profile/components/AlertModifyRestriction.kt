package com.example.foodiefrontend.presentation.ui.screens.profile.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.screens.register.components.CustomComboBox
import com.example.foodiefrontend.utils.Constants
import com.example.foodiefrontend.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun AlertModifyRestriction(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
    userViewModel: UserViewModel = viewModel()
) {
    var restricciones by remember { mutableStateOf(emptyList<String>()) }
    var isUpdating by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val updateResult by userViewModel.updateResult.observeAsState()

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
                    Log.d(
                        "AlertModifyRestriction",
                        restricciones.toString()
                    ) // Verifica el contenido antes de enviar
                    isUpdating = true
                    scope.launch {
                        userViewModel.actualizarRestriccionesUsuario(context, restricciones)
                        navController.navigate("profile_screen")
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                text = "Confirmar"
            )
        }
    )

    LaunchedEffect(updateResult) {
        if (isUpdating) {
            updateResult?.let {
                if (it) {
                    Log.d("AlertModifyRestriction", "Restricciones actualizadas con éxito")
                } else {
                    Log.e("AlertModifyRestriction", "Error al actualizar restricciones")
                }
                setShowDialog(false)
                isUpdating = false
            }
        }
    }
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
