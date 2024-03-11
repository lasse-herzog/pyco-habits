package com.example.pyco.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true) val categoryId: Int = 0,
    val name: String
)

data class CategoryWithHabitBlueprints(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "habitBlueprintId",
        associateBy = Junction(HabitBlueprintCategoryCrossRef::class)
    )
    val habitBlueprints: List<HabitBlueprint>
)

data class CategoryWithQuotes(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val quotes: List<Quote>
)