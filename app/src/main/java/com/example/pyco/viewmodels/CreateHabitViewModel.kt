package com.example.pyco.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.data.entities.Category
import com.example.pyco.data.repositories.HabitBlueprintsRepository
import com.example.pyco.data.repositories.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateHabitViewModel @Inject constructor (
    private val habitsRepository: HabitsRepository,
    private val habitsBlueprintsRepository: HabitBlueprintsRepository
): ViewModel() {
    suspend fun submitData(name: String, categories: List<Category>, description: String /*TODO: Bad habit mit übergeben und Intervall übergeben*/) {
        viewModelScope.launch {
            val habitBlueprint = habitsBlueprintsRepository.createHabitBlueprint(name, description, categories, isBadHabit = false)
            val habitId = habitsRepository.createHabit(habitBlueprint, 42)
            habitsRepository.createHabitDate(habitId)
        }
    }
}