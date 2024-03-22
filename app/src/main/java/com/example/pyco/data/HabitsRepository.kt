package com.example.pyco.data

import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import com.example.pyco.data.entities.HabitAndHabitBlueprintWithCategories
import com.example.pyco.data.entities.HabitBlueprint
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface to the data layer.
 */
interface HabitsRepository {
    suspend fun createHabit(habitBlueprint: HabitBlueprint, interval: Int)
    suspend fun getHabits(): List<Habit>
    suspend fun getAllHabitsWithBlueprint() : List<HabitAndHabitBlueprint>
    suspend fun getAllHabitsWithAllInfo(): List<HabitAndHabitBlueprintWithCategories>
    fun getHabitsStream(): Flow<List<Habit>>
    suspend fun getLastHabitDate(habit: Habit): LocalDate
    suspend fun setHabitFailed(habit: Habit, newHabitDate: LocalDate?)
    suspend fun createHabitAndHabitBlueprint(title: String, tag: String, details: String)
}
