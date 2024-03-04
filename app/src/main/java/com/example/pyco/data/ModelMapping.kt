package com.example.pyco.data

import com.example.pyco.data.entities.LocalHabit

/**
 * Data model mapping extension functions. There are two model types:
 *
 * - Habit: External model exposed to other layers in the architecture.
 * Obtained using `toExternal`.
 *
 * - LocalHabit: Internal model used to represent a habit stored locally in a database. Obtained
 * using `toLocal`.
 *
 */

fun Habit.toLocal() = LocalHabit(
    id = id,
    createdAt = createdAt,
    title = title
)

fun LocalHabit.toExternal() = Habit(
    id = id,
    createdAt = createdAt,
    title = title
)

fun List<LocalHabit>.toExternal() = map(LocalHabit::toExternal)