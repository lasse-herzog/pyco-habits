package com.example.pyco.views.navigation

import androidx.annotation.StringRes
import com.example.pyco.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    data object Streak : Screen("streak", R.string.streak)
    data object Home : Screen("home", R.string.home)
    data object Habits : Screen("habits", R.string.habits)
}
