package com.example.foodiefrontend.presentation.ui.screens.register.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.ui.components.CustomTextField

@Composable
fun CustomComboBox() {
    val restrictions = listOf(
        "Celiaquía",
        "Embarazo",
        "Veganismo",
        "Vegetarianismo",
        "Diabetes",
        "Kosher",
        "Hipertensión",
        "Intolerancia a la lactosa"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedItems by remember { mutableStateOf(emptyList<String>()) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        R.drawable.ic_chevron_up
    else
        R.drawable.ic_chevron_down


    Column() {
        CustomTextField(
            value = selectedItems.joinToString(", "),
            label = "Restricciones alimentarias",
            placeholder = "",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            readOnly = true,
            trailingIcon = icon,
            onClickIcon = { expanded = !expanded }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            restrictions.forEach { label ->
                val isSelected = selectedItems.contains(label)
                DropdownMenuItem(onClick = {
                    if (isSelected) {
                        selectedItems = selectedItems - label
                    } else {
                        selectedItems = selectedItems + label
                    }
                }) {
                    Text(text = label + if (isSelected) " ✓" else "")
                }
            }
        }
    }
}