package com.sonchan.dobakapppractice.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {
    var check: Int by mutableStateOf(0)
}