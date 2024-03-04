package com.example.pyco.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pyco.data.daos.HabitDao
import com.example.pyco.data.entities.LocalHabit

/**
 * The Room Database of the App.
 */
@Database(entities = [LocalHabit::class], version = 1)
@TypeConverters(Converters::class)
abstract class PycoDatabase : RoomDatabase() {
    abstract fun habitDao() : HabitDao
}