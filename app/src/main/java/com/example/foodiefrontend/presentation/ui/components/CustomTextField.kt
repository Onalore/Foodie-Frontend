package com.example.foodiefrontend.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodiefrontend.presentation.theme.FoodieFrontendTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CustomTextField(
    value: String,
    placeholder: String,
    label: String = "",
    onValueChange: (String) -> Unit,
    trailingIcon: Int? = null,
    modifier: Modifier = Modifier,
    onClickIcon: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    enabled: Boolean,
) {
    val shape = RoundedCornerShape(16.dp)



    Surface(
        elevation = 4.dp,
        shape = shape,
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .then(modifier),
        onClick = { onClickIcon() }
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                if (label != "") {
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
                .fillMaxWidth()
                .then(modifier),
            shape = shape,
            singleLine = true,
            textStyle = TextStyle(fontSize = 18.sp),
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
                            .width(15.dp),
                        onClick = { onClickIcon() }
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
            value = "value",
            placeholder = "placeholder",
            label = "label",
            onValueChange = { },
            enabled = false
        )
    }
}