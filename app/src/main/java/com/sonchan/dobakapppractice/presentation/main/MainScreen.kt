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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.sonchan.dobakapppractice.presentation.alert.LackAlert
import com.sonchan.dobakapppractice.ui.theme.DobakAppPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this) // Firebase 초기화
        setContent {
            DobakAppPracticeTheme {
                MainScreen(MainViewModel())
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    DobakAppPracticeTheme {
        MainScreen(MainViewModel())
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    var money by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
    ) {
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
                    viewModel.updateValue(money.toLong())
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
