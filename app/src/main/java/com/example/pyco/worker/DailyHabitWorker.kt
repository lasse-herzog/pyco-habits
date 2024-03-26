package com.example.pyco.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pyco.data.entities.HabitDate
import com.example.pyco.data.repositories.HabitsRepository
import java.time.LocalDate
import javax.inject.Inject

class DailyHabitWorker @Inject constructor(
    appContext: Context,
    workerParams: WorkerParameters,
    val habitsRepository: HabitsRepository
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
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

        return Result.failure()
    }
}
