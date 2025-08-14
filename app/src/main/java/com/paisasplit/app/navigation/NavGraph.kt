package com.paisasplit.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.paisasplit.app.presentation.ui.screens.HomeScreen
import com.paisasplit.app.presentation.ui.screens.GroupsScreen
import com.paisasplit.app.presentation.ui.screens.AddSplitScreen
import com.paisasplit.app.presentation.ui.screens.AnalyticsScreen
import com.paisasplit.app.presentation.ui.screens.SearchScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    PaisaScaffold(navController)
}

@Composable
private fun PaisaScaffold(navController: NavHostController) {
    val items = listOf(
        NavItem("home", "Home", Icons.Filled.Home),
        NavItem("groups", "Groups", Icons.Filled.Group),
        NavItem("add", "Add", Icons.Filled.Add),
        NavItem("analytics", "Analytics", Icons.Filled.Analytics),
        NavItem("search", "Search", Icons.Filled.Search)
    )

    androidx.compose.material3.Scaffold(
        bottomBar = {
            val currentRoute = currentRoute(navController)
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = { navController.navigate(item.route) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") { HomeScreen() }
            composable("groups") { GroupsScreen() }
            composable("add") { AddSplitScreen() }
            composable("analytics") { AnalyticsScreen() }
            composable("search") { SearchScreen() }
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route ?: "home"
}

data class NavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)


