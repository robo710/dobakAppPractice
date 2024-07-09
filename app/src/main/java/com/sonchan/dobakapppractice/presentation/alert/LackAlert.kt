package com.sonchan.dobakapppractice.presentation.alert

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LackAlertPreview(){
    LackAlert()
}

@Composable
fun LackAlert() {
    val showDialog = remember { mutableStateOf(false) }

    Button(onClick = { showDialog.value = true }) {
        Text("Show Alert")
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "경고") },
            text = { Text(text = "작업을 진행하시겠습니까?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        // 확인 동작
                    }) {
                    Text("확인")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        // 취소 동작
                    }) {
                    Text("취소")
                }
            }
        )
    }
}
