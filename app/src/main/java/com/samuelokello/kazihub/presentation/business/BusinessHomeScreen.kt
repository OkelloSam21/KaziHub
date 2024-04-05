package com.samuelokello.kazihub.presentation.business

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.samuelokello.kazihub.presentation.shared.components.AppBar
import com.samuelokello.kazihub.presentation.shared.components.ReusableModalNavigationDrawer
import com.samuelokello.kazihub.presentation.shared.components.StandardScaffold
import com.samuelokello.kazihub.ui.theme.KaziHubTheme

@Composable
fun BusinessHomeScreen() {
    val navcontroller = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    ReusableModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { },
        topBar = {
            AppBar(onNavigationIconClick = { })
        },
        bottomBar = {
            StandardScaffold(
                navController = navcontroller,
                content = { /*TODO*/ }
            )
        }
    ) {
        Column {
            Text(text = "Business Home Screen")
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