package com.sonchan.dobakapppractice.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _shouldShowBottomNavigation = mutableStateOf(true)
    val shouldShowBottomNavigation: State<Boolean> = _shouldShowBottomNavigation

    fun setBottomNavigationVisibility(visible: Boolean) {
        _shouldShowBottomNavigation.value = visible
    }

    fun updateBottomNavigationVisibility(currentRoute: String?) {
        _shouldShowBottomNavigation.value = currentRoute != "login" && currentRoute != "profile"
    }
}
