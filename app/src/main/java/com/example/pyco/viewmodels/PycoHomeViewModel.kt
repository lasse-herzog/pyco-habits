package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.repositories.HabitsRepository
import com.example.pyco.data.repositories.QuotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUIState(
    val pendingHabits: List<HabitWithName> = emptyList(),
    val quote: String = ""
)

@HiltViewModel
class PycoHomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val quotesRepository: QuotesRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        observePendingHabits()
    }

    private fun getQuote(habit: Habit?) {
        viewModelScope.launch {
            val quote =
                (if (habit != null) quotesRepository.getQuote(habit) else quotesRepository.getRandomQuote()).text
            _uiState.update {
                it.copy(
                    quote = quote
                )
            }
        }
    }

    private fun observePendingHabits() {
        viewModelScope.launch {
            habitsRepository.observePendingHabitAndHabitBlueprints()
                .collect { habits ->
                    if (_uiState.value.quote.isEmpty()) {
                        getQuote(habits.firstOrNull()?.habit)
                    }

                    _uiState.update { homeUIState ->
                        homeUIState.copy(
                            pendingHabits = habits.map {
                                HabitWithName(
                                    habitId = it.habit.habitId,
                                    name = it.habitBlueprint.name
                                )
                            }
                        )
                    }
                }
        }
    }

    fun setHabitPracticed(habit: HabitWithName) {
        viewModelScope.launch {
            habitsRepository.setHabitPracticed(habit.habitId)

            removeFromPendingHabits(habit)
        }
    }

    fun setHabitNotPracticed(habit: HabitWithName) {
        viewModelScope.launch {
            habitsRepository.setHabitNotPracticed(habit.habitId)

            removeFromPendingHabits(habit)
        }
    }

    private fun removeFromPendingHabits(habit: HabitWithName) {
        _uiState.update { homeUIState ->
            homeUIState.copy(
                pendingHabits = homeUIState.pendingHabits.minus(
                    habit
                )
            )
        }
    }
}

data class HabitWithName(val habitId: Int, val name: String)