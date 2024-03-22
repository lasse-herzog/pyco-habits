package com.example.pyco.data

import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitBlueprintWithCategories

interface HabitBlueprintsRepository {
    suspend fun activateHabitBlueprint(habit: HabitBlueprint)

    suspend fun deactivateHabitBlueprint(habit: HabitBlueprint)

    suspend fun createHabitBlueprint(
        name: String,
        description: String,
        categories: List<Category>,
        isBadHabit: Boolean
    )

    suspend fun getHabitBlueprints() : List<HabitBlueprintWithCategories>
}