package com.example.pyco.viewmodels

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
    private val _uiState = MutableStateFlow(HomeUIState(isLoading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        observeHabits()
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