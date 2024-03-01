package com.example.pyco.views

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pyco.data.HabitSampleData
import com.example.pyco.views.ui.theme.PycoTheme

@Composable
fun HabitsOverviewScreen(habits: List<Habit>){
    LazyColumn {
        items(habits){ habit ->
            HabitItem(habit = habit)
        }
    }
}

@Preview
@Composable
fun PreviewHabitsOverviewScreen(){
    PycoTheme {
        HabitsOverviewScreen(habits = HabitSampleData.habitSample)
    }
}