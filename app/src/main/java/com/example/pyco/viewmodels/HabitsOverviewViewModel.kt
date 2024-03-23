package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.CategoryChipAndState
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.repositories.CategoriesRepository
import com.example.pyco.data.repositories.HabitBlueprintsRepository
import com.example.pyco.data.repositories.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OverviewUIState(
    val habits: List<HabitAndHabitBlueprintWithCategories> = emptyList(),
    val categories: MutableList<CategoryChipAndState> =  mutableListOf(),
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
        //check if there are category filters selected
        val filterCategories: MutableList<Category> = mutableListOf()
        categories.forEach {
            if(it.selected){ filterCategories.add(it.category)}
        }
        if(filterCategories.isNotEmpty()){
            viewModelScope.launch {
                habitsRepository.observeAllHabitsWithAllInfo().collect { habits ->
                    _uiState.update{ currentState ->
                        currentState.copy(
                            habits = habits.filter { it.habitAndHabitBlueprint.habitBlueprint.isActive && it.categories.containsAll(filterCategories)}
                        )
                    }
                }
            }
        }else{
            viewModelScope.launch {
                habitsRepository.observeAllHabitsWithAllInfo().collect { habits ->
                    _uiState.update{ currentState ->
                        currentState.copy(
                            habits = habits.filter { it.habitAndHabitBlueprint.habitBlueprint.isActive }
                        )
                    }
                }
            }
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
          _uiState.update { currentState ->
              currentState.copy(
                  categories = categoriesRepository.getCategoriesForChips()
              )
          }

        }
    }

val habits: List<HabitAndHabitBlueprintWithCategories>
    get() = _uiState.value.habits

val categories: MutableList<CategoryChipAndState>
    get() = _uiState.value.categories

fun remove(habitBlueprint: HabitBlueprint) {
    viewModelScope.launch {
        habitsBlueprintsRepository.deactivateHabitBlueprint(habitBlueprint)
    }
}

fun filterWithCategory(category: CategoryChipAndState, selected: Boolean){
    categories.find { it.category == category.category }?.selected = selected
    getAllHabits()
}

fun sortHabitsAlphabetical(sortAscending: Boolean) {
    if (sortAscending) {
        _uiState.update {currentState ->
            currentState.copy(
                habits = habits.sortedBy { it.habitAndHabitBlueprint.habitBlueprint.name }
            )
        }
    } else {
        _uiState.update {currentState ->
            currentState.copy(
                habits = habits.sortedByDescending { it.habitAndHabitBlueprint.habitBlueprint.name }
            )
        }
    }

}

fun sortHabitsNewest(sortNewest: Boolean) {
    if (sortNewest) {
        _uiState.update {currentState ->
            currentState.copy(
                habits = habits.sortedBy { it.habitAndHabitBlueprint.habit.start }
            )
        }
    } else {
        _uiState.update {currentState ->
            currentState.copy(
                habits = habits.sortedByDescending { it.habitAndHabitBlueprint.habit.start }
            )
        }
    }
}

}