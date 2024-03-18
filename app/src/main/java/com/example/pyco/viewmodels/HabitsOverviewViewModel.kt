package com.example.pyco.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.HabitsRepository
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OverviewUIState(
    val habits: List<HabitAndHabitBlueprint> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
@HiltViewModel
class HabitsOverviewViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(OverviewUIState(isLoading = true))
    val uiState: StateFlow<OverviewUIState> = _uiState

    init {
        getAllHabits()
    }

    private fun getAllHabits(){
        viewModelScope.launch {
            _uiState.value = OverviewUIState(
                habits = habitsRepository.getAllHabitsWithBlueprint(),
            )
        }
    }

    val habits: List<HabitAndHabitBlueprint>
        get() = _uiState.value.habits

    fun remove(item: Habit){
        //TODO
    }

    fun sortHabitsAlphabetical(sortAscending: Boolean){
        if(sortAscending){
            _uiState.value.habits.sortedBy { it.habitBlueprint.name }
        }else{
            _uiState.value.habits.sortedByDescending { it.habitBlueprint.name }
        }

    }

}