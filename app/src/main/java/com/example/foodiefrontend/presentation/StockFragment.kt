package com.example.foodiefrontend.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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


class StockViewModel : ViewModel() {

}
class StockFragment : Fragment() {
    private val viewModel: StockViewModel by viewModels {
        ViewModelProvider.NewInstanceFactory()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StockContent() {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tu stock", style = MaterialTheme.typography.headlineLarge)
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Buscar ingrediente") }
            )
            Text(text = "matriz de stock, 3 columnas por fila")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun HomeContentPreview() {
        StockContent()
    }
}