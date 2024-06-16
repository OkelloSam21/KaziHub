package com.samuelokello.kazihub.presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.google.android.libraries.places.api.model.Place
import com.samuelokello.kazihub.presentation.shared.components.EditTextField

@Composable
fun LocationAutocompleteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    suggestions: List<Place>,
    onSuggestionSelected: (Place) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    val showDropdown = remember { mutableStateOf(true) }
    Column(modifier = modifier) {
        EditTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                showDropdown.value = true
            },
            label = label,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
        )
        DropdownMenu(
            expanded = showDropdown.value,
            onDismissRequest = { showDropdown.value = false }
        ) {
            suggestions.forEach { prediction ->
                DropdownMenuItem(onClick = {
                    onSuggestionSelected(prediction)
                    showDropdown.value = false
                },
                    text = { prediction.name?.let { Text(it) } }
                )
            }
        }
    }
}



