package com.samuelokello.kazihub.presentation.business.Home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.samuelokello.kazihub.presentation.business.Home.components.BusinessHomeScreenContent
import com.samuelokello.kazihub.presentation.common.components.AppBar
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BusinessHomeScreen() {
//    val navController = rememberNavController()
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val showBottomSheet by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = { AppBar(onNavigationIconClick = {}) },
//        modifier = Modifier.padding(bottom = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BusinessHomeScreenContent(
                jobs = emptyList(),
                onEvent = {
                    when(it) {
                        BusinessHomeUiEvents.OnCreateJobClick -> {}
                        BusinessHomeUiEvents.OnDrawerClick -> {}
                        is BusinessHomeUiEvents.OnJobClick -> {}
                        is BusinessHomeUiEvents.OnCreateJob -> {

                        }
                    }
                },
                showModalSheet = showBottomSheet,
                setShowModalSheet = { !showBottomSheet }
            )
        }
    }
}


@Preview
@Composable
fun BusinessHomeScreenPreview() {
    KaziHubTheme {
        BusinessHomeScreen()
    }
}