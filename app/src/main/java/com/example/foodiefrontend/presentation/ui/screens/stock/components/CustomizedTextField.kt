package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    height: Dp = 70.dp
) {
    val shape = RoundedCornerShape(16.dp)

    Surface(
        elevation = 4.dp,
        shape = shape,
        modifier = Modifier
            .then(modifier),
        onClick = { onClickIcon() }
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                if (label.isNotEmpty()) {
                    Text(text = label)
                } else {
                    Text(text = placeholder)
                }
            },
            placeholder = {
                Text(
                    text = placeholder,
                    textAlign = TextAlign.Center
                )
            },
            readOnly = readOnly,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onTertiary, shape)
                .border(1.dp, MaterialTheme.colorScheme.onTertiary, shape)
                .then(modifier),
            shape = shape,
            singleLine = singleLine,
            maxLines = 1,
            textStyle = TextStyle(
                fontSize = 10.sp, textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.surface
            ),
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
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .width(30.dp),
                        onClick = { onClickIcon() }
                    )
                }
            },
        )
    }
}


