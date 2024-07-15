package com.sonchan.dobakapppractice.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.sonchan.dobakapppractice.presentation.login.GoogleAuthUiClientProvider
import com.sonchan.dobakapppractice.presentation.nav.BottomNavItem
import com.sonchan.dobakapppractice.presentation.nav.BottomNavigation
import com.sonchan.dobakapppractice.presentation.nav.NavigationGraph
import com.sonchan.dobakapppractice.ui.theme.DobakAppPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this) // Firebase 초기화
        GoogleAuthUiClientProvider.initialize(applicationContext) // GoogleAuthUiClient 초기화
        setContent {
            DobakAppPracticeTheme {
                val navController = rememberNavController()
                NavigationGraph(navController = navController, "login")
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
        .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = if (currentRoute != "login" && currentRoute != "profile") 56.dp else 0.dp)
        ) {
            NavigationGraph(navController = navController, BottomNavItem.Dobak.screenRoute)
        }
        if (currentRoute != "login" && currentRoute != "profile") {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                BottomNavigation(
                    navController = navController
                )
            }
        }
    }
}