package com.example.foodiefrontend.presentation.ui.screens.stock

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodiefrontend.R
import com.example.foodiefrontend.data.Ingredient
import com.example.foodiefrontend.data.SampleData
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.stock.components.AlertIngredientManual
import com.example.foodiefrontend.presentation.ui.screens.stock.components.AlertIngredientScanned
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
    var lookFor by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Log.d("Barcode", "Código recibido en stock: $codeEan")

    LaunchedEffect(Unit) {
        viewModel.getUserStock(context)
    }

    if(showManualDialog && ingredientSelected != null) {
        AlertIngredientManual(
            navController = navController,
            setShowDialog = {showManualDialog = it},
            productType = ingredientSelected
        )
    }
    else if (showDialog && codeEan != null) {
        AlertIngredientScanned(
            navController = navController,
            setShowDialog = { param ->
                showDialog = param
            },
            codeEan = codeEan
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
            Row {
                Title(
                    title = stringResource(R.string.your_stock),
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 30.dp)
                )
                ImageWithResource(
                    resourceId = R.drawable.ic_scan,
                    onClick = {
                        navController.navigate("camera_screen")
                    }
                )
            }

            CustomTextField(
                value = ingredient,
                placeholder = stringResource(R.string.look_for_ingredient),
                onValueChange = { newValue ->
                    ingredient = newValue
                },
                trailingIcon = R.drawable.ic_search,
                modifier = Modifier,
                enabled = false
            )
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
            if (lookFor) {

                Surface(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Card(
                        modifier = Modifier.padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    ) {
                        Column(
                        ) {
                            SampleData.sampleIngredients.forEach { ingredient ->
                                IngredientCard(
                                    ingredient,
                                    setShowManualDialog = { param ->
                                        showManualDialog = param
                                    },
                                    setIngredientScan = {
                                        ingredientSelected = ingredient
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientCard(
    ingredient: Ingredient,
    setShowManualDialog: (Boolean) -> Unit,
    setIngredientScan: (Ingredient) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(80.dp)
            .clickable { setShowManualDialog(true) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${ingredient.description}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = if (ingredient.imageUrl.isNotEmpty()) {
                    rememberAsyncImagePainter(ingredient.imageUrl)
                } else {
                    painterResource(id = R.drawable.box) // Reemplaza con tu recurso de imagen de marcador de posición
                },
                contentDescription = ingredient.id,
                modifier = Modifier.size(45.dp),
                contentScale = ContentScale.Crop
            )
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
    var ingredientSelected by remember { mutableStateOf(SampleData.sampleIngredient) }

    FoodieFrontendTheme {

        if(showManualDialog && ingredientSelected != null) {
            AlertIngredientManual(
                navController = rememberNavController(),
                setShowDialog = {showManualDialog = it},
                productType = ingredientSelected
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
                Row {
                    Title(
                        title = stringResource(R.string.your_stock),
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 30.dp)
                    )
                    ImageWithResource(
                        resourceId = R.drawable.ic_scan,
                        onClick = {
                            //navController.navigate("camera_screen")
                        }
                    )
                }

                CustomTextField(
                    value = ingredient,
                    placeholder = stringResource(R.string.look_for_ingredient),
                    onValueChange = { newValue ->
                        ingredient = newValue
                    },
                    trailingIcon = R.drawable.ic_search,
                    modifier = Modifier,
                    enabled = false
                )
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
                            onIncrement = { /* Implement increment action */ },
                            onDecrement = { /* Implement decrement action */ }
                        )
                    }
                }
                if (lookFor) {

                    Surface(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Card(
                            modifier = Modifier,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onSurface,
                            ),
                        ) {
                            Column(
                            ) {
                                SampleData.sampleIngredients.forEach { ingredient ->
                                    IngredientCard(
                                        ingredient,
                                        setShowManualDialog = { param ->
                                            showManualDialog = param
                                        },
                                        setIngredientScan = {
                                            ingredientSelected = ingredient
                                        }
                                    )                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScaned() {
    FoodieFrontendTheme {
        StockScreen(navController = rememberNavController(), codeEan = "123498389")
    }
}
