package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme
import com.example.foodiefrontend.presentation.ui.screens.login.LoginScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    trailingIcon: Int? = null,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)



    Surface(
        elevation = 4.dp,
        shape = shape,
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            },
            modifier = modifier
                .background(MaterialTheme.colorScheme.onTertiary, shape)
                .border(1.dp, MaterialTheme.colorScheme.onTertiary, shape)
                .fillMaxWidth(),
            shape = shape,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colorScheme.onSurface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            trailingIcon = {
                if (trailingIcon != null) {
                    ImageWithResource(
                        resourceId = trailingIcon,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
            },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    FoodieFrontendTheme {
        CustomTextField(
            value = "",
            placeholder = "Ingrese correo",
            onValueChange = { }
        )
    }
}