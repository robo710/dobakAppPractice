package com.sonchan.dobakapppractice.presentation.nav

// Navigation.kt
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sonchan.dobakapppractice.presentation.login.GoogleAuthUiClientProvider.googleAuthUiClient
import com.sonchan.dobakapppractice.presentation.login.LoginScreen
import com.sonchan.dobakapppractice.presentation.login.LoginViewModel
import com.sonchan.dobakapppractice.presentation.profile.ProfileScreen
import kotlinx.coroutines.launch

@Composable
fun setupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel = viewModel<LoginViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            LaunchedEffect(key1 = Unit) {
                if (googleAuthUiClient.getSignedInUser() != null) {
                    navController.navigate("profile")
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

                    navController.navigate("main")
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
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current

            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context,
                            "로그아웃됨",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.navigate("login")
                    }
                }
            )
        }
        composable("main") {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNavigation(navController = navController) }
            ) {
                Box(Modifier.padding(it)) {
                    NavigationGraph(navController = navController)
                }
            }
        }
    }
}
