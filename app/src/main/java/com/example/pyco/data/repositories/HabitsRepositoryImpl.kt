package com.example.pyco.data.repositories

import com.example.pyco.data.daos.HabitDao
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.Date
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
    override suspend fun createHabit(habitBlueprint: HabitBlueprint, interval: Int) {
        val habit = Habit(
            habitBlueprintId = habitBlueprint.habitBlueprintId,
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

    override fun observePendingHabits(): Flow<List<HabitAndHabitBlueprint>> {
        return
    }

    override suspend fun getLastHabitDate(habit: Habit): LocalDate {
        return habitsDataSource.getByLastDate(habit.habitId).first()
    }

    override suspend fun setHabitPracticed(habit: Habit, date: LocalDate) {
        habitsDataSource.upsertHabitDate(
            Date(
                habitId = habit.habitId, date = date, habitPracticed = true
            )
        )
    }

    override suspend fun setHabitNotPracticed(habit: Habit, date: LocalDate) {
        habitsDataSource.upsertHabitDate(
            Date(
                habitId = habit.habitId, date = date, habitPracticed = false
            )
        )
    }
}
