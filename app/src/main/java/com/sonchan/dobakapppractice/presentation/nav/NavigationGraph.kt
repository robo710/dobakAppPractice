package com.sonchan.dobakapppractice.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sonchan.dobakapppractice.presentation.login.GoogleAuthUiClientProvider.googleAuthUiClient
import com.sonchan.dobakapppractice.presentation.main.DobakScreen
import com.sonchan.dobakapppractice.presentation.main.MainScreen
import com.sonchan.dobakapppractice.presentation.main.DobakViewModel
import com.sonchan.dobakapppractice.presentation.mine.MineScreen
import com.sonchan.dobakapppractice.presentation.mine.MineViewModel
import com.sonchan.dobakapppractice.presentation.rank.RankScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Dobak.screenRoute) {
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
            MineScreen(viewModel = MineViewModel())
        }
        composable(BottomNavItem.Rank.screenRoute) {
            RankScreen()
        }
        composable("profile") {
            val navController = rememberNavController()
            setupNavGraph(navController = navController, "profile")
        }
    }
}