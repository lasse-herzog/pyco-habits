package com.example.pyco.helper

import com.example.pyco.data.entities.CompleteHabit
import java.time.LocalDate
import kotlin.math.ln

class StreakHelper {
    companion object {
        fun CalculateStreakMultiplier(habit: CompleteHabit): Float {
            val dates = habit.dates
            val sortedDates = dates.sortedByDescending { it.date }
            var streak = 0
            var previousDate = LocalDate.now()

            for (habitDate in sortedDates) {
                if (habitDate.habitDone) {
                    if (habitDate.date == previousDate || habitDate.date.plusDays(1) == previousDate) {
                        streak++
                        previousDate = habitDate.date
                    } else {
                        break // End of continuous streak
                    }
                } else {
                    // If habit was due but not done, streak ends
                    break
                }
            }

            return calculateMultiplier(streak)
        }

        fun CalculateStreak(habit: CompleteHabit): Float {
            var totalPoints = 0f
            var currentStreak = 0
            var lastDate: LocalDate? = null

            val sortedDates = habit.dates.sortedBy { it.date }

            for (habitDate in sortedDates) {
                if (lastDate == null || habitDate.date.minusDays(1) == lastDate) {
                    // Continue streak
                    currentStreak++
                } else if (habitDate.date.minusDays(1) != lastDate) {
                    // Reset streak if there's a break
                    currentStreak = 1
                }

                // Calculate the multiplier for the current streak
                val dailyMultiplier = calculateMultiplier(currentStreak)

                // If the habit was done, add points based on the current multiplier
                if (habitDate.habitDone) {
                    totalPoints += 1 * dailyMultiplier
                }

                lastDate = habitDate.date
            }

            return totalPoints
        }

        private fun calculateMultiplier(streak: Int): Float {
            if (streak <= 1) return 1f

            val baseMultiplier = 1.0f // This is the minimum multiplier
            val growthFactor = 1.5f // This controls the rate of growth of the multiplier

            return baseMultiplier + (ln(streak.toDouble()) * growthFactor).toFloat()
        }
    }
}