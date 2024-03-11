package com.example.pyco.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class Quote(
    @PrimaryKey(autoGenerate = true) val quoteId: Int = 0,
    val habitBlueprintId: Int?,
    val categoryId: Int,
    val text: String,
    val author: String?
)
