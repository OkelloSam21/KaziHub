package com.samuelokello.kazihub.presentation.shared.authentication.common

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.samuelokello.kazihub.ui.theme.primaryLight

interface AuthState {
    val isLoading: Boolean
        get() = false
    val error: String
        get() = ""
    val isSuccess: Boolean
        get() = false

}

@Composable
fun HandleLoading(state: AuthState) {
    if (state.isLoading) {
        ShowLoadingDialog()
    }
}

@Composable
fun HandleError(state: AuthState) {
    if (state.error.isNotEmpty()) {
//        ShowErrorToast(state.error)
        ShowErrorDialog(error = state.error)
    }
}

@Composable
fun HandleSuccess(state: AuthState, successMessage: String) {
    if (state.isSuccess) {
        ShowSuccessToast(successMessage)
    }
}


@Composable
fun ShowLoadingDialog() {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = primaryLight
                )
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun ShowErrorDialog(error: String) {
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Surface {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = primaryLight
                )
            ) {
                Column {
                    Text(text = error, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun ShowErrorToast(error: String) {
    val context = LocalContext.current
    Toast.makeText(context, error, Toast.LENGTH_LONG).show()
}

@Composable
fun ShowSuccessToast(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

