package com.samuelokello.kazihub.presentation.shared.components

import android.os.Build
import androidx.annotation.RequiresExtension
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
import com.google.android.libraries.places.api.model.AutocompletePrediction

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun LocationDropDown(
    value: String,
    onValueChange: (String, String) -> Unit,
    locationSuggestions: List<AutocompletePrediction>,
    onLocationInputChanged: (String) -> Unit,
    label: String
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it, "")
                onLocationInputChanged(it)
            },
            label = { Text(label) },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = locationSuggestions.isNotEmpty(),
            onDismissRequest = { onLocationInputChanged("") }
        ) {
            locationSuggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = { Text(text = suggestion.getFullText(null).toString()) },
                    onClick = {
                        val newLocation = suggestion.getFullText(null).toString()
                        val placeId = suggestion.placeId
                        onValueChange(newLocation, placeId)
                        onLocationInputChanged("")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
//@Composable
//fun LocationDropDown(
//    value: String,
//    onValueChange: (String, String) -> Unit,
//    places: PlacesClient,
//    label: String
//) {
//    var suggestions by remember { mutableStateOf(listOf<AutocompletePrediction>())}
//
//    LaunchedEffect(value) {
//        if(value.isNotEmpty()) {
//            val request = FindAutocompletePredictionsRequest.builder()
//                .setQuery(value)
//                .build()
//
//            val response = places.findAutocompletePredictions(request).await()
//            suggestions = response.autocompletePredictions
//        } else {
//            suggestions = emptyList()
//        }
//    }
//
//    Column {
//        OutlinedTextField(
//            value = value,
//            onValueChange = { onValueChange(it, "") },
//            label = { Text(label) },
//            shape = RoundedCornerShape(8.dp),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        DropdownMenu(
//            expanded = suggestions.isNotEmpty(),
//            onDismissRequest = {suggestions = listOf() }
//        ) {
//            suggestions.forEach { suggestion ->
//                DropdownMenuItem(
//                    text = { Text(text = suggestion.getFullText(null).toString()) },
//                    onClick = {
//                        val newLocation = suggestion.getFullText(null).toString()
//                        val placeId = suggestion.placeId
//                        onValueChange(newLocation, placeId)
//                        suggestions = listOf()
//                    })
//            }
//        }
//    }
//}