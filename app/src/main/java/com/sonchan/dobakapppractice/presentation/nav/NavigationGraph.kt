package com.sonchan.dobakapppractice.presentation.nav

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sonchan.dobakapppractice.presentation.dobak.DobakScreen
import com.sonchan.dobakapppractice.presentation.dobak.DobakViewModel
import com.sonchan.dobakapppractice.presentation.login.GoogleAuthUiClientProvider.googleAuthUiClient
import com.sonchan.dobakapppractice.presentation.login.LoginScreen
import com.sonchan.dobakapppractice.presentation.login.LoginViewModel
import com.sonchan.dobakapppractice.presentation.main.MainScreen
import com.sonchan.dobakapppractice.presentation.main.MainViewModel
import com.sonchan.dobakapppractice.presentation.mine.MineScreen
import com.sonchan.dobakapppractice.presentation.profile.ProfileScreen
import com.sonchan.dobakapppractice.presentation.rank.RankScreen
import com.sonchan.dobakapppractice.presentation.rank.RankViewModel
import kotlinx.coroutines.launch

@Composable
fun NavigationGraph(navController: NavHostController, destination: String) {

    NavHost(navController = navController, startDestination = destination) {
        composable(BottomNavItem.Dobak.screenRoute) {
            DobakScreen(
                viewModel = DobakViewModel
                    (userData = googleAuthUiClient.getSignedInUser()),
                onProfileClick = {
                    navController.navigate("profile")
                }
            )
        }
        composable(BottomNavItem.Mine.screenRoute) {
            MineScreen(viewModel = DobakViewModel(userData = googleAuthUiClient.getSignedInUser()))
        }
        composable(BottomNavItem.Rank.screenRoute) {
            RankScreen(viewModel = RankViewModel())
        }
        composable("login") {
            val viewModel = viewModel<LoginViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate("main"){
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == ComponentActivity.RESULT_OK) {
                        coroutineScope.launch {
                            val signInResult = googleAuthUiClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    Toast.makeText(
                        context,
                        "성공",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate("main"){
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                    viewModel.resetState()
                }
            }

            LoginScreen(
                state = state,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }
        composable("profile") {
            val mainViewModel: MainViewModel = viewModel()
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        mainViewModel.setBottomNavigationVisibility(false)
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context,
                            "로그아웃됨",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate("login"){
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                }
            )
        }
        composable("main") {
            MainScreen(viewModel = MainViewModel())
        }
    }
}