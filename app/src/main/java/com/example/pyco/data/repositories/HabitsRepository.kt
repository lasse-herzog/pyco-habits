package com.example.pyco.data.repositories

import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitBlueprint
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface to the data layer.
 */
interface HabitsRepository {
    suspend fun createHabit(habitBlueprint: HabitBlueprint, interval: Int)
    suspend fun getHabits(): List<Habit>
    fun getHabitsStream(): Flow<List<Habit>>
    fun observePendingHabits():Flow<List<HabitAndHabitBlueprint>>
    suspend fun getLastHabitDate(habit: Habit): LocalDate
    suspend fun setHabitNotPracticed(habit: Habit, date: LocalDate = LocalDate.now())
    suspend fun setHabitPracticed(habit: Habit, date: LocalDate = LocalDate.now())
}
