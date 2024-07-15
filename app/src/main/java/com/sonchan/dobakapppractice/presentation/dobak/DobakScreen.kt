package com.sonchan.dobakapppractice.presentation.dobak

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
import coil.compose.AsyncImage
import com.sonchan.dobakapppractice.presentation.alert.FailAlert
import com.sonchan.dobakapppractice.presentation.alert.LackAlert
import com.sonchan.dobakapppractice.presentation.alert.SuccessAlert

@Composable
fun DobakScreen(
    onProfileClick: () -> Unit,
    viewModel: DobakViewModel
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
                    if (money != "") {
                        try {
                            val longMoney = money.toLong()
                            if (longMoney > 0) {
                                viewModel.dobak(
                                    longMoney
                                )
                                money = ""
                            }
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
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
        LackAlert(viewModel = viewModel)
        SuccessAlert(viewModel = viewModel)
        FailAlert(viewModel = viewModel)
    }
}