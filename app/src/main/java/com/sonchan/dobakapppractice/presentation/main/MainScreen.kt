package com.sonchan.dobakapppractice.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.FirebaseApp
import com.sonchan.dobakapppractice.presentation.alert.LackAlert
import com.sonchan.dobakapppractice.presentation.login.GoogleAuthUiClientProvider
import com.sonchan.dobakapppractice.presentation.nav.BottomNavigation
import com.sonchan.dobakapppractice.presentation.nav.NavigationGraph
import com.sonchan.dobakapppractice.presentation.nav.setupNavGraph
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
                setupNavGraph(navController = navController, "login")
            }
//            DobakAppPracticeTheme {
//                val navController = rememberNavController()
//                Scaffold(
//                    bottomBar = { BottomNavigation(navController = navController) }
//                ) {
//                    Box(Modifier.padding(it)) {
//                        NavigationGraph(navController = navController)
//                    }
//                }
//            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController)
        }
    }
}