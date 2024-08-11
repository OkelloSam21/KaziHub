package com.samuelokello.kazihub.presentation.common.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.Place

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationAutocompleteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    suggestions: List<Place>,
    onSuggestionSelected: (Place) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(value) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
                onValueChange(it)
                expanded = it.isNotEmpty() && suggestions.isNotEmpty()
            },
            placeholder = { Text(text = placeholder) },
            trailingIcon = {
                Icon(Icons.Default.LocationOn, contentDescription = "location")
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .menuAnchor()  // Use menuAnchor for alignment with the dropdown menu
                .fillMaxWidth()

        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            suggestions.forEach { prediction ->
                DropdownMenuItem(
                    onClick = {
                        selectedText = prediction.name?.toString() ?: ""
                        onSuggestionSelected(prediction)
                        expanded = false
                    },
                    text = { Text(prediction.name?.toString() ?: "") }
                )
                Log.e("LocationAutocompleteTextField", "suggestions: ${prediction.name}")
            }
        }
    }
}
