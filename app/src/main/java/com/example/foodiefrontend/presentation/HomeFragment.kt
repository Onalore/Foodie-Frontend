@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodiefrontend.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodiefrontend.AppColors

// ViewModel para manejar la lógica de negocio si es necesario
class HomeViewModel : ViewModel() {
    // Aquí puedes agregar la lógica necesaria para el HomeFragment
}

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun HomeContent(username: String) {
        // Diseño del HomeFragment utilizando Jetpack Compose
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hola $username", fontSize = 30.sp, textAlign = TextAlign.Left)

            // Agrega aquí más elementos de interfaz de usuario si es necesario
            Text(text = "Que vas a comer hoy?", fontSize = 20.sp)
            Button(onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.fondoBoton,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )

                ) {
                Text("Sugerir recetas en base a ingredientes que tengo",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center)
            }
            Button(onClick = { /*TODO*/ }) {
                Text("Incluir cualquier ingrediente\n" + "!Hoy tengo que comprar!",
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Distribute cards evenly
            ) {
                repeat(5) { // Repeat 10 times to create 10 cards
                    Card(
                        modifier = Modifier
                            .weight(1f) // Make each card occupy equal space
                            .padding(8.dp), // Add some padding around each card
                        onClick = { /* Handle card click */ }
                    ) {
                        // Content of each card (image, text, etc.)
                        Text(text = "Card $it") // Placeholder text for now
                    }
                }
            }
            Text(text = "Tus Recetas favoritas",
                fontSize = 20.sp,
                textAlign = TextAlign.Left)
            val cards = listOf("Card 1", "Card 2", "Card 3", "Card 4", "Card 5")
            val pagerState = rememberPagerState(pageCount = {
                cards.size
            })
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Distribute cards evenly
            ){
                HorizontalPager(state = pagerState) { page ->
                    Card(
                        modifier = Modifier
                            .weight(1f) // Make each card occupy equal space
                            .padding(8.dp), // Add some padding around each card
                        onClick = { /* Handle card click */ }
                    ) {
                        // Content of each card (image, text, etc.)
                        Text(text = cards[page]) // Placeholder text for now
                    }
                }
            }
        }
    }

    @Preview (showBackground = true)
    @Composable
    fun HomeContentPreview() {
        HomeContent("Usuario")
    }
}