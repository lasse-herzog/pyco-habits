package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.CategoriesRepository
import com.example.pyco.data.entities.Category
import com.example.pyco.data.HabitBlueprintsRepository
import com.example.pyco.data.HabitsRepository
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.data.entities.HabitBlueprint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OverviewUIState(
    val habits: List<HabitAndHabitBlueprintWithCategories> = emptyList(),
    val categories: List<Category> =  emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HabitsOverviewViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val habitsBlueprintsRepository: HabitBlueprintsRepository,
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OverviewUIState(isLoading = true))
    val uiState: StateFlow<OverviewUIState> = _uiState

    init {
        getCategories()
        getAllHabits()
    }

    private fun getAllHabits() {
        viewModelScope.launch {
            habitsRepository.getAllHabitsWithAllInfoStream().collect { habits ->
                _uiState.value =
                    OverviewUIState(habits = habits.filter { it.habitAndHabitBlueprint.habitBlueprint.isActive },
                        categories = categoriesRepository.getCategories())
            }
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
          _uiState.update { currentState ->
              currentState.copy(
                  categories = categoriesRepository.getCategories()
              )
          }

        }
    }

val habits: List<HabitAndHabitBlueprintWithCategories>
    get() = _uiState.value.habits

fun remove(habitBlueprint: HabitBlueprint) {
    viewModelScope.launch {
        habitsBlueprintsRepository.deactivateHabitBlueprint(habitBlueprint)
    }
}

fun sortHabitsAlphabetical(sortAscending: Boolean) {
    if (sortAscending) {
        _uiState.value =
            OverviewUIState(habits = habits.sortedBy { it.habitAndHabitBlueprint.habitBlueprint.name })
    } else {
        _uiState.value =
            OverviewUIState(habits = habits.sortedByDescending { it.habitAndHabitBlueprint.habitBlueprint.name })
    }

}

fun sortHabitsNewest(sortNewest: Boolean) {
    if (sortNewest) {
        _uiState.value =
            OverviewUIState(habits = habits.sortedBy { it.habitAndHabitBlueprint.habit.start })
    } else {
        _uiState.value =
            OverviewUIState(habits = habits.sortedByDescending { it.habitAndHabitBlueprint.habit.start })
    }
}

}