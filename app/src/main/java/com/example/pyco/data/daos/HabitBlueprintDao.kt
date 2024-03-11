package com.example.pyco.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitBlueprintCategoryCrossRef
import com.example.pyco.data.entities.HabitBlueprintWithCategories

@Dao
interface HabitBlueprintDao {
    @Transaction
    @Query("SELECT * FROM habitBlueprint")
    suspend fun getHabitBlueprintWithCategories(): List<HabitBlueprintWithCategories>

    @Query("UPDATE habitBlueprint SET isActive = :active WHERE habitBlueprintId = :habitBlueprintId")
    suspend fun updateActive(habitBlueprintId: Int, active: Boolean)

    @Upsert
    suspend fun upsert(habitBlueprint: HabitBlueprint) : Long

    @Upsert
    suspend fun upsert(habitBlueprintCategoryCrossRef: HabitBlueprintCategoryCrossRef)
}