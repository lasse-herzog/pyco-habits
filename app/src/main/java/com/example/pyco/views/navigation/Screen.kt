package com.example.pyco.views.navigation

import androidx.annotation.StringRes
import com.example.pyco.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    data object Calendar : Screen("calendar", R.string.calendar)
    data object Home : Screen("home", R.string.home)
    data object Habits : Screen("habits", R.string.habits)
    data object HabitDetails : Screen("habitDetails", R.string.habitDetails)
}
