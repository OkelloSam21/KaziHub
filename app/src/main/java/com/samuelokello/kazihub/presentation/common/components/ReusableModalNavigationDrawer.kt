package com.samuelokello.kazihub.presentation.common.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReusableModalNavigationDrawer(
    drawerState: DrawerState,
    drawerContent: @Composable () -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
//    content: @Composable (PaddingValues) -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = drawerContent
    ) {
        Scaffold(
            topBar = topBar,
            modifier = Modifier.padding(bottom = 32.dp),
            content = {}
        )
    }
}