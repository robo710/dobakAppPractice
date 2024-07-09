package com.sonchan.dobakapppractice.presentation.main

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MoneyCountPreview(){
    MoneyCount(money = 1000000000000000000)
}

@Composable
fun MoneyCount(money: Long, modifier: Modifier = Modifier) {
    val formatter: NumberFormat = DecimalFormat("#,###")
    val formattedMoney = formatter.format(money)

    Card(
        modifier = modifier
    ) {
        Text(
            text = ("잔여 돈 : $formattedMoney"),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}