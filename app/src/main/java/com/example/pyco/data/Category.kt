package com.example.pyco.data

import java.util.Date

/**
 * Immutable model class for a Habit.
 *
 * @param id id of the category
 * @param name name of the category
 */
data class Category internal constructor(
    val id: Int = 0,
    val name: String = ""
)
