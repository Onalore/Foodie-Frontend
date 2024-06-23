package com.example.foodiefrontend.presentation.ui.screens.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import com.example.foodiefrontend.R
import com.example.foodiefrontend.presentation.ui.components.CustomTextField

@Composable
fun CustomComboBox(
    selectedItems: List<String>,
    onSelectedItemsChange: (List<String>) -> Unit
) {
    val restricciones = listOf(
        "Celiaquía",
        "Embarazo",
        "Vegetarianismo",
        "Veganismo",
        "Diabetes",
        "Kosher",
        "Hipertensión",
        "Intolerancia a la Lactosa"
    )
    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        R.drawable.ic_chevron_up
    else
        R.drawable.ic_chevron_down


    Column() {
        CustomTextField(
            value = selectedItems.joinToString(", "),
            placeholder = "",
            label = "Restricciones alimentarias",
            onValueChange = { },
            trailingIcon = icon,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    textfieldSize = coordinates.size.toSize()
                },
            onClickIcon = { expanded = !expanded },
            readOnly = true,
            enabled = false
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            restricciones.forEach { label ->
                val isSelected = selectedItems.contains(label)
                DropdownMenuItem(onClick = {
                    val newSelectedItems = if (isSelected) {
                        selectedItems - label
                    } else {
                        selectedItems + label
                    }
                    onSelectedItemsChange(newSelectedItems)
                }) {
                    Text(text = label + if (isSelected) " ✓" else "")
                }
            }
        }
    }
}
