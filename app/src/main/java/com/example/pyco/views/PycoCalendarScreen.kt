package com.example.pyco.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pyco.viewmodels.HabitDayData
import com.example.pyco.viewmodels.HabitTimeStampData
import com.example.pyco.viewmodels.HabitWeekData
import com.example.pyco.viewmodels.PycoCalendarViewModel
import com.example.pyco.views.ui.theme.PycoTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun PycoCalendarScreen(pycoCalendarViewModel: PycoCalendarViewModel = hiltViewModel()) {
    PycoCalendarContent(pycoCalendarViewModel.uiState.collectAsState().value.habitWeekData)
}

class HabitTimeStampParentData(
    val offset: Float
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@HabitTimeStampParentData
}

@Composable
fun Calendar(
    hoursLabel: @Composable () -> Unit,
    habitTimestampItemCounts: List<Int>,
    habitTimestamp: @Composable() (HabitTimeStampScope.(index: Int) -> Unit),
    modifier: Modifier,
) {
    val gridColor = MaterialTheme.colorScheme.onBackground
    val habitTimestamps =
        @Composable { repeat(habitTimestampItemCounts.sum()) { HabitTimeStampScope.habitTimestamp(it) } }

    Layout(
        contents = listOf(hoursLabel, habitTimestamps), modifier = modifier.drawWithCache {
            val brush = Brush.linearGradient(
                colors = listOf(
                    gridColor, gridColor
                )
            )

            onDrawBehind {
                for (x in 1..7) {
                    drawLine(
                        brush = brush,
                        start = Offset(size.width / 8 * x, -10F),
                        end = Offset(size.width / 8 * x, size.height)
                    )

                }
                for (y in 1..23) {
                    drawLine(
                        brush = brush,
                        start = Offset(0F, size.height / 24 * y),
                        end = Offset(size.width, size.height / 24 * y)
                    )

                }
            }
        }
    ) { (hoursLabelMeasurable, habitTimestampMeasurables), constraints ->
        require(hoursLabelMeasurable.size == 1)

        data class HabitTimestampLayoutData(
            val isRight: Boolean,
            val placeable: Placeable
        )

        var totalIndex = 0
        val habitTimestampsLayoutData = habitTimestampItemCounts.map {
            val data = habitTimestampMeasurables.subList(totalIndex, totalIndex + it)
            totalIndex += it
            data
        }

        val totalWidth = constraints.maxWidth
        val columnWidth = totalWidth / 8

        val hoursLabelPlaceable = hoursLabelMeasurable.first()
            .measure(constraints.copy(minWidth = columnWidth, maxWidth = columnWidth))

        val totalHeight = hoursLabelPlaceable.height

        val habitTimestampPlaceables = habitTimestampsLayoutData.map { habitTimestampMeasurables ->
            var isShort = false
            var isNextShort = false
            var isRight = false
            habitTimestampMeasurables.mapIndexed { index, measurable ->
                var width = (columnWidth * 0.9).roundToInt()
                if (index < habitTimestampMeasurables.size - 1) {
                    val habitTimestampParentData = measurable.parentData as HabitTimeStampParentData
                    val nextHabitTimestampParentData =
                        habitTimestampMeasurables[index + 1].parentData as HabitTimeStampParentData
                    val distance =
                        nextHabitTimestampParentData.offset - habitTimestampParentData.offset

                    isShort = isNextShort || distance < 1F / 24 / 5
                    isNextShort = distance < 1F / 24 / 5

                    if (isShort) {
                        width = columnWidth / 2
                    }
                }

                val habitTimestampLayoutData = HabitTimestampLayoutData(
                    isRight = isShort && isRight,
                    placeable = measurable.measure(
                        constraints.copy(
                            minWidth = width,
                            maxWidth = width
                        )
                    )
                )

                isRight = if (isShort) !isRight else false

                habitTimestampLayoutData
            }
        }


        layout(totalWidth, totalHeight) {
            val xPosition = hoursLabelPlaceable.width
            val yPosition = 0

            hoursLabelPlaceable.place(0, 0)

            habitTimestampPlaceables.forEachIndexed { index, item ->
                item.forEach { layoutData ->
                    val habitTimestampParentData =
                        layoutData.placeable.parentData as HabitTimeStampParentData
                    val habitTimestampOffset =
                        (habitTimestampParentData.offset * totalHeight).roundToInt()

                    layoutData.placeable.place(
                        xPosition + index * columnWidth + if (layoutData.isRight) columnWidth / 2 else 0,
                        yPosition + habitTimestampOffset
                    )
                }
            }

        }
    }
}

