package com.example.pyco.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Internal model used to represent a habit stored locally in a Room database. This is used inside
 * the data layer only.
 *
 * See ModelMapping.kt for mapping functions used to convert this model to other
 * models.
 *
 * TODO a habit can be stacked on another habit or take place at a certain time at a certain place
 */
@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val blueprintId: Int,
    val start: Date,
    val end : Date?,
    val interval: Int
)
