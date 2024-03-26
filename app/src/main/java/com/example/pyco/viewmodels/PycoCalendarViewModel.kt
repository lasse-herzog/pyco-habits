package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PycoCalendarViewModel @Inject constructor() : ViewModel() {
    val sampleData: HabitWeekData

    init {
        val todayLocalDate = LocalDate.now()
        val dayOfWeek = todayLocalDate.dayOfWeek
        val firstDateOfWeek = todayLocalDate.minusDays(dayOfWeek.value.toLong() - 1)
        val randomArray = LongArray(11) { ((it + 1) * 5).toLong() }

        sampleData = HabitWeekData(
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
    }
}

data class HabitWeekData(
    val firstDayOfWeek: LocalDate,
    val habitDays: List<HabitDayData>
)

data class HabitDayData(
    val habitTimeStamps: List<HabitTimeStampData>
)

data class HabitTimeStampData(
    val timeStamp: LocalDateTime,
    val habitId: Int
)