package com.sonchan.dobakapppractice.presentation.alert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.sonchan.dobakapppractice.presentation.dobak.DobakViewModel

@Composable
fun LackAlert(viewModel: DobakViewModel) {
    val showLackAlert by viewModel.showLackAlert.observeAsState(false)

    if (showLackAlert) {
        CustomAlertDialog(title = "돈이 없습니다.", description = "돈을 더 모으세요.",
            onClickConfirm = { viewModel.dismissAlert() })
    }
}

@Composable
fun SuccessAlert(viewModel: DobakViewModel) {
    val showSuccessAlert by viewModel.showSuccessAlert.observeAsState(false)

    if (showSuccessAlert) {
        CustomAlertDialog(title = "성공하였습니다.", description = "축하드립니다.",
            onClickConfirm = { viewModel.dismissAlert() })
    }
}

@Composable
fun FailAlert(viewModel: DobakViewModel) {
    val showFailAlert by viewModel.showFailAlert.observeAsState(false)

    if (showFailAlert) {
        CustomAlertDialog(title = "실패하였습니다.", description = "돈을 모아 다시 시도하세요.",
            onClickConfirm = { viewModel.dismissAlert() })
    }
}