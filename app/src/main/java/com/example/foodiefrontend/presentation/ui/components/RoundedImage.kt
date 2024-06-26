package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun RoundedImage(
    image: String?,
    modifier: Modifier,
    modifierBox: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
    ) {
        val displayImage = image
            ?: "https://okdiario.com/img/2021/12/09/hamburguesas-caseras-rellenas-de-queso-cheddar-655x368.jpg"
        Box {
            Image(
                painter = rememberAsyncImagePainter(model = displayImage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifierBox)
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val image = "https://cdn0.recetasgratis.net/es/posts/9/2/3/carne_molida_con_papas_42329_orig.jpg"

    RoundedImage(image = image, modifier = Modifier)
}