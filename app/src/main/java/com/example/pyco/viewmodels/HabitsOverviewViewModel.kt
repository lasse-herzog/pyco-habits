package com.example.pyco.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pyco.views.Habit
import com.example.pyco.data.HabitSampleData
import com.example.pyco.data.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsOverviewViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    //TODO: change sample data with getAllHabits from the repository
    private val _habits = HabitSampleData.habitSample.toMutableStateList()
    val habits: List<Habit>
        get() = _habits

    fun remove(item: Habit){
        _habits.remove(item)
    }

    fun sortHabitsAlphabetical(sortAscending: Boolean){
        if(sortAscending){
            _habits.sortBy { it.title }
        }else{
            _habits.sortByDescending { it.title }
        }

    }

}