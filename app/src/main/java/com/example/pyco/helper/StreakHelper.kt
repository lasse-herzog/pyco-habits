package com.example.pyco.helper

import com.example.pyco.data.entities.CompleteHabit
import java.time.LocalDate
import kotlin.math.ln

class StreakHelper {
    companion object {
        fun CalculateCurrentStreak(habit: CompleteHabit): Triple<Int, Int, Int> {
            val dates = habit.dates
            val sortedDates = dates.sortedByDescending { it.date }
            var streak = 0
            var previousDate = LocalDate.now()

            for (habitDate in sortedDates) {
                if (habitDate.habitPracticed == true) {
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

            return CalculateMultiplier(streak)
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
                val dailyMultiplier = CalculateMultiplier(currentStreak)

                // If the habit was done, add points based on the current multiplier
                if (habitDate.habitPracticed == true) {
                    totalPoints += 1 * dailyMultiplier.first
                }

                lastDate = habitDate.date
            }

            return totalPoints
        }

        fun CalculateMultiplier(streak: Int): Triple<Int, Int, Int> {
            val daysPerLevel = listOf(1, 3, 7, 14, 21, 30, 42, 56, 72, 90)
            // Calculate cumulative thresholds for easier comparison.
            val thresholds = daysPerLevel.scan(0) { acc, i -> acc + i }

            var currentLevel = 0
            var daysIntoLevel = streak
            var totalDays = 0

            for ((index, threshold) in thresholds.withIndex()) {
                if (streak < threshold) {
                    break
                }
                currentLevel = index
                daysIntoLevel = streak - totalDays
                if(index < thresholds.size - 1) {
                    totalDays = threshold
                }
            }

            val multiplier = currentLevel + 1
            return Triple(multiplier, daysIntoLevel, daysPerLevel[currentLevel])
        }
    }
}