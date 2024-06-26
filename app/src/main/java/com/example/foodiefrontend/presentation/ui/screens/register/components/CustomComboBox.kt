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
import com.example.foodiefrontend.data.Persona
import com.example.foodiefrontend.presentation.ui.components.CustomTextField

@Composable
fun CustomComboBox(
    selectedItems: List<String>,
    label: String,
    onSelectedItemsChange: (List<String>) -> Unit,
    items: List<String>,
    isMultiSelect: Boolean = true
) {
    CustomComboBoxBase(
        selectedItems = selectedItems,
        label = label,
        onSelectedItemsChange = { onSelectedItemsChange(it as List<String>) },
        items = items,
        isMultiSelect = isMultiSelect
    )
}

@Composable
fun CustomComboBox(
    selectedPerson: List<Persona>,
    label: String,
    onSelectedPersonChange: (List<Persona>) -> Unit,
    items: List<Persona>
) {
    CustomComboBoxBasePersona(
        selectedItems = selectedPerson,
        label = label,
        onSelectedItemsChange = { onSelectedPersonChange(it) },
        items = items,
        isMultiSelect = true
    )
}


@Composable
fun CustomComboBox(
    selectedItem: String,
    label: String,
    onSelectedItemChange: (String) -> Unit,
    items: List<String>
) {
    CustomComboBoxBase(
        selectedItems = listOf(selectedItem),
        label = label,
        onSelectedItemsChange = { selectedItems -> onSelectedItemChange(selectedItems.first()) },
        items = items,
        isMultiSelect = false
    )
}

@Composable
private fun CustomComboBoxBase(
    selectedItems: List<String>,
    label: String,
    onSelectedItemsChange: (List<String>) -> Unit,
    items: List<String>,
    isMultiSelect: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) R.drawable.ic_chevron_up else R.drawable.ic_chevron_down

    Column {
        CustomTextField(
            value = selectedItems.joinToString(", "),
            label = label,
            placeholder = "",
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
            modifier = Modifier.width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            items.forEach { item ->
                val isSelected = selectedItems.contains(item)
                DropdownMenuItem(
                    onClick = {
                        val newSelectedItems = if (isMultiSelect) {
                            if (isSelected) selectedItems - item else selectedItems + item
                        } else {
                            if (isSelected) emptyList() else listOf(item)
                        }
                        onSelectedItemsChange(newSelectedItems)
                        expanded = isMultiSelect // Cerrar el menú si es selección simple
                    }
                ) {
                    Text(text = item + if (isSelected) " ✓" else "")
                }
            }
        }
    }
}


@Composable
private fun CustomComboBoxBasePersona(
    selectedItems: List<Persona>,
    label: String,
    onSelectedItemsChange: (List<Persona>) -> Unit,
    items: List<Persona>,
    isMultiSelect: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) R.drawable.ic_chevron_up else R.drawable.ic_chevron_down

    Column {
        CustomTextField(
            value = selectedItems.joinToString(", ") { "${it.nombre} ${it.apellido}" },
            label = label,
            placeholder = "",
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
            modifier = Modifier.width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            items.forEach { item ->
                val isSelected = selectedItems.contains(item)
                DropdownMenuItem(
                    onClick = {
                        val newSelectedItems = if (isMultiSelect) {
                            if (isSelected) selectedItems - item else selectedItems + item
                        } else {
                            if (isSelected) emptyList() else listOf(item)
                        }
                        onSelectedItemsChange(newSelectedItems)
                        expanded = isMultiSelect
                    }
                ) {
                    Text(text = "${item.nombre} ${item.apellido}" + if (isSelected) " ✓" else "")
                }
            }
        }
    }
}
