package com.example.foodiefrontend.presentation.ui.screens.stock.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomTextField
import com.example.foodiefrontend.viewmodel.StockViewModel
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AlertLookForIngredient(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
    productList: List<Ingredient>? = null
) {
    val context = LocalContext.current
    val viewModel: StockViewModel = viewModel()
    var showManualDialog by remember { mutableStateOf(false) }
    var recentlyOpen by remember { mutableStateOf(true) }
    var ingredient by remember { mutableStateOf("") }
    var ingredientSelected by remember { mutableStateOf(SampleData.sampleIngredient) }
    val searchResults by viewModel.stockResult.observeAsState(emptyList())

    AlertDialog(
        onDismissRequest = { setShowDialog(false) },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Buscar ingrediente",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Para añadir a tu stock",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CustomTextField(
                    value = ingredient,
                    placeholder = stringResource(R.string.look_for_ingredient),
                    onValueChange = { newValue ->
                        ingredient = newValue
                        recentlyOpen = false
                        viewModel.searchProducts(context, newValue)
                    },
                    trailingIcon = R.drawable.ic_search,
                    modifier = Modifier.fillMaxWidth()
                )
                if (searchResults.isNotEmpty()) {
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
                            LazyColumn(
                                modifier = Modifier.height(300.dp),
                            ) {
                                items(searchResults) { ingredient ->
                                    IngredientCard(
                                        ingredient,
                                        onClick = {
                                            setShowDialog(false)
                                            val ingredientJson = URLEncoder.encode(
                                                Gson().toJson(ingredient),
                                                StandardCharsets.UTF_8.toString()
                                            )
                                            navController.navigate("alertIngredient/$ingredientJson")
                                        }
                                    )
                                }
                            }
                        }
                    }
                } else if (recentlyOpen) {
                    null
                } else {
                    Column(
                        modifier = Modifier.height(200.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No se encontraron productos con ese nombre",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                        )
                    }
                }
            }
        },
        dismissButton = { },
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


@Composable
fun IngredientCard(
    ingredient: Ingredient,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(80.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ingredient.description,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "${ingredient.quantity} ${ingredient.unitMesure}",
                    fontSize = 16.sp
                )
            }
            Image(
                painter = if (ingredient.imageUrl.isNotEmpty()) {
                    rememberAsyncImagePainter(ingredient.imageUrl)
                } else {
                    painterResource(id = R.drawable.box) // Placeholder image
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
private fun Preview() {
    var showDialog by remember { mutableStateOf(true) }

    FoodieFrontendTheme {
        AlertLookForIngredient(
            rememberNavController(),
            setShowDialog = { showDialog = it }
        )
    }
}

