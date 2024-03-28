package com.example.pyco.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.CategoryChipAndState
import com.example.pyco.data.daos.HabitBlueprintDao
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitBlueprintCategoryCrossRef
import com.example.pyco.data.repositories.CategoriesRepository
import com.example.pyco.data.repositories.HabitBlueprintsRepository
import com.example.pyco.data.repositories.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class HabitDetailsViewUIState(
    val habit: HabitAndHabitBlueprintWithCategories = HabitAndHabitBlueprintWithCategories(
        HabitAndHabitBlueprint(
            Habit(
                habitId = 0,
                habitBlueprintId = 0,
                start = LocalDate.now(),
                end = null,
                interval = 0
            ),
            HabitBlueprint(
                habitBlueprintId = 0,
                name = "",
                description = "",
                badHabit = false,
                customHabit = false,
                isActive = false
            )
        ), categories = listOf()
    ),
    val categories: MutableList<CategoryChipAndState> = mutableListOf(),
    val categoriesFull: MutableList<Category> = mutableListOf(),
    val isLoading: Boolean = false
)

@HiltViewModel
class HabitDetailsViewViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val habitsBlueprintsRepository: HabitBlueprintsRepository,
    private val habitBlueprintDataSource: HabitBlueprintDao,
    private val categoriesRepository: CategoriesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(HabitDetailsViewUIState(isLoading = true))
    val uiState: StateFlow<HabitDetailsViewUIState> = _uiState

    val habitId: Int = checkNotNull(savedStateHandle["habitId"])

    val categories: MutableList<CategoryChipAndState>
        get() = _uiState.value.categories

    val habit: HabitAndHabitBlueprintWithCategories
        get() = _uiState.value.habit

    init {
        getHabitById(habitId)
        getCategoriesForChips()
        getAllCategories()
        autoSelectCategories(habitId)
    }

    fun submitData(
        habitId: Int,
        habitBlueprintId: Int,
        name: String,
        categories: List<Category>,
        description: String,
        isBadHabit: Boolean,
        interval: Int,
        endDate: LocalDate?,
        start: LocalDate
    ) {
        viewModelScope.launch {
            val habitBlueprint = HabitBlueprint(
                habitBlueprintId = habitBlueprintId,
                name = name,
                description = description,
                badHabit = isBadHabit,
            )
            habitsBlueprintsRepository.update(habitBlueprint)

            val habit = Habit(
                habitId = habitId,
                habitBlueprintId = habitBlueprintId,
                interval = interval,
                start = start,
                end = endDate
            )
            habitsRepository.update(habit)

            habitBlueprintDataSource.deleteCrossrefs(habitBlueprintId)

            for (id in categories.map { it.categoryId }) {
                habitBlueprintDataSource.upsert(
                    HabitBlueprintCategoryCrossRef(
                        habitBlueprintId,
                        id
                    )
                )
            }
        }
    }

    private fun getHabitById(habitId: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    habit = habitsRepository.getHabitWithAllInfo(habitId)
                )
            }
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

    private fun autoSelectCategories(habitBlueprintId: Int) {
        viewModelScope.launch {
            val crossrefs = habitBlueprintDataSource.getCrossrefs(habitBlueprintId)

            for (crossref in crossrefs) {
                categories.find { it.category.categoryId == crossref.categoryId }?.selected = true
            }
        }
    }
}