package com.example.pyco.worker

import android.content.Context
import com.example.pyco.data.di.DatabaseModule
import com.example.pyco.data.entities.HabitDate
import com.example.pyco.data.repositories.HabitsRepositoryImpl
import java.time.LocalDate

class HabitWorker(private val appContext: Context) {

    private fun isDailyWorkDone(): Boolean {
        val sharedPreferences =
            appContext.getSharedPreferences("PycoPreferences", Context.MODE_PRIVATE)
        val lastWorkDate = sharedPreferences.getString("LastDailyWorkDate", null)
        val currentDate = LocalDate.now().toString()

        return lastWorkDate != null && lastWorkDate == currentDate
    }

    private fun markDailyWorkAsDone() {
        val sharedPreferences =
            appContext.getSharedPreferences("PycoPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val currentDate = LocalDate.now().toString()
        editor.putString("LastDailyWorkDate", currentDate)
        editor.apply()
    }

    suspend fun doWork() {
        if (isDailyWorkDone()) return

        val database = DatabaseModule.provideDataBase(appContext)
        val habitsRepository = HabitsRepositoryImpl(
            DatabaseModule.provideHabitDao(database),
            DatabaseModule.provideHabitDateDao(database)
        )
        val habits = habitsRepository.getHabits()
        val localDate = LocalDate.now()

        for (habit in habits) {
            val habitDate: HabitDate? = habitsRepository.getLastHabitDate(habit)

            // if null create one for today
            if (habitDate == null) {
                habitsRepository.createHabitDate(habit.habitId, localDate)
                continue
            }

            // check if habitdate is before today, exit if today
            if (habitDate.date == localDate) continue

            // if habitdate practiced == null, set to undone
            if (habitDate.habitPracticed == null) {
                habitsRepository.createHabitDate(habit.habitId, habitDate.date, false)
            }

            // find the next due date
            var nextHabitDate = habitDate.date.plusDays(habit.interval.toLong())
            while (nextHabitDate < localDate) {
                // if next due day < today, create habit date and set practiced false
                habitsRepository.createHabitDate(habit.habitId, nextHabitDate, false)
                nextHabitDate = nextHabitDate.plusDays(habit.interval.toLong())
            }

            // if next due day = today, create habit date and set practiced null
            if (nextHabitDate == localDate) {
                habitsRepository.createHabitDate(habit.habitId, nextHabitDate)
            }
        }

        markDailyWorkAsDone()
    }
}