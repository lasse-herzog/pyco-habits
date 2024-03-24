package com.example.pyco.viewmodels

import android.app.Application
import android.content.Context
import android.view.View
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.pyco.data.PycoDatabase
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.Quote
import com.example.pyco.data.repositories.HabitsRepository
import com.example.pyco.data.repositories.QuotesRepository
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

data class HomeUIState(
    val pendingHabits: List<Habit> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PycoHomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository,
    private val quotesRepository: QuotesRepository,
    @ApplicationContext context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState(isLoading = true))
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        importQuotes(context)
        observeHabits()
    }

    private fun observeHabits() {
        viewModelScope.launch {
            habitsRepository.observePendingHabits()
                .collect { habits ->
                    _uiState.value = HomeUIState(
                        pendingHabits = habits
                    )
                }
        }
    }

    fun setHabitPracticed(habit: Habit) {
        viewModelScope.launch {
            habitsRepository.setHabitPracticed(habit)

            removeFromPendingHabits(habit)
        }
    }

    fun setHabitNotPracticed(habit: Habit) {
        viewModelScope.launch {
            habitsRepository.setHabitNotPracticed(habit)

            removeFromPendingHabits(habit)
        }
    }

    private fun removeFromPendingHabits(habit: Habit) {
        _uiState.update { homeUIState ->
            homeUIState.copy(
                pendingHabits = homeUIState.pendingHabits.minus(
                    habit
                )
            )
        }
    }


    fun importQuotes(context: Context) {
        val quotesList: List<Quote> = readCsv(context.assets.open("quotes.csv"))
        viewModelScope.launch {
            for(quote in quotesList){
                quotesRepository.createQuote(quote)
            }
        }
    }

    fun readCsv(inputStream: InputStream): List<Quote> {
        val reader = inputStream.bufferedReader()
        val header = reader.readLine()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (categoryId, text, author) = it.split(';', ignoreCase = false, limit = 3)
                Quote(habitBlueprintId = null,
                    categoryId = categoryId.trim().toInt(),
                    text = text.trim().removeSurrounding("\""),
                    author = author.trim().removeSurrounding("\"").ifBlank { null })
            }.toList()
    }
}