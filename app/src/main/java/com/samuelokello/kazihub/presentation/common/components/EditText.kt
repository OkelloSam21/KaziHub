package com.samuelokello.kazihub.presentation.shared.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun EditText(
    @DrawableRes icon: Int,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOption: KeyboardOptions,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = label) },
//        placeholder = { Text(text = label) },
        leadingIcon = { painterResource(id = icon) },
        keyboardOptions = keyboardOption,
    )
}
