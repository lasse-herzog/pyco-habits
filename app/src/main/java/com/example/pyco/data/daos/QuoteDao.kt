package com.example.pyco.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.pyco.data.entities.Quote

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote WHERE categoryId = :categoryId")
    suspend fun getQuoteByCategoryId(categoryId: Int): Quote?

    @Query("SELECT * FROM quote WHERE categoryId = :categoryId")
    suspend fun getQuotesByCategoryId(categoryId: Int): List<Quote>

    @Query("SELECT * FROM quote WHERE habitBlueprintId = :habitBlueprintId")
    suspend fun getQuoteByHabitBlueprintId(habitBlueprintId: Int): Quote?

    @Query("SELECT * FROM quote WHERE habitBlueprintId = :habitBlueprintId")
    suspend fun getQuotesByHabitBlueprintId(habitBlueprintId: Int): List<Quote>
}