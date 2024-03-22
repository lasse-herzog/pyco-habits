package com.example.pyco.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    var score: Int by mutableIntStateOf(0)
        private set
    var someText: String by mutableStateOf("")
        private set

    init {
        observeHabits()
    }

    private fun observeHabits() {

        viewModelScope.launch {
            val habits = habitsRepository.getCompleteHabits()
            score = habits[1].dates.count()
            someText = habits.first().habitBlueprint.name
        }

//        viewModelScope.launch {
//        }

//        habitsRepository.getHabitsStream()
//            .catch { ex ->
//                _uiState.value = HomeUIState(error = ex.message)
//            }
//            .collect { habits ->
//                _uiState.value = HomeUIState(
//                    habits = habits,
//                )
//            }
    }
}