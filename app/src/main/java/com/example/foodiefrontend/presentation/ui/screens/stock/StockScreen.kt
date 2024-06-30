package com.example.foodiefrontend.presentation.ui.screens.stock

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.stock.components.AlertIngredient
import com.example.foodiefrontend.presentation.ui.screens.stock.components.AlertIngredientScanned
import com.example.foodiefrontend.presentation.ui.screens.stock.components.AlertLookForIngredient
import com.example.foodiefrontend.viewmodel.StockViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    navController: NavController,
    codeEan: String? = null
) {
    val viewModel: StockViewModel = viewModel()
    val stockIngredients by viewModel.stockIngredients.observeAsState(emptyList())
    var ingredient by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(!codeEan.isNullOrEmpty()) }
    var showManualDialog by remember { mutableStateOf(false) }
    var ingredientSelected by remember { mutableStateOf(SampleData.sampleIngredient) }
    var lookForManually by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Log.d("Barcode", "Código recibido en stock: $codeEan")

    LaunchedEffect(Unit) {
        viewModel.getUserStock(context)
    }

    if (showManualDialog && ingredientSelected != null) {
        AlertIngredient(
            navController = navController,
            setShowDialog = { showManualDialog = it },
            tipoProducto = ingredientSelected,
            codeEan = ""
        )
    } else if (showDialog && codeEan != null) {
        AlertIngredientScanned(
            navController = navController,
            setShowDialog = { param ->
                showDialog = param
            },
            codeEan = codeEan
        )
    } else if (lookForManually) {
        AlertLookForIngredient(
            navController = navController,
            setShowDialog = { param ->
                lookForManually = param
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 30.dp)
            ) {
                Title(
                    title = stringResource(R.string.your_stock),
                    modifier = Modifier
                        .weight(1f)
                )
                ImageWithResource(
                    resourceId = R.drawable.ic_keyboard,
                    onClick = {
                        lookForManually = true
                    },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .padding(end = 30.dp)
                        .size(50.dp)
                )
                ImageWithResource(
                    resourceId = R.drawable.ic_scan,
                    onClick = {
                        navController.navigate("camera_screen")
                    },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }

            /*CustomTextField(
                value = ingredient,
                placeholder = stringResource(R.string.look_for_ingredient),
                onValueChange = { newValue ->
                    ingredient = newValue
                },
                trailingIcon = R.drawable.ic_search,
                modifier = Modifier,
                enabled = false
            )*/
        }
        Box() {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            ) {
                items(stockIngredients) { ingredient ->
                    IngredientCard(
                        ingredient = ingredient,
                        onIncrement = { /* Implement increment action */ },
                        onDecrement = { /* Implement decrement action */ },
                        modify = false
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {

    val stockIngredients = SampleData.sampleIngredients
    var ingredient by remember { mutableStateOf("") }
    var lookFor by remember { mutableStateOf(true) }
    var showManualDialog by remember { mutableStateOf(false) }
    var lookForManually by remember { mutableStateOf(false) }
    var ingredientSelected by remember { mutableStateOf(SampleData.sampleIngredient) }

    FoodieFrontendTheme {

        if (showManualDialog && ingredientSelected != null) {
            AlertIngredient(
                navController = rememberNavController(),
                setShowDialog = { showManualDialog = it },
                tipoProducto = ingredientSelected,
                codeEan = ""
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 30.dp)
                ) {
                    Title(
                        title = stringResource(R.string.your_stock),
                        modifier = Modifier
                            .weight(1f)
                    )
                    ImageWithResource(
                        resourceId = R.drawable.ic_keyboard,
                        onClick = {
                            lookForManually = true
                        },
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .size(50.dp)
                    )
                    ImageWithResource(
                        resourceId = R.drawable.ic_scan,
                        onClick = {
                            //navController.navigate("camera_screen")
                        },
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }

//                CustomTextField(
//                    value = ingredient,
//                    placeholder = stringResource(R.string.look_for_ingredient),
//                    onValueChange = { newValue ->
//                        ingredient = newValue
//                    },
//                    trailingIcon = R.drawable.ic_search,
//                    modifier = Modifier,
//                    enabled = false
//                )
            }
            Box() {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {
                    items(stockIngredients) { ingredient ->
                        IngredientCard(
                            ingredient = ingredient,
                            onIncrement = {},
                            onDecrement = {}
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewScaned() {
//    FoodieFrontendTheme {
//        StockScreen(navController = rememberNavController(), codeEan = "123498389")
//    }
//}
