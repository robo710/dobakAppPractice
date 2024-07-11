package com.sonchan.dobakapppractice.presentation.mine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sonchan.dobakapppractice.presentation.dobak.DobakViewModel
import com.sonchan.dobakapppractice.presentation.dobak.MoneyCount

@Composable
fun MineScreen(viewModel: DobakViewModel){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFFFFFFF))
        ) {
        Box(
            modifier = Modifier,
            Alignment.Center
        ) {
            Button(
                onClick = {
                    viewModel.addMoney()
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
}