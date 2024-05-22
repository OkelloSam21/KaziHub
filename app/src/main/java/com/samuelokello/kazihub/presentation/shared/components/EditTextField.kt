package com.samuelokello.kazihub.presentation.shared.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditTextField(
    label: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    error: Boolean? = false,
    singleLine: Boolean?,
    trailingIcon: @Composable() (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label ?: "") },
//        placeholder = { Text(text = label ?: "") },
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        isError = error ?: false,
        singleLine = singleLine ?: true,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    )
}
