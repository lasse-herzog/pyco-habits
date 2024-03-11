package com.example.pyco.views

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pyco.data.Habit
import com.example.pyco.viewmodels.PycoHomeViewModel
import com.example.pyco.views.ui.theme.PycoTheme

@Composable
fun DismissIcon(icon: ImageVector, color: Color, backgroundColor: Color) {
    Icon(
        icon,
        "",
        Modifier
            .background(backgroundColor, RoundedCornerShape(30))
            .size(40.dp),
        tint = color
    )
}

@Composable
fun QuoteCard() {
    Card {
        Text(
            text = " Your Mom is beautiful!",
            color = MaterialTheme.colorScheme.primary,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}

@Composable
fun PycoHome(
    habitsLazyListState: LazyListState,
    modifier: Modifier,
    viewModel: PycoHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.windowInsetsPadding(WindowInsets.statusBars)) {
        QuoteCard()

        val habits = uiState.habits

        HabitsList(habitsLazyListState, habits)
    }
}

@Composable
private fun HabitsList(
    habitsLazyListState: LazyListState,
    habits :List<Habit>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        state = habitsLazyListState
    ) {
        items(
            items = habits,
            key = { it.id }) { habit -> RemainingHabitsListItem(habit = habit) }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemainingHabitsListItem(habit: Habit) {
    val dismissState = rememberSwipeToDismissBoxState()
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.surface
                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.secondary
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                }, label = "dismiss habit"
            )

            val alignment = when (dismissState.targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                else -> Alignment.CenterStart
            }

            val icon = when (dismissState.targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> Icons.Filled.Check
                SwipeToDismissBoxValue.EndToStart -> Icons.Filled.Clear
                else -> Icons.Filled.Check
            }

            val backgroundColor = when (dismissState.targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.surfaceContainerHigh
                SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.surfaceContainerHigh
                else -> MaterialTheme.colorScheme.surface
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                DismissIcon(icon = icon, color = color, backgroundColor = backgroundColor)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
        ) {
            Card(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
                ListItem(
                    headlineContent = {
                        Text(habit.title)
                    },
                    supportingContent = { Text("Swipe me left or right!") }
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun HomePreview() {
    PycoTheme {
        HabitsList(habitsLazyListState = rememberLazyListState(), habits = listOf(Habit(title = "TEST")))
    }
}