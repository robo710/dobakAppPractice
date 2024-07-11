package com.sonchan.dobakapppractice.presentation.rank

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RankScreen(viewModel: RankViewModel) {
    val rankings by viewModel.rankings.observeAsState()
    Log.d("로그", " rankings: ${rankings}")

    // rankings가 업데이트될 때마다 UI를 자동으로 업데이트합니다.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopCenter)
        ) {
            items(rankings ?: emptyList()) { ranking ->
                Text(text = "${ranking.rank}. ${ranking.username}: ${ranking.score}")
            }
        }

        Button(
            onClick = { viewModel.updateRankings() },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Text("랭킹 업데이트")
        }
    }
}
