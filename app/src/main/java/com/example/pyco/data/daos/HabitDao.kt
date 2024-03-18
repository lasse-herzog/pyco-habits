package com.example.pyco.data.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.pyco.data.entities.Habit
import com.example.pyco.data.entities.HabitAndHabitBlueprint
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Data Access Object for the habit table.
 */
@Dao
interface HabitDao {
    @Transaction
    @Query("SELECT *  FROM habit WHERE habitId = :habitId")
    suspend fun getHabitAndHabitBlueprint(habitId: Int): HabitAndHabitBlueprint

    /**
     * Observes list of habits.
     *
     * @return all habits.
     */
    @Query("SELECT * FROM habit")
    fun observeAll(): Flow<List<Habit>>

    /**
     * Observes a single habit.
     *
     * @param habitId the habit id.
     * @return the habit with habitId.
     */
    @Query("SELECT * FROM habit WHERE habitId = :habitId")
    fun observeById(habitId: Int): Flow<Habit>

    /**
     * Select all habits from the habits table.
     *
     * @return all habits.
     */
    @Query("SELECT * FROM habit")
    suspend fun getAll(): List<Habit>

    /**
     * Select all habits and blueprints from the db.
     *
     * @return a list of habitandblueprint objects with all habits.
     */
    @Transaction
    @Query("SELECT * FROM habit")
    suspend fun getAllHabitsAndBlueprints(): List<HabitAndHabitBlueprint>

    /**
     * Select a habit by id.
     *
     * @param habitId the habit id.
     * @return the habit with habitId.
     */
    @Query("SELECT * FROM habit WHERE habitId = :habitId")
    suspend fun getById(habitId: String): Habit?

    /**
     * Insert or update a habit in the database. If a habit already exists, replace it.
     *
     * @param habit the habit to be inserted or updated.
     */
    @Upsert
    suspend fun upsert(habit: Habit)

    /**
     * Insert or update habits in the database. If a habit already exists, replace it.
     *
     * @param habits the habits to be inserted or updated.
     */
    @Upsert
    suspend fun upsertAll(habits: List<Habit>)

    /**
     * Delete a habit by id.
     *
     * @return the number of habits deleted. This should always be 1.
     */
    @Query("DELETE FROM habit WHERE habitId = :habitId")
    suspend fun deleteById(habitId: String): Int

    /**
     * Delete all habits.
     */
    @Query("DELETE FROM habit")
    suspend fun deleteAll()

    @Query("SELECT date FROM habitDate WHERE habitId = :habitId ORDER BY date DESC")
    suspend fun getByLastDate(habitId: Int): List<LocalDate>
}
