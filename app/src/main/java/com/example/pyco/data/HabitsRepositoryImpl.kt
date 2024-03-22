package com.example.pyco.data

import com.example.pyco.data.daos.HabitDao
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitBlueprint
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

/**
 * Default implementation of [HabitsRepository]. Single entry point for managing habits' data.
 *
 * @param habitsDataSource - The local data source
 */
class HabitsRepositoryImpl @Inject constructor(
    private val habitsDataSource: HabitDao,
) : HabitsRepository {
    override suspend fun createHabit(habitBlueprint: HabitBlueprint, interval : Int) {
        val habit = Habit(
            blueprintId = habitBlueprint.habitBlueprintId,
            start = LocalDate.now(),
            end = null,
            interval = interval
        )

        habitsDataSource.upsert(habit)
    }

    override suspend fun getHabits(): List<Habit> {
        return habitsDataSource.getAll()
    }

    override fun getHabitsStream(): Flow<List<Habit>> {
        return habitsDataSource.observeAll()
    }

    override suspend fun getLastHabitDate(habit: Habit): LocalDate {
        return habitsDataSource.getByLastDate(habit.habitId).first()
    }

    override suspend fun setHabitFailed(habit: Habit, newHabitDate: LocalDate?) {
        TODO("Not yet implemented")
    }

    override suspend fun createHabitAndHabitBlueprint(title: String, tag: String, details: String) {
        TODO("Not yet implemented")
    }
}
