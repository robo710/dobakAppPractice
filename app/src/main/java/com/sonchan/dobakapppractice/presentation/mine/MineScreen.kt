package com.sonchan.dobakapppractice.presentation.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun MineScreenPreview(){
    MineScreen(viewModel = MineViewModel())
}

@Composable
fun MineScreen(viewModel: MineViewModel){
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF)),
        Alignment.Center
    ){
        Button(
            onClick = {
                /*TODO*/
            }
        ) {
            Text(text = "확인")
        }
    }
}