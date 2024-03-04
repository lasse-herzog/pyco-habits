package com.example.pyco.data

import kotlinx.coroutines.flow.Flow

/**
 * Interface to the data layer.
 */
interface HabitsRepository {
    suspend fun createHabit(title: String)

    fun getHabitsStream(): Flow<List<Habit>>
}
