package com.example.foodiefrontend.presentation.ui.screens.stock.components

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

@Composable
fun AlertLookForIngredient(
    navController: NavController,
    setShowDialog: (Boolean) -> Unit,
    productList: List<Ingredient>? = null
) {
    val context = LocalContext.current
    var showManualDialog by remember { mutableStateOf(false) }
    var recentlyOpen by remember { mutableStateOf(true) }
    var ingredient by remember { mutableStateOf("") }
    var ingredientSelected by remember {
        mutableStateOf(SampleData.sampleIngredient)
    }


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
                    },
                    trailingIcon = R.drawable.ic_search,
                    modifier = Modifier,
                    enabled = false
                )
                if (productList != null) {
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
                                productList.forEach { ingredient ->
                                    item {
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
                } else if (recentlyOpen){
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
    setShowManualDialog: (Boolean) -> Unit,
    setIngredientScan: (Ingredient) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(80.dp)
            .clickable {
                setShowManualDialog(true)
                setIngredientScan(ingredient)
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
                text = "${ingredient.description}",
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
private fun Preview() {
    var showDialog by remember { mutableStateOf(true) }

    FoodieFrontendTheme {
        AlertLookForIngredient(
            rememberNavController(),
            setShowDialog = { showDialog = it }
        )
    }
}

