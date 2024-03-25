package com.example.pyco.views.streak

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
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
    StreakView(uiState.activeHabits, uiState.score)
}

@Composable
fun StreakView(habits: List<CompleteHabit>, score: Int) {
    val level = StreakHelper.CalculateLevel(score)
    Column {
        LevelBanner(score, level)
        Box(modifier = Modifier.weight(1f)) {
            StreakProgress(level)
        }
//        Quote()
        Box(modifier = Modifier.weight(1f)) {
            HabitStreaks(habits)
        }
    }
}

@Composable
fun LevelBanner(score: Int, levelInfo: Triple<Int, Int, Int>) {
    val resourceId =
        when (levelInfo.first) {
            2 -> R.drawable.streak_level_2
            3 -> R.drawable.streak_level_3
            4 -> R.drawable.streak_level_4
            5 -> R.drawable.streak_level_5
            else -> R.drawable.streak_level_1
        }

    Row(
        Modifier.background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = "Colored vector graphic",
            modifier = Modifier.padding(5.dp),
            contentScale = ContentScale.Crop
        )
        Box(Modifier.weight(1f))
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(score.toString(), fontSize = TextUnit(8f, TextUnitType.Em))
            StaticFlame()
        }
    }
}

@Composable
fun HabitStreaks(habits: List<CompleteHabit>) {
    val sortedHabits = habits.sortedByDescending { x -> x.dates.count() }
    LazyColumn {
        items(sortedHabits) { habit ->
            HabitStreakView(habit = habit)
        }
    }
}

//@Composable
//fun Quote() {
//
//}

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

    StreakView(habits, 1000)
}