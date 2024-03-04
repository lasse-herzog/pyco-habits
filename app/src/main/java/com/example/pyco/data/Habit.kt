package com.example.pyco.data

import java.util.Date

/**
 * Immutable model class for a Habit.
 *
 * @param id id of the habit
 * @param title title of the habit
 * @param createdAt date when the habit was created
 */
data class Habit internal constructor(
    val id: Int = 0,
    val title: String = "",
    val createdAt: Date = Date()
)
