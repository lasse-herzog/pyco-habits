package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.CategoryChipAndState
import com.example.pyco.data.entities.Category
import com.example.pyco.data.repositories.CategoriesRepository
import com.example.pyco.data.repositories.HabitBlueprintsRepository
import com.example.pyco.data.repositories.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HabitDetailsViewUIState(
    val categories: MutableList<CategoryChipAndState> = mutableListOf(),
    val categoriesFull: MutableList<Category> = mutableListOf(),
    val isLoading: Boolean = false
)

@HiltViewModel
class HabitDetailsViewViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val habitsBlueprintsRepository: HabitBlueprintsRepository,
    private val categoriesRepository: CategoriesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HabitDetailsViewUIState(isLoading = true))
    val uiState: StateFlow<HabitDetailsViewUIState> = _uiState

    val categories: MutableList<CategoryChipAndState>
        get() = _uiState.value.categories

    init {
        getCategoriesForChips()
        getAllCategories()
    }

    fun submitData(
        name: String,
        categories: List<Category>,
        description: String,
        isBadHabit: Boolean,
        interval: Int,
        endDate: String
    ) {
        viewModelScope.launch {
            val habitBlueprint = habitsBlueprintsRepository.createHabitBlueprint(
                name,
                description,
                categories,
                isBadHabit
            )
            val habitId = habitsRepository.createHabit(habitBlueprint, interval)
            habitsRepository.createHabitDate(habitId)
        }
    }

    private fun getCategoriesForChips() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    categories = categoriesRepository.getCategoriesForChips()
                )
            }
        }
    }

    private fun getAllCategories() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    categoriesFull = categoriesRepository.getCategories().toMutableList()
                )
            }
        }
    }

    fun categoryClicked(category: CategoryChipAndState, selected: Boolean) {
        categories.find { it.category == category.category }?.selected = selected
    }
}