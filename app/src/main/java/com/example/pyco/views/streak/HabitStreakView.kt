package com.example.pyco.views.streak

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pyco.R
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitBlueprint
import java.time.LocalDate

@Composable
fun HabitStreakView(habit: HabitAndHabitBlueprint) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .border(
                width = 2.dp,
                color = colorScheme.outlineVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(
            containerColor = colorScheme.surfaceBright,
            contentColor = colorScheme.onSurface,
            disabledContainerColor = colorScheme.surfaceDim,
            disabledContentColor = colorScheme.onSurface
        )
//        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = habit.habitBlueprint.name)
                HabitStreakProgress(habit)
            }
            Box(modifier = Modifier.weight(1f)) {}
            HabitStreakMultiplier(habit)
        }
    }
}

@Composable
fun HabitStreakMultiplier(habit: HabitAndHabitBlueprint) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "1")
        Text(text = "x")

        Box {
            Image(
                painter = painterResource(id = R.drawable.streak_flame),
                contentDescription = "Flame",
                colorFilter = ColorFilter.tint(Color(255, 150, 0)),
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(2f),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.streak_flame),
                contentDescription = "Flame",
                colorFilter = ColorFilter.tint(Color(255, 150, 0)),
                modifier = Modifier
                    .fillMaxHeight(.8f)
                    .aspectRatio(2f)
                    .align(Alignment.Center)
                    .graphicsLayer(
                        rotationZ = -30f,
                        transformOrigin = TransformOrigin(0.5f, .8f)
                    ),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.streak_flame_inner),
                contentDescription = "Flame",
                colorFilter = ColorFilter.tint(Color(255, 199, 0)),
                modifier = Modifier
                    .fillMaxHeight(.6f)
                    .aspectRatio(2f)
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun HabitStreakProgress(habit: HabitAndHabitBlueprint) {
    Row(
        Modifier
            .height(IntrinsicSize.Min)
            .padding(top = 5.dp)
    ) {
        CustomProgressBar(progress = .5f)
        Text("3", modifier = Modifier.padding(start = 5.dp))
        Text("/3")
    }
}

@Composable
fun CustomProgressBar(progress: Float, modifier: Modifier = Modifier) {
    val barColor = colorScheme.surfaceDim
    val barBackgroundColor = colorScheme.primary

    Canvas(
        modifier = modifier
            .fillMaxWidth(.5f)
            .fillMaxHeight(1f)
    ) {
        val cornerRadius = CornerRadius(x = size.height / 2, y = size.height / 2)
        // Draw the background track
        drawRoundRect(
            color = barColor,
            size = Size(size.width, size.height),
            cornerRadius = cornerRadius
        )
        // Draw the progress part
        drawRoundRect(
            color = barBackgroundColor,
            size = Size(width = size.width * progress, size.height),
            cornerRadius = cornerRadius
        )
    }
}

@Preview
@Composable
fun HabitStreakViewPreview() {
    val habits: List<HabitAndHabitBlueprint> = mutableListOf(
        HabitAndHabitBlueprint(
            Habit(0, 0, LocalDate.now(), LocalDate.now().plusDays(5), 5),
            HabitBlueprint(0, "What a nice Habit!", "It is really nice!")
        ),
        HabitAndHabitBlueprint(
            Habit(0, 0, LocalDate.now(), LocalDate.now().plusDays(5), 5),
            HabitBlueprint(0, "What a nice Habit!", "It is really nice!")
        ),
        HabitAndHabitBlueprint(
            Habit(0, 0, LocalDate.now(), LocalDate.now().plusDays(5), 5),
            HabitBlueprint(0, "What a nice Habit!", "It is really nice!")
        )
    )

    LazyColumn {
        items(habits) { habit ->
            HabitStreakView(habit)
        }
    }

}