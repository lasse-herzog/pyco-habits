package com.example.pyco.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitBlueprintCategoryCrossRef
import com.example.pyco.data.entities.HabitBlueprintWithCategories
import com.example.pyco.data.entities.HabitBlueprintWithQuotes

@Dao
interface HabitBlueprintDao {
    @Transaction
    @Query("SELECT * FROM habitBlueprint")
    suspend fun getHabitBlueprintWithCategories(): List<HabitBlueprintWithCategories>
    @Transaction
    @Query("SELECT * FROM habitBlueprint")
    suspend fun getHabitBlueprintWithQuotes(): List<HabitBlueprintWithQuotes>

    @Query("UPDATE habitBlueprint SET isActive = :active WHERE habitBlueprintId = :habitBlueprintId")
    suspend fun updateActive(habitBlueprintId: Int, active: Boolean)

    @Query("DELETE FROM HabitBlueprintCategoryCrossRef WHERE habitBlueprintId = :habitBlueprintId")
    suspend fun deleteCrossrefs(habitBlueprintId: Int)

    @Upsert
    suspend fun upsert(habitBlueprint: HabitBlueprint) : Long

    @Upsert
    suspend fun upsert(habitBlueprintCategoryCrossRef: HabitBlueprintCategoryCrossRef)

    @Update
    suspend fun update(habitBlueprint: HabitBlueprint)
}