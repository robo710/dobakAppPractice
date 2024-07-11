package com.sonchan.dobakapppractice.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {  if (currentRoute != "login" && currentRoute != "profile") {
            BottomNavigation(navController)
        }}
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController, BottomNavItem.Dobak.screenRoute)
        }
    }
}