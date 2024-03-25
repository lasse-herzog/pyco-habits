package com.example.pyco.views.streak

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.R
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.CompleteHabit
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitDate
import com.example.pyco.helper.StreakHelper
import com.example.pyco.viewmodels.PycoHomeViewModel
import com.example.pyco.viewmodels.StreakViewModel
import com.example.pyco.views.HabitItem
import java.time.LocalDate

@Composable
fun StreakView(
    viewModel: StreakViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    StreakView(uiState.activeHabits)
}

@Composable
fun StreakView(habits: List<CompleteHabit>) {
    Column {
//        Quote()
        Box(modifier = Modifier.weight(1f)) {
            LevelBanner()
        }
        Box(modifier = Modifier.weight(1f)) {
            StreakProgress()
        }
        Box(modifier = Modifier.weight(1f)) {
            HabitStreaks(habits)
        }
    }
}

@Composable
fun LevelBanner() {
//    Image(
//        painter = painterResource(id = R.drawable.streak_mountains),
//        contentDescription = "Colored vector graphic",
////        colorFilter = ColorFilter.tint(color),
//        modifier = Modifier
////            .padding(start.dp, top.dp, end.dp, bottom.dp)
//            .fillMaxSize(1f),
//        contentScale = ContentScale.Crop
////            .graphicsLayer(
////                rotationZ = rotation,
////                transformOrigin = TransformOrigin(0.5f, 1f)
////            )
//    )
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
fun StreakViewPreview() {
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

    StreakView(habits)
}