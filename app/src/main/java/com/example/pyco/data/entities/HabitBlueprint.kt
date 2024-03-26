package com.example.pyco.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "habitBlueprint")
data class HabitBlueprint(
    @PrimaryKey(autoGenerate = true) val habitBlueprintId: Int = 0,
    val name: String,
    val description: String,
    val badHabit: Boolean = false,
    val customHabit: Boolean = true,
    var isActive: Boolean = true
)

data class HabitBlueprintWithCategories(
    @Embedded val habitBlueprint: HabitBlueprint,
    @Relation(
        parentColumn = "habitBlueprintId",
        entityColumn = "categoryId",
        associateBy = Junction(HabitBlueprintCategoryCrossRef::class)
    )
    val categories: List<Category>
)

data class HabitBlueprintWithQuotes(
    @Embedded val habitBlueprint: HabitBlueprint,
    @Relation(
        parentColumn = "habitBlueprintId",
        entityColumn = "habitBlueprintId"
    )
    val quotes: List<Quote>
)
