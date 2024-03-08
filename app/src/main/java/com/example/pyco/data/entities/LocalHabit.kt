package com.example.pyco.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Internal model used to represent a habit stored locally in a Room database. This is used inside
 * the data layer only.
 *
 * See ModelMapping.kt for mapping functions used to convert this model to other
 * models.
 */
@Entity(tableName = "habit")
data class LocalHabit(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "createdAt") val createdAt : Date
)
