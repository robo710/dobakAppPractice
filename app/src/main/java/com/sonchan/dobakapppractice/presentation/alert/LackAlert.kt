package com.sonchan.dobakapppractice.presentation.alert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.sonchan.dobakapppractice.presentation.main.DobakViewModel

@Composable
fun LackAlert(viewModel: DobakViewModel) {
    val showLackAlert by viewModel.showLackAlert.observeAsState(false)

    if (showLackAlert) {
        CustomAlertDialog(title = "돈이 없습니다.", description = "돈을 더 모으세요.",
            onClickConfirm = { viewModel.dismissLackAlert() })
    }
}
