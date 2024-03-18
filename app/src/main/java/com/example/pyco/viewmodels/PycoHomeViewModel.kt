package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.HabitBlueprintsRepository
import com.example.pyco.data.HabitsRepository
import com.example.pyco.data.entities.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUIState(
    val habits: List<Habit> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PycoHomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val habitBlueprintsRepository: HabitBlueprintsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState(isLoading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        testHabitBLueprints()

        observeHabits()
    }

    private fun testHabitBLueprints() {
        viewModelScope.launch {
            habitBlueprintsRepository.getHabitBlueprints()
            habitBlueprintsRepository.createHabitBlueprint(
                "test", "a test habit blueprint", listOf(
                    Category(name = "test1"), Category(name = "test2")
                ), false
            )
        }
    }

    private fun observeHabits() {
        viewModelScope.launch {
            habitsRepository.getHabitsStream()
                .catch { ex ->
                    _uiState.value = HomeUIState(error = ex.message)
                }
                .collect { habits ->
                    _uiState.value = HomeUIState(
                        habits = habits,
                    )
                }
        }
    }
}