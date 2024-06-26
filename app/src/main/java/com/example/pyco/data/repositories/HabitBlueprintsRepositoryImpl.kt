package com.example.pyco.data.repositories

import com.example.pyco.data.daos.HabitBlueprintDao
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.Habit
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
    ) : HabitBlueprint {
        val habitBlueprint = HabitBlueprint(
            name = name,
            description = description,
            badHabit = isBadHabit
        )
        val habitBlueprintId = habitBlueprintDataSource.upsert(habitBlueprint).toInt()

        for (id in categories.map { it.categoryId }) {
            habitBlueprintDataSource.upsert(
                HabitBlueprintCategoryCrossRef(
                    habitBlueprintId,
                    id
                )
            )
        }

        return habitBlueprint.copy(habitBlueprintId = habitBlueprintId)
    }

    override suspend fun getHabitBlueprint(habit: Habit): HabitBlueprint {
        TODO("Not yet implemented")
    }

    override suspend fun getHabitBlueprintWithCategories(habitBlueprintId: Int): HabitBlueprintWithCategories {
        return habitBlueprintDataSource.getHabitBlueprintsWithCategoriesById(habitBlueprintId)
    }

    override suspend fun getHabitBlueprintsWithCategories(): List<HabitBlueprintWithCategories> {
        return habitBlueprintDataSource.getHabitBlueprintsWithCategories()
    }

    override suspend fun update(habitBlueprint: HabitBlueprint){
        habitBlueprintDataSource.update(habitBlueprint)
    }
}