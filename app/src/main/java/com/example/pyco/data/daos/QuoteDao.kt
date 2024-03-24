package com.example.pyco.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.pyco.data.entities.Quote

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote WHERE categoryId = :categoryId")
    suspend fun getQuoteByCategory(categoryId: Int) : Quote?

    @Query("SELECT * FROM quote WHERE habitBlueprintId = :habitBlueprintId")
    suspend fun getQuoteByHabitBlueprint(habitBlueprintId: Int) : Quote?

    @Upsert
    suspend fun upsert(quote: Quote): Long
}