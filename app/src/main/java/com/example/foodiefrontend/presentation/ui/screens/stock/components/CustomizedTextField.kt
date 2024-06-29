package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CustomizedTextField(
    value: String,
    placeholder: String,
    label: String = "",
    onValueChange: (String) -> Unit,
    trailingIcon: Int? = null,
    modifier: Modifier = Modifier,
    onClickIcon: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    readOnly: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    height: Dp = 50.dp
) {

    Surface(
        modifier = Modifier
            .height(height)
            .width(70.dp)
            .then(modifier),
        onClick = { onClickIcon() }
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    textAlign = TextAlign.Center
                )
            },
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onTertiary)
                .then(modifier),
            singleLine = singleLine,
            textStyle = TextStyle(fontSize = 14.sp, textAlign = TextAlign.Center),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = if (enabled)
                    MaterialTheme.colorScheme.onSurface
                else
                    Color(0xFFD8D8D8),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            enabled = enabled
        )
    }
}

@Preview
@Composable
private fun PreviewCustomizedTextField() {
    FoodieFrontendTheme {
        var text by remember { mutableStateOf("value") }

        CustomizedTextField(
            value = text,
            placeholder = "placeholder",
            label = "label",
            onValueChange = { text = it },
            enabled = true
        )
    }
}