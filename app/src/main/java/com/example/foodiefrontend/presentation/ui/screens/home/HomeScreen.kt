@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.foodiefrontend.presentation.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.ui.components.CustomButton
import com.example.foodiefrontend.presentation.ui.components.ImageWithResource
import com.example.foodiefrontend.presentation.ui.components.Subtitle
import com.example.foodiefrontend.presentation.ui.components.Title

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    username: String = "Nicolaqui"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSurface),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.onTertiary)
                .padding(horizontal = 15.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Title(title = "${stringResource(R.string.hi)} $username")

            Title(
                title = stringResource(R.string.what_are_u_eating),
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(25.dp))

            CustomButton(
                text = stringResource(R.string.suggest_with_my_ingredients),
                containerColor = MaterialTheme.colorScheme.primary,
                icon = R.drawable.stock,
                modifier = Modifier.height(100.dp),
                colorIcon = ColorFilter.tint(Color.White),
                onClick = { }
            )

            Spacer(modifier = Modifier.height(25.dp))

            CustomButton(
                text = stringResource(R.string.suggest_without_my_ingredients),
                containerColor = MaterialTheme.colorScheme.secondary,
                icon = R.drawable.dice,
                modifier = Modifier.height(100.dp),
                colorIcon = ColorFilter.tint(Color.White),
                onClick = { }
            )


        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp, end = 15.dp, top = 40.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Subtitle(title = "Tus recetas favoritas")
            }

            //Está de ejemplo, se debe borrar una vez que se envíe la info verdadera
            val sampleData = listOf(
                "Title 1" to R.drawable.bombas_de_papa,
                "Title 2" to R.drawable.bombas_de_papa,
                "Title 3" to R.drawable.bombas_de_papa,
                "Title 4" to R.drawable.bombas_de_papa
            )

            item {
                HorizontalCardList(items = sampleData)
            }
        }
    }
}

@Composable
fun HorizontalCardList(items: List<Pair<String, Int>>) {
    LazyRow(
        contentPadding = PaddingValues(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            CardItem(title = item.first, image = item.second)
        }
    }
}

@Composable
fun CardItem(
    title: String,
    image: Int,
    liked: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box() {
        Card(
            modifier = modifier
                .padding(end = 10.dp)
                .width(270.dp)
                .height(200.dp)
        ) {
            ImageWithResource(
                resourceId = image,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Card(
            modifier = modifier
                .padding(start = 10.dp, top = 150.dp)
                .width(250.dp)
                .height(90.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.8f),
                contentColor = Color.Black
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                var isSelected by remember { mutableStateOf(false) }

                Subtitle(
                    title = "Bombas de papa con cosas",
                    modifier = Modifier.padding(end = 10.dp).weight(1f)
                )
                SelectableHeartIcon(
                    selected = isSelected,
                    onSelectedChange = { isSelected = it }
                )
            }
        }
    }
}

@Composable
fun SelectableHeartIcon(
    selected: Boolean = false,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(60.dp)
            .height(60.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onSelectedChange(!selected) }
    ) {
        ImageWithResource(
            resourceId = if (selected) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    HomeScreen(
        navController = rememberNavController(),
        username = "Usuario"
    )
}
