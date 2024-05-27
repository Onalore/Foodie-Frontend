@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodiefrontend.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecipesViewModel : ViewModel() {

}
class RecipesFragment : Fragment() {
    private val viewModel: RecipesViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    @Composable
    fun RecipesContent() {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

        ){
            Button(onClick = { /*TODO*/ },Modifier.align(Alignment.End)) {
               Text(text = "Icono Filter")

            }
            Text(text = "Recetas", style = MaterialTheme.typography.headlineLarge)
            TextField(value = "", onValueChange = {}, placeholder = {Text(text = "Buscar Receta") } )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly)
            {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Todas")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Favoritas")
                }
            }
        }
        
    }

    @Preview(showBackground = true)
    @Composable
    fun RecipesContentPreview() {
        RecipesContent()
    }
}