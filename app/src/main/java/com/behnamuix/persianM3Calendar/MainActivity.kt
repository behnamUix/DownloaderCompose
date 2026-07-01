package com.behnamuix.persianM3Calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.behnamuix.persianM3Calendar.ui.nav.BottomNavigationBar
import com.behnamuix.persianM3Calendar.ui.nav.SetupBottomNavigation
import com.behnamuix.persianM3Calendar.ui.nav.screens.PersianCalendarScreen

import com.behnamuix.testliquidbottomnav.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TestAppTheme {
                Scaffold(

                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        SetupBottomNavigation(
                            navController = navController,
                            paddingValue = innerPadding
                        )
                    }
                }
            }
        }
    }
}

