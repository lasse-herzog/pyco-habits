package com.example.pyco.views.streak

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.CompleteHabit
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitDate
import com.example.pyco.viewmodels.PycoHomeViewModel
import com.example.pyco.viewmodels.StreakViewModel
import com.example.pyco.views.HabitItem
import java.time.LocalDate

@Composable
fun StreakView(
    viewModel: StreakViewModel = hiltViewModel()
) {
    Column {
        Text("Score:")
        Text(viewModel.score.toString())
        StreakProgress()
//        Quote()
        HabitStreaks(viewModel.activeHabits)
    }
}

@Composable
fun HabitStreaks(habits: List<CompleteHabit>) {
    LazyColumn {
        items(habits) { habit ->
            HabitStreakView(habit = habit)
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
    val habits: List<CompleteHabit> = mutableListOf(
        CompleteHabit(
            Habit(0, 0, LocalDate.now(), LocalDate.now().plusDays(5), 5),
            HabitBlueprint(0, "What a nice Habit!", "It is really nice!"),
            listOf(Category(0, "Testcategory")),
            listOf(HabitDate(0, LocalDate.now(), true))
        ),
        CompleteHabit(
            Habit(0, 0, LocalDate.now(), LocalDate.now().plusDays(5), 5),
            HabitBlueprint(0, "What a nice Habit!", "It is really nice!"),
            listOf(Category(0, "Testcategory")),
            listOf(HabitDate(0, LocalDate.now(), true))
        ),
        CompleteHabit(
            Habit(0, 0, LocalDate.now(), LocalDate.now().plusDays(5), 5),
            HabitBlueprint(0, "What a nice Habit!", "It is really nice!"),
            listOf(Category(0, "Testcategory")),
            listOf(HabitDate(0, LocalDate.now(), true))
        )
    )

    HabitStreaks(habits)
}