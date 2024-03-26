package com.example.pyco.data.repositories

import com.example.pyco.data.daos.HabitDao
import com.example.pyco.data.daos.HabitDateDao
import com.example.pyco.data.entities.CompleteHabit
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Default implementation of [HabitsRepository]. Single entry point for managing habits' data.
 *
 * @param habitDataSource - The local data source
 */
class HabitsRepositoryImpl @Inject constructor(
    private val habitDataSource: HabitDao, private val habitDateDataSource: HabitDateDao
) : HabitsRepository {
    override suspend fun createHabit(habitBlueprint: HabitBlueprint, interval: Int): Int {
        val habit = Habit(
            habitBlueprintId = habitBlueprint.habitBlueprintId,
            start = LocalDate.now(),
            end = null,
            interval = interval
        )

        return habitDataSource.upsert(habit).toInt()
    }

    override suspend fun createHabitDate(habitId: Int, date: LocalDate, practiced: Boolean?) {
        habitDateDataSource.upsert(HabitDate(habitId = habitId, date = date, habitPracticed = practiced))
    }

    override suspend fun getCompleteHabits(): Flow<List<CompleteHabit>> {
        return habitDataSource.getAllComplete()
    }

    override suspend fun getAllHabitsWithBlueprint(): List<HabitAndHabitBlueprint> {
        return habitDataSource.getAllHabitsAndBlueprints()
    }

    override suspend fun getAllHabitsWithAllInfo(): List<HabitAndHabitBlueprintWithCategories> {
        return habitDataSource.getAllHabitsWithAllInfo()
    }

    override suspend fun getHabits(): List<Habit> {
        return habitDataSource.getAll()
    }

    override suspend fun getLastHabitDate(habit: Habit): HabitDate? {
        return habitDateDataSource.getLastHabitDateByHabitId(habit.habitId)
    }

    override suspend fun getHabitDatesByDate(date: LocalDate): List<HabitDate> {
        return habitDateDataSource.getHabitDatesByDate(date)
    }

    override fun observeAllHabitsWithAllInfo(): Flow<List<HabitAndHabitBlueprintWithCategories>> {
        return habitDataSource.getAllHabitsWithAllInfoStream()
    }

    override fun observeHabits(): Flow<List<Habit>> {
        return habitDataSource.observeAll()
    }

    override fun observeHabitDatesByDate(date: LocalDate): Flow<List<HabitDate>> {
        return habitDateDataSource.observeHabitDatesByDate(date = date)
    }

    override fun observePendingHabits(): Flow<List<Habit>> {
        return habitDateDataSource.observeHabitDatesByDate(date = LocalDate.now())
            .map { habitDates ->
                habitDates.filter { it.habitPracticed == null }.map {
                    habitDataSource.getById(
                        it.habitId
                    )
                }
            }
    }

    override suspend fun setHabitPracticed(habit: Habit, date: LocalDate) {
        habitDateDataSource.upsert(
            HabitDate(
                habitId = habit.habitId,
                date = date,
                habitPracticed = true,
                timestamp = LocalDateTime.now()
            )
        )
    }

    override suspend fun setHabitNotPracticed(habit: Habit, date: LocalDate) {
        habitDateDataSource.upsert(
            HabitDate(
                habitId = habit.habitId,
                date = date,
                habitPracticed = false,
                timestamp = LocalDateTime.now()
            )
        )
    }
}
