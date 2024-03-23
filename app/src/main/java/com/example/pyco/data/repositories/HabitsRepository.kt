package com.example.pyco.data.repositories

import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitBlueprint
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface to the data layer.
 */
interface HabitsRepository {
    suspend fun createHabit(habitBlueprint: HabitBlueprint, interval: Int) : Int
    suspend fun createHabitDate(habitId: Int, date: LocalDate = LocalDate.now())
    suspend fun getHabits(): List<Habit>
    suspend fun getLastHabitDate(habit: Habit): LocalDate
    fun observeHabits(): Flow<List<Habit>>
    fun observePendingHabits(): Flow<List<Habit>>
    suspend fun setHabitNotPracticed(habit: Habit, date: LocalDate = LocalDate.now())
    suspend fun setHabitPracticed(habit: Habit, date: LocalDate = LocalDate.now())
}
