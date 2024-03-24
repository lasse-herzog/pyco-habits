package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.repositories.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUIState(
    val pendingHabits: List<Habit> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PycoHomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState(isLoading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        observeHabits()
    }

    private fun observeHabits() {
        viewModelScope.launch {
            habitsRepository.observePendingHabits()
                .collect { habits ->
                    _uiState.value = HomeUIState(
                        pendingHabits = habits
                    )
                }
        }
    }

    fun setHabitPracticed(habit: Habit) {
        viewModelScope.launch {
            habitsRepository.setHabitPracticed(habit)

            removeFromPendingHabits(habit)
        }
    }

    fun setHabitNotPracticed(habit: Habit) {
        viewModelScope.launch {
            habitsRepository.setHabitNotPracticed(habit)

            removeFromPendingHabits(habit)
        }
    }

    private fun removeFromPendingHabits(habit: Habit) {
        _uiState.update { homeUIState ->
            homeUIState.copy(
                pendingHabits = homeUIState.pendingHabits.minus(
                    habit
                )
            )
        }
    }
}