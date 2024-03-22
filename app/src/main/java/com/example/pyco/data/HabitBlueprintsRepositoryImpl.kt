package com.example.pyco.data

import com.example.pyco.data.daos.HabitBlueprintDao
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitBlueprintCategoryCrossRef
import com.example.pyco.data.entities.HabitBlueprintWithCategories
import javax.inject.Inject

class HabitBlueprintsRepositoryImpl @Inject constructor(
    private val habitBlueprintDataSource: HabitBlueprintDao
) : HabitBlueprintsRepository {
    override suspend fun activateHabitBlueprint(habit: HabitBlueprint) {
        habitBlueprintDataSource.updateActive(habit.habitBlueprintId, true)
    }

    override suspend fun deactivateHabitBlueprint(habit: HabitBlueprint) {
        habitBlueprintDataSource.updateActive(habit.habitBlueprintId, false)
    }

    override suspend fun createHabitBlueprint(
        name: String,
        description: String,
        categories: List<Category>,
        isBadHabit: Boolean
    ) {
        val habitBlueprint = HabitBlueprint(
            name = name,
            description = description,
            badHabit = isBadHabit
        )
        val habitBlueprintId = habitBlueprintDataSource.upsert(habitBlueprint)

        for (id in categories.map { it.categoryId }) {
            habitBlueprintDataSource.upsert(
                HabitBlueprintCategoryCrossRef(
                    habitBlueprintId.toInt(),
                    id
                )
            )
        }
    }
    override suspend fun getHabitBlueprints(): List<HabitBlueprintWithCategories> {
        return habitBlueprintDataSource.getHabitBlueprintWithCategories()
    }

}