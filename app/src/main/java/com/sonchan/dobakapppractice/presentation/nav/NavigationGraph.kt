package com.sonchan.dobakapppractice.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sonchan.dobakapppractice.presentation.main.MainScreen
import com.sonchan.dobakapppractice.presentation.main.MainViewModel
import com.sonchan.dobakapppractice.presentation.mine.MineScreen
import com.sonchan.dobakapppractice.presentation.mine.MineViewModel
import com.sonchan.dobakapppractice.presentation.rank.RankScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Main.screenRoute) {
//        composable(BottomNavItem.Main.screenRoute) {
//            MainScreen(viewModel = MainViewModel(null))
//        }
        composable(BottomNavItem.Mine.screenRoute) {
            MineScreen(viewModel = MineViewModel())
        }
        composable(BottomNavItem.Rank.screenRoute) {
            RankScreen()
        }
    }
}