@Composable
fun HabitTimeStamp(timeStamp: HabitTimeStampData, modifier: Modifier) {
    InputChip(
        selected = false,
        onClick = { },
        label = { },
        modifier = modifier
            .height(14.dp)
            .padding(horizontal = 1.dp),
        colors = InputChipDefaults.inputChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        border = null
    )
}

@Composable
fun PycoCalendarContent(sampleData: HabitWeekData) {
    val scrollState = rememberScrollState()
    val habitTimestamps: List<HabitTimeStampData> =
        sampleData.habitDays.flatMap { it.habitTimeStamps }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsTopHeight(WindowInsets.systemBars)
                .background(MaterialTheme.colorScheme.surfaceContainer)
        )
        WeekRow(sampleData.firstDayOfWeek.dayOfMonth - 1, modifier = Modifier)
        Calendar(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            hoursLabel = {
                DayColumn(modifier = Modifier)
            },
            habitTimestampItemCounts = sampleData.habitDays.map {
                it.habitTimeStamps.size
            },
            habitTimestamp = { index ->
                val data: HabitTimeStampData = habitTimestamps[index]
                HabitTimeStamp(
                    timeStamp = data, modifier = Modifier.habitTimeStamp(timeStamp = data.timeStamp)
                )
            }
        )

        /*
        LazyColumn {
            item {
                Row {
                    DayColumn(
                        modifier = Modifier
                            .weight(1F)
                    )
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = (64 * 24).dp)
                            .weight(7f)
                    ) {
                        for (x in 0..6) {
                            drawLine(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.Gray, Color.Gray
                                    )
                                ),
                                start = Offset(size.width / 7 * x, 0F),
                                end = Offset(size.width / 7 * x, size.height)
                            )

                        }
                        for (y in 1..23) {
                            drawLine(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.Gray, Color.Gray
                                    )
                                ),
                                start = Offset(0F, size.height / 24 * y),
                                end = Offset(size.width, size.height / 24 * y)
                            )

                        }
                    }
                }
            }
        }*/
    }
}

private const val hoursADay = 24

