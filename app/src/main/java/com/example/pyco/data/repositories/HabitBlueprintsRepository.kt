package com.example.pyco.data.repositories

import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitBlueprintWithCategories

interface HabitBlueprintsRepository {
    suspend fun activateHabitBlueprint(habit: HabitBlueprint)
    suspend fun deactivateHabitBlueprint(habit: HabitBlueprint)
    suspend fun createHabitBlueprint(
        name: String, description: String, categories: List<Category>, isBadHabit: Boolean
    ): HabitBlueprint

    suspend fun getHabitBlueprint(habit: Habit): HabitBlueprint
    suspend fun getHabitBlueprintWithCategories(habitBlueprintId: Int): HabitBlueprintWithCategories
    suspend fun getHabitBlueprintsWithCategories(): List<HabitBlueprintWithCategories>

    suspend fun update(habitBlueprint: HabitBlueprint)
}