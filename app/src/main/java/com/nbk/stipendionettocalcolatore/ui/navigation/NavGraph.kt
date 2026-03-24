package com.nbk.stipendionettocalcolatore.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nbk.stipendionettocalcolatore.R
import com.nbk.stipendionettocalcolatore.ui.screens.CalculatorScreen
import com.nbk.stipendionettocalcolatore.ui.screens.HistoryScreen
import com.nbk.stipendionettocalcolatore.ui.screens.ResultsScreen
import com.nbk.stipendionettocalcolatore.ui.screens.SettingsScreen
import com.nbk.stipendionettocalcolatore.ui.theme.AccentOrange
import com.nbk.stipendionettocalcolatore.ui.theme.PrimaryNavy
import com.nbk.stipendionettocalcolatore.viewmodel.calculator.CalculatorViewModel

sealed class Screen(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val resourceId: Int) {
    object Calculator : Screen("calculator", Icons.Filled.Calculate, R.string.calculate)
    object HistoryRoute : Screen("history", Icons.Filled.History, R.string.history)
    object SettingsRoute : Screen("settings", Icons.Filled.Settings, R.string.settings)
    object Results : Screen("results", Icons.Filled.Calculate, R.string.calculate)
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val viewModel: CalculatorViewModel = viewModel()
    
    val items = listOf(
        Screen.Calculator,
        Screen.HistoryRoute,
        Screen.SettingsRoute
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = PrimaryNavy
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = isSelected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AccentOrange,
                            selectedTextColor = AccentOrange,
                            indicatorColor = AccentOrange.copy(alpha = 0.1f),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Calculator.route, Modifier.padding(innerPadding)) {
            composable(Screen.Calculator.route) { 
                CalculatorScreen(viewModel, onNavigateToResults = {
                    navController.navigate(Screen.Results.route)
                }) 
            }
            composable(Screen.HistoryRoute.route) { 
                HistoryScreen(viewModel, onNavigateToDetails = {
                    // Logic to show details if needed, for now just show list
                })
            }
            composable(Screen.SettingsRoute.route) { 
                SettingsScreen() 
            }
            composable(Screen.Results.route) {
                ResultsScreen(viewModel, onBack = {
                    navController.popBackStack()
                })
            }
        }
    }
}
