package com.example.pyco.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.entities.CompleteHabit
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.repositories.HabitsRepository
import com.example.pyco.data.repositories.QuotesRepository
import com.example.pyco.helper.StreakHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random
import kotlin.reflect.typeOf

data class StreakUiState(
    val activeHabits: List<CompleteHabit> = emptyList(),
    val score: Int = 0,
    val quote: String = ""
)

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val quoteRepository: QuotesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StreakUiState())
    val uiState: StateFlow<StreakUiState> = _uiState

    init {
        observeHabits()
    }

    private fun observeHabits() {

        viewModelScope.launch {
            val habits: List<CompleteHabit> = emptyList()
            habitsRepository
                .getCompleteHabits()
                .collect { habits ->
                    var score = 0
                    val activeHabits =
                        habits.filter { x -> x.habitBlueprint.isActive && (x.habit.end == null || x.habit.end >= LocalDate.now()) }

                    for (habit in habits) {
                        score += StreakHelper.CalculateStreak(habit)
                    }

                    _uiState.value = StreakUiState(activeHabits, score, loadQuote(activeHabits) ?: "")
                }
        }
    }

    private suspend fun loadQuote(activeHabits: List<CompleteHabit>): String? {
        val randomCategory = activeHabits.randomOrNull()?.categories?.randomOrNull() ?: return null
        return quoteRepository.getQuotesByCategory(randomCategory).randomOrNull()?.text
    }
}