package com.samuelokello.kazihub.presentation.shared.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.tasks.await

@Composable
fun LocationDropDown(
    value: String,
    onValueChange: (String, String) -> Unit,
    places: PlacesClient,
    label: String
) {
    var suggestions by remember { mutableStateOf(listOf<AutocompletePrediction>())}

    LaunchedEffect(value) {
        if(value.isNotEmpty()) {
            val request = FindAutocompletePredictionsRequest.builder()
                .setQuery(value)
                .build()

            val response = places.findAutocompletePredictions(request).await()
            suggestions = response.autocompletePredictions
        } else {
            suggestions = emptyList()
        }
    }

    Column {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it, "") },
            label = { Text(label) },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = suggestions.isNotEmpty(),
            onDismissRequest = {suggestions = listOf() }
        ) {
            suggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = { Text(text = suggestion.getFullText(null).toString()) },
                    onClick = {
                        val newLocation = suggestion.getFullText(null).toString()
                        val placeId = suggestion.placeId
                        onValueChange(newLocation, placeId)
                        suggestions = listOf()
                    })
            }
        }
    }
}