package com.example.foodiefrontend.presentation.ui.screens.suggestedRecipes

import SuggestedRecipesViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.CustomToolbar
import com.example.foodiefrontend.presentation.ui.components.Title
import com.example.foodiefrontend.presentation.ui.screens.recipes.RecipesCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuggestedRecipesScreen(
    navController: NavController,
    viewModel: SuggestedRecipesViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes()
    }

    val recipes by viewModel.recipes.observeAsState(initial = emptyList())
    Scaffold(
        topBar = {
            CustomToolbar(navController = navController, title = "")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Title(title = stringResource(R.string.suggests_for_you), textAlign = TextAlign.Start)

            recipes.forEach { item ->
                RecipesCardItem(
                    title = item.name,
                    image = item.imageUrl
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                CustomButton(
                    onClick = {
                        //TODO
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    text = stringResource(R.string.more_options),
                )
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSuggestedRecipes() {
    SuggestedRecipesScreen(rememberNavController())
}