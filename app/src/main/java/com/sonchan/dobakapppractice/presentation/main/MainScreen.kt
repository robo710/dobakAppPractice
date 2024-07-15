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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun MainScreen(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // MainViewModel에서 가져온 shouldShowBottomNavigation 상태
    val shouldShowBottomNavigation by viewModel.shouldShowBottomNavigation

    if(shouldShowBottomNavigation) {
        // currentRoute 변경 시 viewModel에서 상태 업데이트
        LaunchedEffect(currentRoute) {
            viewModel.updateBottomNavigationVisibility(currentRoute)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = if (shouldShowBottomNavigation) 56.dp else 0.dp)
                .fillMaxSize()
        ) {
            NavigationGraph(navController = navController, BottomNavItem.Dobak.screenRoute)
        }

        // shouldShowBottomNavigation 값에 따라 조건부로 BottomNavigation을 렌더링
        if (shouldShowBottomNavigation) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter // contentAlignment의 속성을 수정
            ) {
                BottomNavigation(
                    navController = navController
                )
            }
        }
    }
}
