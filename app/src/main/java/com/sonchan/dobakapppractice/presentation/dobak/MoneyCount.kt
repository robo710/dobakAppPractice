package com.sonchan.dobakapppractice.presentation.dobak

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData

@Composable
fun MoneyCount(money: LiveData<Long>, modifier: Modifier = Modifier) {
    val currentMoney by money.observeAsState()
    val formatter: NumberFormat = DecimalFormat("#,###")
    val formattedMoney = formatter.format(currentMoney)

    Card(
        modifier = modifier
    ) {
        Text(
            text = ("잔여 돈 : $formattedMoney"),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(8.dp)
        )
    }
}
