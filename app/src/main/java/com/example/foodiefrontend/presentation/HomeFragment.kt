package com.example.foodiefrontend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
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
        }
    }

    @Preview (showBackground = true)
    @Composable
    fun HomeContentPreview() {
        HomeContent("Usuario")
    }
}