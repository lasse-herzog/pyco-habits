package com.example.pyco.views.streak

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.viewmodels.PycoHomeViewModel
import com.example.pyco.views.HabitItem

@Composable
fun StreakView(
    modifier: Modifier,
    viewModel: PycoHomeViewModel = hiltViewModel()
) {
    Column {
        StreakProgress()
//        Quote()
        HabitStreaks()
    }
}

@Composable
fun HabitStreaks() {
    val habits: List<HabitAndHabitBlueprint> = mutableListOf()

    LazyColumn {
        items(habits) { habit ->
            HabitItem(habit = habit)
        }
    }
}

@Composable
fun Quote() {
    TODO("Not yet implemented")
}

@Preview
@Composable
fun HabitStreaksPreview() {
    HabitStreaks()
}