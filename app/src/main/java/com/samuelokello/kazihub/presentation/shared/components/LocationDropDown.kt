package com.samuelokello.kazihub.presentation.shared.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.presentation.common.location.LocationViewModel

@Composable
fun LocationDropDown(
    viewModel: LocationViewModel, // Use the interface as a parameter
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    val locationSuggestions = viewModel.locationSuggestions // Get location suggestions from the ViewModel
    Log.d("LocationDropDown", "Current number of location suggestions: ${locationSuggestions.size}") // Debug

    Column {
        // OutlinedTextField for location input
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                viewModel.onLocationChange(it)
            },
            label = { Text(label) },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )

        // DropdownMenu for location suggestions
        DropdownMenu(
            expanded = locationSuggestions.isNotEmpty(),
            onDismissRequest = {
                // Handle dropdown dismiss (optional)
            }
        ) {
            locationSuggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = { Text(text = suggestion.toString()) },
                    onClick = {
                        val newLocation = suggestion.toString()

                        onValueChange(newLocation)
                        viewModel.onLocationChange(newLocation)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

