package com.example.foodiefrontend.presentation.ui.screens.stock.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.viewmodel.StockViewModel

@Composable
fun AlertIngredientManual(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
    productType: Ingredient,
    stockViewModel: StockViewModel = viewModel()
) {
    var quantity by remember { mutableStateOf(productType.quantity.toIntOrNull() ?: 0) }
    var unit by remember { mutableStateOf(productType.unit.toIntOrNull() ?: 0) }
    var alertaEscasez by remember { mutableStateOf(productType.alertaEscasez) }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Text(
                text = "Agregar producto",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Log.d("Product", "Detected Product: $productType")
                Text(
                    text = productType.description,
                    textAlign = TextAlign.Center
                )
                productType.imageUrl?.let {
                    Surface(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(100.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(100.dp)
                                )
                                .padding(20.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp)
                            )
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 40.dp)
                ) {
                    IngredientQuantity(
                        quantity = quantity.toString(),
                        unit = null,
                        onDecrement = { if (quantity > 0) quantity-- },
                        onIncrement = { quantity++ }
                    )
                    Text(
                        text = productType.unitMesure,
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 40.dp)
                ) {
                    IngredientQuantity(
                        quantity = unit.toString(),
                        unit = null,
                        onDecrement = { if (unit > 0) unit-- },
                        onIncrement = { unit++ }
                    )
                    Text(
                        text = "u.",
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recibir alerta de escasez",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            alertaEscasez = if (alertaEscasez > 0) 0 else 1
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    ImageWithResource(
                        resourceId = if (alertaEscasez > 0)
                            R.drawable.ic_bell_on
                        else
                            R.drawable.ic_bell_off,
                        modifier = Modifier.size(20.dp),
                        onClick = { alertaEscasez = if (alertaEscasez > 0) 0 else 1 }
                    )
                }
            }
        },
        dismissButton = {
            CustomButton(
                onClick = { setShowDialog(false) },
                containerColor = MaterialTheme.colorScheme.primary,
                text = "Cancelar",
                iconHeight = 30.dp,
            )
        },
        confirmButton = {
            CustomButton(
                onClick = {
                    stockViewModel.addProductByName(
                        context = context,
                        nombreProducto = productType.description,
                        cantidad = quantity,
                        unidad = unit,
                        alertaEscasez = alertaEscasez
                    )
                    setShowDialog(false)
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                text = stringResource(R.string.add_product),
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewAlertIngredientManual() {
    var showDialog by remember { mutableStateOf(true) }

    FoodieFrontendTheme {
        AlertIngredientManual(
            navController = rememberNavController(),
            productType = SampleData.sampleIngredient,
            setShowDialog = { showDialog = it }
        )
    }
}
