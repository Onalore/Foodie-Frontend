package com.example.foodiefrontend.presentation.ui.screens.stock.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.screens.home.suggestedRecipes.CookingAnimation
import com.example.foodiefrontend.viewmodel.StockViewModel

@Composable
fun AlertIngredientScanned(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
    codeEan: String,
) {
    val context = LocalContext.current
    val viewModel: StockViewModel = viewModel()

    LaunchedEffect(codeEan) {
        if (codeEan.isNotEmpty()) {
            viewModel.findProductByEan(codeEan)
        }
    }

    val productType by viewModel.productType.observeAsState()
    val error by viewModel.error.observeAsState()
    val confirmationResult by viewModel.confirmationResult.observeAsState()

    var shortageAlert by remember { mutableStateOf(false) }
    val quantityState = remember { mutableStateOf(productType?.quantity?.toInt() ?: 0) }
    var unit by remember { mutableStateOf(productType?.unit?.toInt() ?: 1) }
    var alertaEscasez by remember { mutableStateOf(productType?.alertaEscasez ?: 0) }

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Text(
                text = if (productType != null)
                    stringResource(R.string.is_this_product)
                else if (error != null)
                    "Hubo un error al escanear el producto"
                else
                    "Procesando producto",
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
                if (productType != null) {
                    Log.d("Product", "Detected Product: $productType")
                    Text(
                        text = productType?.description ?: "Producto desconocido",
                        textAlign = TextAlign.Center
                    )
                    productType?.imageUrl?.let {
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
                    productType?.quantity?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 40.dp)
                        ) {
                            IngredientEditable(
                                quantity = quantityState,
                                unit = null,
                                onDecrement = { if (quantityState.value > 0) quantityState.value-- },
                                onIncrement = { quantityState.value++ }
                            )
                            Text(
                                text = productType?.unitMesure?.let {
                                    if (it.length >= 2) it.substring(
                                        0,
                                        2
                                    ) else ""
                                } ?: "",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    productType?.unit?.let {
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
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recibir alerta de escasez",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.clickable {
                                shortageAlert = !shortageAlert
                            },
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        ImageWithResource(
                            resourceId = if (shortageAlert)
                                R.drawable.ic_bell_on
                            else
                                R.drawable.ic_bell_off,
                            modifier = Modifier.size(20.dp),
                            onClick = { shortageAlert = !shortageAlert }
                        )
                    }

                    productType?.alertaEscasez?.let {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 40.dp)
                        ) {
                            IngredientQuantity(
                                quantity = alertaEscasez.toString(),
                                unit = null,
                                onDecrement = {
                                    if (alertaEscasez > 0) alertaEscasez--
                                },
                                onIncrement = { alertaEscasez++ },
                                available = shortageAlert
                            )
                            Text(
                                text = productType?.unitMesure?.let {
                                    if (it.length >= 2) it.substring(
                                        0,
                                        2
                                    ) else ""
                                } ?: "",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else if (error != null) {
                    Text(
                        text = error ?: "Error desconocido",
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                } else {
                    CookingAnimation()
                }
            }
        },
        dismissButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    onClick = {
                        navController.navigate("camera_screen")
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    icon = R.drawable.ic_retry,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(15.dp))
                CustomButton(
                    onClick = {
                        setShowDialog(false)
                        if (productType != null) {
                            val stockConfirmationRequest = mapOf(
                                "ean" to codeEan,
                                "tipoProducto" to productType!!.description,
                                "cantidad" to quantityState.value,
                                "unidad" to unit,
                                "alerta" to alertaEscasez,
                                "unidadMedida" to productType!!.unitMesure
                            )
                            viewModel.confirmUser(
                                context,
                                codeEan,
                                productType!!.description,
                                quantityState.value,
                                unit.toString(),
                                alertaEscasez,
                                productType!!.unitMesure
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    icon = R.drawable.ic_check,
                    iconHeight = 30.dp,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        confirmButton = {
            Column(modifier = Modifier.fillMaxWidth()) {
                CustomButton(
                    onClick = {
                        setShowDialog(false)
                    },
                    containerColor = Color(0xFFE8BB66),
                    text = stringResource(R.string.enter_manually),
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )

    LaunchedEffect(confirmationResult) {
        confirmationResult?.let {
            if (it) {
                Log.d("ConfirmUser", "User confirmation successful.")
                navController.navigate("stock_screen")
            } else {
                Log.d("ConfirmUser", "Failed to confirm user.")
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
private fun Preview() {
    var showDialog by remember { mutableStateOf(true) }
    var shortageAlert by remember { mutableStateOf(false) }

    FoodieFrontendTheme {
        @Composable
        fun AlertIngredientScannedPreview(
            navController: NavController,
            setShowDialog: (Boolean) -> Unit,
            codeEan: String,
        ) {
            val productType = SampleData.sampleIngredient
            val error = "Producto no encontrado"
            val addProductResult = true

            var quantity by remember { mutableStateOf(productType?.quantity?.toInt() ?: 0) }
            var unit by remember { mutableStateOf(productType?.unit?.toInt() ?: 0) }
            var alertaEscasez by remember { mutableStateOf(productType?.alertaEscasez ?: 0) }

            AlertDialog(
                onDismissRequest = { setShowDialog(false) },
                title = {
                    Text(
                        text = stringResource(R.string.is_this_product),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
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
                        if (productType != null) {
                            Log.d("Product", "Detected Product: $productType")
                            Text(
                                text = productType?.description ?: "Producto desconocido",
                                textAlign = TextAlign.Center
                            )
                            productType?.imageUrl?.let {
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
                                            modifier = Modifier.size(80.dp)
                                        )
                                    }
                                }
                            }
                            productType?.quantity?.let {
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
                                        text = productType?.unitMesure?.let {
                                            if (it.length >= 2) it.substring(
                                                0,
                                                2
                                            ) else ""
                                        } ?: "",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                            productType?.unit?.let {
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
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Recibir alerta de escasez",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.clickable {
                                        shortageAlert = !shortageAlert
                                    },
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                                ImageWithResource(
                                    resourceId = if (shortageAlert)
                                        R.drawable.ic_bell_on
                                    else
                                        R.drawable.ic_bell_off,
                                    modifier = Modifier.size(20.dp),
                                    onClick = { shortageAlert = !shortageAlert }
                                )
                            }

                            productType?.alertaEscasez?.let {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier
                                        .width(200.dp)
                                        .padding(start = 40.dp)
                                ) {
                                    IngredientQuantity(
                                        quantity = alertaEscasez.toString(),
                                        unit = null,
                                        onDecrement = {
                                            if (alertaEscasez > 0) alertaEscasez--
                                        },
                                        onIncrement = { alertaEscasez++ },
                                        available = shortageAlert
                                    )
                                    Text(
                                        text = productType?.unitMesure?.let {
                                            if (it.length >= 2) it.substring(
                                                0,
                                                2
                                            ) else ""
                                        } ?: "",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        } else if (error != null) {
                            Text(
                                text = error ?: "Error desconocido",
                                textAlign = TextAlign.Center,
                                color = Color.Red
                            )
                        } else {
                            Text(
                                text = "Cargando...",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                },
                dismissButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomButton(
                            onClick = {
                                navController.navigate("camera_screen")
                            },
                            containerColor = MaterialTheme.colorScheme.primary,
                            icon = R.drawable.ic_retry,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        CustomButton(
                            onClick = {
                                setShowDialog(false)
                                if (productType != null) {
                                    val eanRequest = mapOf(
                                        "ean" to codeEan,
                                        "tipoProducto" to productType.description,
                                        "cantidad" to quantity,
                                        "unidad" to unit,
                                        "alerta" to shortageAlert,
                                        "unidadMedida" to productType.unitMesure
                                    )
                                    viewModel.confirmUser(eanRequest, context)
                                }
                            },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            icon = R.drawable.ic_check,
                            iconHeight = 30.dp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                },
                confirmButton = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CustomButton(
                            onClick = {
                                setShowDialog(false)
                            },
                            containerColor = Color(0xFFE8BB66),
                            text = stringResource(R.string.enter_manually),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )

            LaunchedEffect(addProductResult) {
                addProductResult?.let {
                    if (it) {
                        Log.d("AddProduct", "Product successfully added to stock.")
                        navController.navigate("stock_screen")
                    } else {
                        Log.d("AddProduct", "Failed to add product to stock.")
                    }
                }
            }
        }

        AlertIngredientScannedPreview(
            navController = rememberNavController(),
            setShowDialog = {
                showDialog = false
            },
            codeEan = "EAN398243"
        )
    }
}
*/
