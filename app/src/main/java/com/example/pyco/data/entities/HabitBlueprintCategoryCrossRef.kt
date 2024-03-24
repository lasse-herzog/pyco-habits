package com.example.pyco.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["habitBlueprintId", "categoryId"])
data class HabitBlueprintCategoryCrossRef(
    val habitBlueprintId: Int,
    val categoryId: Int
)