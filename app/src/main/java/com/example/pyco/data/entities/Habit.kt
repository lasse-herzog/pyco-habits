package com.example.pyco.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.time.LocalDate

/**
 * Internal model used to represent a habit stored locally in a Room database. This is used inside
 * the data layer only.
 *
 * See ModelMapping.kt for mapping functions used to convert this model to other
 * models.
 *
 * @param interval in days between two applications of the habit
 *
 * TODO a habit can be stacked on another habit or take place at a certain time at a certain place
 * TODO interval can be defined in days or in days a week
 */
@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true) val habitId: Int = 0,
    val habitBlueprintId: Int,
    val start: LocalDate,
    val end: LocalDate?,
    val interval: Int
)

data class CompleteHabit(
    @Embedded val habit: Habit,
    @Relation(
        parentColumn = "habitId",
        entityColumn = "habitBlueprintId"
    )
    val habitBlueprint: HabitBlueprint,
    @Relation(
        parentColumn = "habitBlueprintId",
        entityColumn = "categoryId",
        associateBy = Junction(HabitBlueprintCategoryCrossRef::class)
    )
    val categories: List<Category>,
    @Relation(
        parentColumn = "habitId",
        entityColumn = "habitId",
    )
    val dates: List<HabitDate>
)

data class HabitAndHabitBlueprint(
    @Embedded val habit: Habit,
    @Relation(
        parentColumn = "habitId",
        entityColumn = "habitBlueprintId"
    )
    val habitBlueprint: HabitBlueprint
)

data class HabitAndHabitBlueprintWithCategories(
    @Embedded val habitAndHabitBlueprint: HabitAndHabitBlueprint,
    @Relation(
        parentColumn = "habitBlueprintId",
        entityColumn = "categoryId",
        associateBy = Junction(HabitBlueprintCategoryCrossRef::class)
    )
    val categories: List<Category>
)