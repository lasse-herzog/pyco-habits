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
import com.example.pyco.data.repositories.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject
import kotlin.reflect.typeOf

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    var score: Long by mutableLongStateOf(0)
        private set
    var activeHabits by mutableStateOf<List<CompleteHabit>>(emptyList())
        private set

    init {
        observeHabits()
    }

    private fun observeHabits() {

        viewModelScope.launch {
            val habits: List<CompleteHabit> = habitsRepository.getCompleteHabits()
            score = LocalDate.now().toEpochDay()
            activeHabits =
                habits.filter { x -> x.habitBlueprint.isActive && (x.habit.end == null || x.habit.end >= LocalDate.now()) }
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