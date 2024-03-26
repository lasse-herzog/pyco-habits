package com.example.pyco.views

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pyco.views.navigation.Screen
import com.example.pyco.views.streak.StreakView

//import com.example.pyco.views.ui.theme.PycoTheme

data class NavBarItem(val screen: Screen, val icon: ImageVector)

/**
 * The Composable that represents the NavBar at the bottom of the app.
 */
@Composable
fun PycoNavigationBar() {
    val navController = rememberNavController()
    val navBarItems = listOf(
        NavBarItem(Screen.Streak, Icons.Filled.DateRange),
        NavBarItem(Screen.Home, Icons.Filled.Home),
        NavBarItem(Screen.Habits, Icons.AutoMirrored.Filled.List),
    )


    Scaffold(bottomBar = {
        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            navBarItems.forEach { (screen, icon) ->
                NavigationBarItem(
                    icon = { Icon(icon, contentDescription = null) },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable(Screen.Streak.route) { StreakView() }
            composable(Screen.Home.route) { PycoHomeScreen() }
            composable(Screen.CreateHabit.route) { CreateHabit(onNavigateUp = { navController.navigateUp() }) }
            composable(Screen.Habits.route) {
                HabitsOverviewScreen(onNavigateToHabitDetailsView = {
                    navController.navigate(
                        Screen.HabitDetailsView.route + "/$it"
                    )
                }, onNavigateToCreateHabit = { navController.navigate(Screen.CreateHabit.route) })
            }
            composable(
                route = Screen.HabitDetailsView.route + "/{habitId}",
                arguments = listOf(navArgument("habitId") {type = NavType.IntType})
            ) { backStackEntry ->
                val habitId: Int = backStackEntry.arguments?.getInt("habitId") ?: 0
                HabitDetailsView(onNavigateUp = { navController.navigateUp() }, habitId = habitId)
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
private fun NavigationBarPreview() {
    PycoTheme {
        PycoNavigationBar()
    }
}*/