package com.sonchan.dobakapppractice.presentation.mine

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sonchan.dobakapppractice.presentation.dobak.DobakViewModel
import com.sonchan.dobakapppractice.presentation.dobak.MoneyCount
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MineScreen(viewModel: DobakViewModel) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("ButtonPrefs", Context.MODE_PRIVATE)
    var isButtonEnabled by remember { mutableStateOf(true) }
    var remainingTime by remember { mutableStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()
    val disableTime = sharedPreferences.getLong("disableTime", 0L)

    // 현재 시간과 저장된 시간을 비교하여 버튼 상태를 결정
    LaunchedEffect(disableTime) {
        val currentTime = System.currentTimeMillis()
        if (disableTime > currentTime) {
            isButtonEnabled = false
            coroutineScope.launch {
                while (System.currentTimeMillis() < disableTime) {
                    remainingTime = (disableTime - System.currentTimeMillis()) / 1000
                    delay(1000)
                }
                isButtonEnabled = true
                remainingTime = 0
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    if (isButtonEnabled) {
                        isButtonEnabled = false
                        val currentTime = System.currentTimeMillis()
                        val enableTime = currentTime + 3000 // 3초 후
                        sharedPreferences.edit().putLong("disableTime", enableTime).apply()

                        coroutineScope.launch {
                            while (System.currentTimeMillis() < enableTime) {
                                remainingTime = (enableTime - System.currentTimeMillis()) / 1000
                                delay(1000)
                            }
                            isButtonEnabled = true
                            remainingTime = 0
                        }
                        viewModel.addMoney()
                    }
                },
                enabled = isButtonEnabled,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "확인")
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (!isButtonEnabled) {
                Text(
                    text = "남은 시간: ${remainingTime}초",
                    modifier = Modifier.padding(bottom = 160.dp)
                )
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
