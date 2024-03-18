package com.example.pyco.data.entities

import androidx.room.Entity
import java.time.LocalDate

@Entity(tableName = "habitDate", primaryKeys = ["habitId","date"])
data class HabitDate (
    val habitId : Int,
    val date : LocalDate,
    val habitDone: Boolean
)