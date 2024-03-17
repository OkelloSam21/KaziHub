package com.samuelokello.kazihub.presentation.shared.message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun MessagesScreen() {
    MessageScreenContent()
}

@Composable
fun MessageScreenContent() {
    Column (
        Modifier.fillMaxSize()
    ){
        Text(text = "Messages")
    }
}