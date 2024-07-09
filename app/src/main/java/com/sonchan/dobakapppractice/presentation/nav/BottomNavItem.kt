package com.sonchan.dobakapppractice.presentation.nav

import com.sonchan.dobakapppractice.R

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
) {
    object Mine : BottomNavItem(R.string.text_mine, R.drawable.mine, MINE)
    object Main : BottomNavItem(R.string.text_main, R.drawable.main, MAIN)
    object Rank : BottomNavItem(R.string.text_rank, R.drawable.rank, RANK)
}