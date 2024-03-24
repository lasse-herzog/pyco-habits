package com.example.pyco.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pyco.data.daos.CategoryDao
import com.example.pyco.data.daos.HabitBlueprintDao
import com.example.pyco.data.daos.HabitDao
import com.example.pyco.data.daos.HabitDateDao
import com.example.pyco.data.daos.QuoteDao
import com.example.pyco.data.entities.Category
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitBlueprint
import com.example.pyco.data.entities.HabitBlueprintCategoryCrossRef
import com.example.pyco.data.entities.HabitDate
import com.example.pyco.data.entities.Quote

/**
 * The Room Database of the App.
 */
@Database(
    entities = [Category::class, Habit::class, HabitBlueprint::class, HabitBlueprintCategoryCrossRef::class, HabitDate::class, Quote::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PycoDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun habitDao(): HabitDao
    abstract fun habitBlueprintDao(): HabitBlueprintDao
    abstract fun habitDateDao(): HabitDateDao
    abstract fun quoteDao(): QuoteDao
}