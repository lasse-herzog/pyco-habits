package com.example.pyco.views

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.viewmodels.HabitWithName
import com.example.pyco.viewmodels.HomeUIState
import com.example.pyco.viewmodels.PycoHomeViewModel
import com.example.pyco.views.ui.theme.PycoTheme

@Composable
fun PycoHomeScreen(
    viewModel: PycoHomeViewModel = hiltViewModel(),
) {
    val pendingHabitsListState = rememberLazyListState()
    PycoHomeContent(
        viewModel.uiState.collectAsState().value,
        pendingHabitsListState,
        onHabitAccepted = viewModel::setHabitPracticed,
        onHabitDismissed = viewModel::setHabitNotPracticed
    )
}

@Composable
private fun PycoHomeContent(
    pycoHomeUIState: HomeUIState,
    pendingHabitsListState: LazyListState,
    onHabitAccepted: (HabitWithName) -> Unit,
    onHabitDismissed: (HabitWithName) -> Unit
) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 8.dp)
    ) {
        QuoteCard(pycoHomeUIState.quote, modifier = Modifier.weight(19F))

        PendingHabitsList(
            habits = pycoHomeUIState.pendingHabits,
            pendingHabitsListState = pendingHabitsListState,
            modifier = Modifier.weight(31F),
            onHabitAccepted = onHabitAccepted,
            onHabitDismissed = onHabitDismissed
        )
    }
}

@Composable
fun QuoteCard(quote: String, modifier: Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "\u201C$quote\u201D",
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(all = 12.dp),
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun PendingHabitsList(
    habits: List<HabitWithName>,
    pendingHabitsListState: LazyListState,
    modifier: Modifier,
    onHabitAccepted: (HabitWithName) -> Unit,
    onHabitDismissed: (HabitWithName) -> Unit
) {
    LazyColumn(
        state = pendingHabitsListState, modifier = modifier
    ) {
        items(items = habits, key = { it.habitId }) { habit ->
            PendingHabitsListItem(
                habit = habit,
                isNext = habits.indexOf(habit) == 0,
                onHabitAccepted = onHabitAccepted,
                onHabitDismissed = onHabitDismissed
            )
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendingHabitsListItem(
    habit: HabitWithName,
    isNext: Boolean,
    onHabitAccepted: (HabitWithName) -> Unit,
    onHabitDismissed: (HabitWithName) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = {
        when (it) {
            SwipeToDismissBoxValue.StartToEnd -> onHabitAccepted(habit)
            SwipeToDismissBoxValue.EndToStart -> onHabitDismissed(habit)
            SwipeToDismissBoxValue.Settled -> {}
        }
        true
    })

    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier.padding(top = 8.dp),
        backgroundContent = {
            val direction = dismissState.dismissDirection
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.onBackground
                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primary
                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error
                }, label = "swipeToDismiss"
            )
            val scale by animateFloatAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 1.5f else 1.75f,
                label = "swipeToDismiss"
            )

            val alignment = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                SwipeToDismissBoxValue.Settled -> Alignment.Center
            }

            val icon = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Icons.Filled.Check
                SwipeToDismissBoxValue.EndToStart -> Icons.Filled.Clear
                SwipeToDismissBoxValue.Settled -> null
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                icon?.let { DismissIcon(icon = it, color = color, Modifier.scale(scale)) }
            }
        }) {
        Card {
            ListItem(colors = ListItemDefaults.colors(
                containerColor = if (isNext) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surfaceContainer
                }
            ), headlineContent = {
                Text(habit.name)
            })
        }
    }
}

@Composable
fun DismissIcon(icon: ImageVector, color: Color, modifier: Modifier) {
    Icon(
        icon, "", modifier, tint = color
    )
}

@Preview(
    name = "Light Mode", device = Devices.PIXEL_7_PRO, showBackground = true, showSystemUi = true
)
@Preview(
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_7_PRO,
    showBackground = true,
    showSystemUi = true
)
@Composable
fun PycoHomeScreenPreview() {
    PycoTheme {
        PycoHomeContent(pycoHomeUIState = HomeUIState(
            pendingHabits = listOf(
                HabitWithName(habitId = 0, "Test"),
                HabitWithName(habitId = 1, "Test"),
                HabitWithName(habitId = 2, "Test"),
                HabitWithName(habitId = 3, "Test"),
            )
        ),
            pendingHabitsListState = rememberLazyListState(),
            onHabitAccepted = {},
            onHabitDismissed = {})
    }
}
