package com.behnamuix.persianM3Calendar.ui.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.behnamuix.persianM3Calendar.ui.nav.screens.BottomNavSc
import com.behnamuix.persianM3Calendar.ui.nav.screens.DownloaderSc
import com.behnamuix.persianM3Calendar.ui.nav.screens.PersianCalendarScreen

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, navController: NavController) {
    NavigationBar {
        val currentRoute by navController.currentBackStackEntryAsState()
        BottomNavSc.btnNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute?.destination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = ""
                    )
                },
                label = {
                    Text(screen.title)
                }

            )

        }
    }
}

@Composable
fun SetupBottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    paddingValue: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavSc.Downloader.route,
        modifier = Modifier.padding(paddingValue)
    ) {
        composable(BottomNavSc.Calendar.route) {
            PersianCalendarScreen()
        }
        composable(BottomNavSc.Downloader.route) {
            DownloaderSc()
        }
    }
}