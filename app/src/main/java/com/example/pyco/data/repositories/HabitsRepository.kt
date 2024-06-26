package com.example.pyco.data.repositories

import com.example.pyco.data.entities.CompleteHabit
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitDate
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface to the data layer.
 */
interface HabitsRepository {
    suspend fun createHabit(habitBlueprint: HabitBlueprint, interval: Int) : Int
    suspend fun createHabitDate(habitId: Int, date: LocalDate = LocalDate.now(), practiced: Boolean? = null)
    suspend fun getAllHabitsWithAllInfo(): List<HabitAndHabitBlueprintWithCategories>
    suspend fun getAllHabitsWithBlueprint(): List<HabitAndHabitBlueprint>
    suspend fun getCompleteHabits(): Flow<List<CompleteHabit>>
    suspend fun getHabits(): List<Habit>
    suspend fun getLastHabitDate(habit: Habit): HabitDate?
    suspend fun getById(habitId: Int): Habit
    suspend fun getHabitById(habitId: Int): Habit
    suspend fun getHabitWithAllInfo(habitId: Int): HabitAndHabitBlueprintWithCategories
    suspend fun getHabitDatesByDate(date: LocalDate): List<HabitDate>
    fun observeAllHabitsWithAllInfo(): Flow<List<HabitAndHabitBlueprintWithCategories>>
    fun observeHabits(): Flow<List<Habit>>
    fun observeHabitDatesByDate(date: LocalDate): Flow<List<HabitDate>>
    fun observePendingHabits(): Flow<List<Habit>>
    fun observePendingHabitAndHabitBlueprints(): Flow<List<HabitAndHabitBlueprint>>
    suspend fun setHabitNotPracticed(habit: Habit, date: LocalDate = LocalDate.now())
    suspend fun setHabitNotPracticed(habitId: Int, date: LocalDate = LocalDate.now())
    suspend fun setHabitPracticed(habit: Habit, date: LocalDate = LocalDate.now())
    suspend fun setHabitPracticed(habitId: Int, date: LocalDate = LocalDate.now())
    suspend fun update(habit: Habit)
}
