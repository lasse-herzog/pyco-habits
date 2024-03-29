package com.example.pyco.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.pyco.data.entities.HabitDate
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HabitDateDao {
    @Query("SELECT * FROM habitDate WHERE date=:date")
    fun observeHabitDatesByDate(date: LocalDate): Flow<List<HabitDate>>

    @Query("SELECT * FROM habitDate WHERE date=:date AND habitPracticed IS NULL ")
    fun observePendingHabitDates(date: LocalDate): Flow<List<HabitDate>>

    @Query("SELECT * FROM habitDate WHERE habitId = :habitId ORDER BY date DESC LIMIT 1")
    suspend fun getLastHabitDateByHabitId(habitId: Int): HabitDate?

    @Query("SELECT * FROM habitDate WHERE date=:date")
    fun getHabitDatesByDate(date: LocalDate): List<HabitDate>

    @Upsert
    suspend fun upsert(habitDate: HabitDate)
}