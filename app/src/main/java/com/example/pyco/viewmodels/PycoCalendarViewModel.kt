package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.repositories.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

data class CalendarUIState(
    val habitWeekData: HabitWeekData = HabitWeekData(
        firstDayOfWeek = LocalDate.now(), habitDays = listOf()
    )
)

@HiltViewModel
class PycoCalendarViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUIState())
    val uiState: StateFlow<CalendarUIState> = _uiState
    val sampleData: HabitWeekData = createSampleData()

    init {
        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek.value
        val firstDateOfWeek = today.minusDays((dayOfWeek - 1).toLong())

        getWeekData(firstDayOfWeek = firstDateOfWeek)
    }

    private fun getWeekData(firstDayOfWeek: LocalDate) {
        viewModelScope.launch {
            val weekDays = List(7) { firstDayOfWeek.plusDays(it.toLong()) }

            combine(weekDays.map { habitsRepository.observeHabitDatesByDate(it) }) { days ->
                days.map { habitDates ->
                    HabitDayData(
                        habitDates
                            .filter { it.timestamp != null }
                            .map {
                                HabitTimeStampData(
                                    timeStamp = it.timestamp!!,
                                    habitId = it.habitId
                                )
                            })
                }
            }.collect {
                _uiState.value = CalendarUIState(HabitWeekData(firstDayOfWeek, it))
            }
        }
    }

    private fun createSampleData(): HabitWeekData {
        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek.value
        val firstDateOfWeek = today.minusDays((dayOfWeek - 1).toLong())
        val randomArray = LongArray(11) { ((it + 1) * 5).toLong() }

        return HabitWeekData(firstDayOfWeek = firstDateOfWeek, habitDays = List(7) {
            HabitDayData(habitTimeStamps = buildList {
                val day = firstDateOfWeek.plusDays(it.toLong())
                var time = day.atStartOfDay()
                while (time < day.atTime(22, 59)) {
                    val newTime = time.plusMinutes(randomArray.random())
                    time = newTime
                    add(
                        HabitTimeStampData(
                            timeStamp = newTime, habitId = 0
                        )
                    )
                }
            })
        })
    }
}


data class HabitWeekData(
    val firstDayOfWeek: LocalDate, val habitDays: List<HabitDayData>
)

data class HabitDayData(
    val habitTimeStamps: List<HabitTimeStampData>
)

data class HabitTimeStampData(
    val timeStamp: LocalDateTime, val habitId: Int
)