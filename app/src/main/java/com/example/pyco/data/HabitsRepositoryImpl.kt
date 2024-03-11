package com.example.pyco.data

import com.example.pyco.data.daos.HabitDao
import com.example.pyco.data.entities.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

/**
 * Default implementation of [HabitsRepository]. Single entry point for managing habits' data.
 *
 * @param habitsDataSource - The local data source
 */
class HabitsRepositoryImpl @Inject constructor(
    private val habitsDataSource: HabitDao,
) : HabitsRepository {
    override suspend fun createHabit(title: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getHabitsForDay() {
        TODO("Not yet implemented")
    }

    override fun getHabitsStream(): Flow<List<Habit>> {
        return habitsDataSource.observeAll()
    }
}
