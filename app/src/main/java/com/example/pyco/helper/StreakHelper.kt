package com.example.pyco.helper

import com.example.pyco.data.entities.CompleteHabit
import java.time.LocalDate

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

        fun CalculateStreak(habit: CompleteHabit): Int {
            var totalPoints = 0
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
            if (streak <= 0) return Triple(1, 0, 1) // Handle the zero case explicitly

            val daysPerLevel = listOf(1, 3, 7, 14, 21, 30, 42, 56, 72, 90)
            var cumulativeDays = 0
            var level = 1
            var daysIntoLevel = 0
            var nextLevelDays = daysPerLevel.first()

            for (daysRequired in daysPerLevel) {
                if (streak <= cumulativeDays + daysRequired) {
                    daysIntoLevel = streak - cumulativeDays
                    nextLevelDays = daysRequired
                    break
                }
                cumulativeDays += daysRequired
                level++
            }

            return Triple(level, daysIntoLevel, nextLevelDays)
        }

        fun CalculateLevel(score: Int): Triple<Int, Int, Int> {
            if (score <= 0) return Triple(1, 0, 10) // Handle the zero case explicitly

            val pointsPerLevel = listOf(10, 20, 40, 80, 160)
            var cumulativeScore = 0
            var level = 1
            var pointsIntoLevel = 0
            var nextLevelPoints = pointsPerLevel.first()

            for (daysRequired in pointsPerLevel) {
                if (score <= cumulativeScore + daysRequired) {
                    pointsIntoLevel = score - cumulativeScore
                    nextLevelPoints = daysRequired
                    break
                }
                cumulativeScore += daysRequired
                level++
            }

            return Triple(level, pointsIntoLevel, nextLevelPoints)
        }
    }
}