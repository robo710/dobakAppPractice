package com.sonchan.dobakapppractice.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.sonchan.dobakapppractice.presentation.alert.LackAlert
import com.sonchan.dobakapppractice.presentation.login.GoogleAuthUiClient
import com.sonchan.dobakapppractice.presentation.login.LoginScreen
import com.sonchan.dobakapppractice.presentation.login.LoginViewModel
import com.sonchan.dobakapppractice.presentation.profile.ProfileScreen
import com.sonchan.dobakapppractice.ui.theme.DobakAppPracticeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this) // Firebase 초기화
        setContent {
            DobakAppPracticeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login"){
                    composable("login"){
                        val viewModel = viewModel<LoginViewModel>()
                        val state by viewModel.state.collectAsStateWithLifecycle()

                        LaunchedEffect(key1 = Unit) {
                            if(googleAuthUiClient.getSignedInUser() != null){
                                navController.navigate("profile")
                            }
                        }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.StartIntentSenderForResult(),
                            onResult = { result ->
                                if (result.resultCode == RESULT_OK){
                                    lifecycleScope.launch {
                                        val signInResult = googleAuthUiClient.signInWithIntent(
                                            intent = result.data ?: return@launch
                                        )
                                        viewModel.onSignInResult(signInResult)
                                    }
                                }
                            }
                        )
                        
                        LaunchedEffect(key1 = state.isSignInSuccessful) {
                            if(state.isSignInSuccessful){
                                Toast.makeText(
                                    applicationContext,
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
                                lifecycleScope.launch {
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
                    composable("profile"){
                        ProfileScreen(
                            userData = googleAuthUiClient.getSignedInUser(),
                            onSignOut = {
                                lifecycleScope.launch {
                                    googleAuthUiClient.signOut()
                                    Toast.makeText(
                                        applicationContext,
                                        "로그아웃됨",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("login")
                                }
                            }
                        )
                    }
                    composable("main") {
                        MainScreen(
                            viewModel = MainViewModel
                                (userData = googleAuthUiClient.getSignedInUser()),
                            onProfileClick = {
                                navController.navigate("profile")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onProfileClick: () -> Unit,
    viewModel: MainViewModel
) {
    var money by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            contentAlignment = Alignment.TopStart
        ) {
            IconButton(onClick = onProfileClick) {
                if (viewModel.userData?.profilePictureUrl != null) {
                    AsyncImage(
                        model = viewModel.userData.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = money,
                onValueChange = { money = it },
                textStyle = TextStyle(color = Color(0xFF000000)),
                label = { Text("돈 입력") },
                placeholder = { Text("EX)1,000,000") },
                modifier = Modifier.width(350.dp),
                singleLine = true
            )
            Button(
                onClick = {
                    viewModel.dobakValue(
                        money.toLong()
                    )
                }
            ) {
                Text(text = "확인")
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            MoneyCount(money = viewModel.leftMoney)
        }
    }
    LackAlert(viewModel = viewModel)
}
