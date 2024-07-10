package com.sonchan.dobakapppractice.presentation.nav

import com.sonchan.dobakapppractice.R

enum class BottomNavItem(val title: Int, val icon: Int, val screenRoute: String) {
    Mine(R.string.text_mine, R.drawable.mine, MINE),
    Main(R.string.text_main, R.drawable.main, MAIN),
    Rank(R.string.text_rank, R.drawable.rank, RANK)
}