@LayoutScopeMarker
@Immutable
object HabitTimeStampScope {
    @Stable
    fun Modifier.habitTimeStamp(
        timeStamp: LocalDateTime,
    ): Modifier {
        val offset = (((timeStamp.hour * 60 + timeStamp.minute) / 5) * 5) / (hoursADay * 60F)

        return then(
            HabitTimeStampParentData(
                offset = offset
            )
        )
    }
}/*
sampleData.forEach {
    LazyColumn(
        modifier = Modifier.weight(1F),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var left = true
        itemsIndexed(it) { index, time ->

            val style = MaterialTheme.typography.labelMedium.lineHeight
            val height = with(LocalDensity.current) {
                style.toDp()
            }
            val minDifference = when (index) {
                0 -> Duration.between(
                    startOfDay,
                    time
                )
                else -> Duration.between(it[index - 1], time)
            }.toMinutes().toInt()
            val singleItem =
                if (index < it.size - 1) height.value > minDifference else true

            if (true) {
                left = true
                Spacer(modifier = Modifier.height((minDifference - height.value).dp))
                InputChip(
                    selected = false,
                    onClick = { },
                    label = {
                        Text(
                            "Test", style = MaterialTheme.typography.labelSmall
                        )
                    },
                    modifier = Modifier
                        .height(height = height)
                        .zIndex(10F)
                )
            } else {/*
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (left) Arrangement.Absolute.Left else Arrangement.Absolute.Right) {
                    InputChip(
                        selected = false,
                        onClick = { },
                        label = {
                            Text(
                                "Test", style = MaterialTheme.typography.labelSmall
                            )
                        },
                        modifier = Modifier
                            .absoluteOffset(y = (time.minute).dp)
                            .height(height = height)
                            .width(12.dp)
                    )
                }
                left = !left*/
            }
        }

    }


}/*
itemsIndexed(hours) { index, item ->
    HorizontalDivider(thickness = Dp.Hairline)
    Row(
        modifier = Modifier.height(64.dp)
        //.border(1.dp, MaterialTheme.colorScheme.surfaceContainer)
    ) {
        VerticalDivider(thickness = 2.dp)
        Column(modifier = Modifier.weight(1F)) {
            val style = MaterialTheme.typography.labelMedium.lineHeight
            val offset = with(LocalDensity.current) {
                style.toDp() / 2
            }
            Text(
                text = item.toString(),
                modifier = Modifier
                    .absoluteOffset(y = -offset)
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(horizontal = 4.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
        for (i in 0..6) {
            val times = sampleData[i]
            Column
        }
    }
}
}
}
}
val itemsList = (0..167).toList()

val itemModifier = Modifier
.border(1.dp, Color.Blue)
.width(64.dp)
.height(64.dp)

LazyHorizontalGrid(
rows = GridCells.FixedSize(64.dp),
) {
items(itemsList) {
    Box(
        modifier = Modifier
            .border(1.dp, Color.Blue)
            .width(64.dp)
            .requiredHeight(128.dp)
    )
}
}*/
}
}
}*/

@Composable
private fun WeekRow(firstDateOfWeek: Int, modifier: Modifier) {
    Row(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Spacer(modifier = Modifier.weight(1F))
        DayOfWeek.entries.forEach {
            VerticalDivider(
                Modifier
                    .height(20.dp)
                    .align(Alignment.Bottom),
                thickness = Dp.Hairline,
                color = MaterialTheme.colorScheme.onSurface
            )
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = it.name.first().toString(),
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = (firstDateOfWeek + it.value).toString(),
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun DayColumn(modifier: Modifier) {
    val startOfDay = LocalDate.now().atStartOfDay()
    val hours = List(23) {
        startOfDay.plusHours(it.toLong() + 1).format(DateTimeFormatter.ofPattern("HH:mm"))
    }

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(60.dp))

        hours.forEach {
            val style = MaterialTheme.typography.labelMedium.lineHeight
            val offset = with(LocalDensity.current) {
                style.toDp() / 2
            }

            Box(
                modifier = Modifier.height(60.dp)
            ) {
                Text(
                    text = it.toString(),
                    modifier = Modifier
                        .absoluteOffset(y = -offset)
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode", device = Devices.PIXEL_7_PRO, showBackground = true, showSystemUi = true
)
@Preview(
    name = "Dark Mode",
    device = Devices.PIXEL_7_PRO,
    showBackground = true,
    showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun PycoCalendarScreenPreview() {
    val todayLocalDate = LocalDate.now()
    val dayOfWeek = todayLocalDate.dayOfWeek
    val firstDateOfWeek = todayLocalDate.minusDays(dayOfWeek.value.toLong() - 1)
    val randomArray = LongArray(11) { ((it + 1) * 5).toLong() }

    val sampleData = HabitWeekData(
        firstDayOfWeek = firstDateOfWeek,
        habitDays = List(7) {
            HabitDayData(
                habitTimeStamps = buildList {
                    val day = firstDateOfWeek.plusDays(it.toLong())
                    var time = day.atStartOfDay()
                    while (time < day.atTime(22, 59)) {
                        val newTime = time.plusMinutes(randomArray.random())
                        time = newTime
                        add(
                            HabitTimeStampData(
                                timeStamp = newTime,
                                habitId = 0
                            )
                        )
                    }
                }
            )
        }
    )

    PycoTheme {
        PycoCalendarContent(sampleData)
    }
}