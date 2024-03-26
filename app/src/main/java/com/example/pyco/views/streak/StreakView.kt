package com.example.pyco.views.streak

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.R
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.CompleteHabit
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitDate
import com.example.pyco.helper.StreakHelper
import com.example.pyco.viewmodels.StreakViewModel
import java.time.LocalDate

@Composable
fun StreakView(
    viewModel: StreakViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    StreakView(uiState.activeHabits, uiState.score, uiState.quote)
}

@Composable
fun StreakView(habits: List<CompleteHabit>, score: Int, quote: String) {
    val level = StreakHelper.CalculateLevel(score)
    Column {
        LevelBanner(score, level)

        Box(Modifier.weight(1f)) {
            StreakProgress(level)
        }

        Quote(quote)

        Box(modifier = Modifier.weight(2f)) {
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

    Row(verticalAlignment = Alignment.CenterVertically) {
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

fun Modifier.topShadow(shadowHeight: Float = 10f, color: Color = Color.Black): Modifier = this.then(
    Modifier
        .drawWithContent {
            drawContent()
            drawRect(
                brush = Brush.linearGradient(
                    colors = listOf(color.copy(alpha = 0.5f), Color.Transparent),
                    start = Offset.Zero,
                    end = Offset(0f, shadowHeight),
                    tileMode = TileMode.Clamp
                )
            )
        }
)

@Composable
fun HabitStreaks(habits: List<CompleteHabit>) {
    val sortedHabits = habits.sortedByDescending { x -> x.dates.count() }
    val listState = rememberLazyListState()
    var showShadow by remember { mutableStateOf(false) }

    // Check the scroll state and update showShadow accordingly
    LaunchedEffect(listState.firstVisibleItemScrollOffset, listState.firstVisibleItemIndex) {
        showShadow =
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0
    }

    Box(
        modifier = Modifier
            .then(if (showShadow) Modifier.topShadow() else Modifier)
    ) {
        LazyColumn(state = listState) {
            items(sortedHabits) { habit ->
                HabitStreakView(habit = habit)
            }
        }
    }
}

@Composable
fun Quote(quote: String) {
    Row(Modifier.fillMaxWidth(1f)) {
        Box(modifier = Modifier.weight(1f))
        Text(
            text = quote,
            Modifier.padding(10.dp),
            textAlign = TextAlign.Center,
            fontStyle = FontStyle.Italic
        )
        Box(modifier = Modifier.weight(1f))
    }
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

    StreakView(habits, 1000, "blub")
}