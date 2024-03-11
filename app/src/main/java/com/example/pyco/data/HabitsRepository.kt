package com.example.pyco.data

import com.example.pyco.data.entities.Habit
import kotlinx.coroutines.flow.Flow

/**
 * Interface to the data layer.
 */
interface HabitsRepository {
    suspend fun createHabit(title: String)
    suspend fun getHabitsForDay()

    fun getHabitsStream(): Flow<List<Habit>>
}
